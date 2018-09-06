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


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.europeanspallationsource.xaos.ui.control.tree.directory.TreeDirectoryModel;

import static javafx.application.Application.launch;


/**
 * A demo application using the {@link TreeDirectoryModel}.
 *
 * @author claudio.rosati@esss.se
 */
@SuppressWarnings( "ClassWithoutLogger" )
public class FileBrowser extends Application {

	public static void main( String[] args ) {
		launch(args);
	}

	@Override
	public void start( Stage stage ) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/fxml/FileBrowser.fxml"));
		Scene scene = new Scene(root);

		stage.setTitle("File Browser");
		stage.setScene(scene);
		stage.show();
		
	}

}
