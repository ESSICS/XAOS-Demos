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


import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.concurrent.CompletionStage;


/**
 * API for asynchronous file-system operations similar to
 * {@link AsynchronousIOOperations}, where each "file-system changing" operation
 * takes an extra argument: the <i>initiator</i> of the change.
 *
 * @author claudio.rosati@esss.se
 * @param <I> Type of the <i>initiator</i> of the I/O operation.
 * @see <a href="https://github.com/ESSICS/LiveDirsFX">LiveDirsFX</a>
 */
public interface InitiatorAsynchronousIOOperations<I> {

	/**
	 * Creates a directory. If the directory already exists, or its parent
	 * directory does not exist, or another I/O error occurs, the returned
	 * completion stage is completed exceptionally with the encountered error.
	 *
	 * @param dir       The pathname of the directory to be created.
	 * @param initiator The initiator of the operation.
	 * @return An exceptionally completed {@link CompletionStage} in case the
	 *         directory already exists, or its parent directory does not exist,
	 *         or an exception is thrown.
	 */
	public CompletionStage<Void> createDirectory( Path dir, I initiator );

	/**
	 * Creates an empty file. If file already exists or an I/O error occurs,
	 * the returned completion stage is completed exceptionally with the
	 * encountered error.
	 *
	 * @param file      The pathname of the file to be created.
	 * @param initiator The initiator of the operation.
	 * @return An exceptionally completed {@link CompletionStage} in case the
	 *         file already exists, or an exception is thrown.
	 */
	public CompletionStage<Void> createFile( Path file, I initiator );

	/**
	 * Writes binary file to disk. If an I/O error occurs, the returned
	 * completion stage is completed exceptionally with the encountered error.
	 *
	 * @param file      The pathname of the file to be created.
	 * @param content   The bytes to be written into the file.
	 * @param initiator The initiator of the operation.
	 * @return An exceptionally completed {@link CompletionStage} in case an
	 *         exception is thrown.
	 */
	public CompletionStage<Void> writeBinaryFile( Path file, byte[] content, I initiator );

	/**
	 * Writes the given string in a text file to disk. If an I/O error occurs,
	 * the returned completion stage is completed exceptionally with the
	 * encountered error.
	 *
	 * @param file      The pathname of the file to be created.
	 * @param content   The String to be written in the file.
	 * @param charset   The {@link Charset} used.
	 * @param initiator The initiator of the operation.
	 * @return An exceptionally completed {@link CompletionStage} in case an
	 *         exception is thrown.
	 */
	public CompletionStage<Void> writeTextFile( Path file, String content, Charset charset, I initiator );

}
