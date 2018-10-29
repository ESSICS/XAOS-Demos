/*
 * Copyright 2018 European Spallation Source ERIC.
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
package se.europeanspallationsource.xaos.demos.filebrowser;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
import se.europeanspallationsource.xaos.ui.control.tree.TreeItems;
import se.europeanspallationsource.xaos.ui.control.tree.directory.TreeDirectoryMonitor;


/**
 * FXML Controller class
 *
 * @author claudio.rosati@esss.se
 */
public class FileBrowserController implements Initializable {

	private static final Logger LOGGER = Logger.getLogger(FileBrowserController.class.getName());

	private TreeDirectoryMonitor<ChangeSource, Path> directoryMonitor;
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	@FXML
	private TreeView<Path> treeView;

	public TreeDirectoryMonitor<?, Path> getDirectoryMonitor() {
		return directoryMonitor;
	}

	@Override
	@SuppressWarnings( "UseOfSystemOutOrSystemErr" )
	public void initialize( URL url, ResourceBundle rb ) {

		long startTime = System.currentTimeMillis();

		try {

			directoryMonitor = TreeDirectoryMonitor.build(ChangeSource.EXTERNAL);

//			directoryMonitor.addTopLevelDirectory(Paths.get(System.getProperty("user.home")).toAbsolutePath(), true);
//			directoryMonitor.addTopLevelDirectory(Paths.get("/Users").toAbsolutePath(), true);
			directoryMonitor.addTopLevelDirectory(Paths.get("/").toAbsolutePath(), true);
			treeView.setRoot(directoryMonitor.model().getRoot());
			treeView.setShowRoot(false);
			treeView.setCellFactory(TreeItems.defaultTreePathCellFactory());

		} catch ( IOException ex ) {
			LOGGER.log(Level.SEVERE, null, ex);
		}

		long endTime = System.currentTimeMillis();

		System.out.println(MessageFormat.format(
			"Initialisation time: {0}ms",
			( endTime - startTime )
		));

	}

	@SuppressWarnings( "PackageVisibleInnerClass" )
	private enum ChangeSource {
		INTERNAL,
		EXTERNAL
	}

}
