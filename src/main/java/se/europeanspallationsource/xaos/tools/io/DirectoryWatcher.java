/*
 * Copyright 2018 claudiorosati.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.europeanspallationsource.xaos.tools.io;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import org.reactfx.EventSource;
import org.reactfx.EventStream;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;


/**
 * Watches for changes in files and directories.
 *
 * @author claudio.rosati@esss.se
 * @see <a href="https://github.com/ESSICS/LiveDirsFX">LiveDirsFX</a>
 */
@SuppressWarnings( "ClassWithoutLogger" )
class DirectoryWatcher {

	private final EventSource<Throwable> errors = new EventSource<>();
	private final Executor eventThreadExecutor;
	private final LinkedBlockingQueue<Runnable> executorQueue = new LinkedBlockingQueue<>();
	private boolean interrupted = false;
	private final Thread ioThread;
	private boolean mayInterrupt = false;
	private volatile boolean shutdown = false;
	private final EventSource<WatchKey> signalledKeys = new EventSource<>();
	private final WatchService watcher;

	DirectoryWatcher( Executor eventThreadExecutor ) throws IOException {

		this.watcher = FileSystems.getDefault().newWatchService();
		this.ioThread = new Thread(this::ioLoop, "DirectoryWatcherIO");
		this.eventThreadExecutor = eventThreadExecutor;

		startIOThread();

	}

	/**
	 * @return The {@link EventStream} of thrown errors.
	 */
	public EventStream<Throwable> getErrorsStream() {
		return errors;
	}

	/**
	 * @return The {@link EventStream} of signalled {@link WatchKey}s.
	 */
	public EventStream<WatchKey> getSignalledKeysStream() {
		return signalledKeys;
	}

	/**
	 * Shutdown this watcher.
	 */
	public void shutdown() {

		shutdown = true;

		interrupt();

	}

	/**
	 * Watches the given directory for entry create, delete, and modify events.
	 *
	 * @param dir The directory to be watched.
	 * @throws IOException If an I/) error occurs.
	 */
	public void watch( Path dir ) throws IOException {
		dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
	}

	/**
	 * Watches the given directory for entry create, delete, and modify events.
	 * If an I/O error occurs, the exception is logged (emitted).
	 *
	 * @param dir The directory to be watched.
	 */
	public void watchOrLogError( Path dir ) {
		try {
			watch(dir);
		} catch ( IOException e ) {
			emitError(e);
		}
	}

	private void emitError( Throwable e ) {
		executeOnEventThread(() -> errors.push(e));
	}

	private void emitKey( WatchKey key ) {
		executeOnEventThread(() -> signalledKeys.push(key));
	}

	@SuppressWarnings( { "UseSpecificCatch", "BroadCatchBlock", "TooBroadCatch" } )
	private <T> void executeIOOperation( Callable<T> operation, Consumer<T> onSuccess, Consumer<Throwable> onError ) {
		executeOnIOThread(() -> {
			try {

				T result = operation.call();

				executeOnEventThread(() -> onSuccess.accept(result));

			} catch ( Throwable t ) {
				executeOnEventThread(() -> onError.accept(t));
			}
		});
	}

	private void executeOnEventThread( Runnable action ) {
		eventThreadExecutor.execute(action);
	}

	private void executeOnIOThread( Runnable action ) {
		executorQueue.add(action);
		interrupt();
	}

	private synchronized void interrupt() {
		if ( mayInterrupt ) {
			ioThread.interrupt();
		} else {
			interrupted = true;
		}
	}

	private void ioLoop() {
		while ( true ) {

			WatchKey key = take();

			if ( key != null ) {
				emitKey(key);
			} else if ( shutdown ) {
				try {
					watcher.close();
				} catch ( IOException e ) {
					emitError(e);
				}
				break;
			} else {
				processIOQueues();
			}

		}
	}

	@SuppressWarnings( "NestedAssignment" )
	private void processIOQueues() {

		Runnable operation;

		while ( ( operation = executorQueue.poll() ) != null ) {
			try {
				operation.run();
			} catch ( Throwable t ) {
				errors.push(t);
			}
		}

	}

	private void startIOThread() {
		ioThread.start();
	}

	private WatchKey take() {
		try {

			synchronized ( this ) {
				if ( interrupted ) {

					interrupted = false;

					throw new InterruptedException();

				} else {
					mayInterrupt = true;
				}
			}

			try {
				return watcher.take();
			} finally {
				synchronized ( this ) {
					mayInterrupt = false;
				}
			}

		} catch ( InterruptedException e ) {
			return null;
		}
	}

}
