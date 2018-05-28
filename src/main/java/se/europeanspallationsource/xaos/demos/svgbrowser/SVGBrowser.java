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
package se.europeanspallationsource.xaos.demos.svgbrowser;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.europeanspallationsource.xaos.components.SVG;


/**
 * A browser for SVG images.
 * <P>
 * The application allows to navigate the file system, select an {@code SVG} file,
 * and display a {@code PNG} version of the file along with the one generated
 * using {@link SVG}. The structure of the pure JavaFX version is displayed
 * too, along with the properties of any selected structure element.
 * <P>
 * To convert the {@code SVG} file into a {@code PNG} one, the {@code rsvg-convert}
 * command line application is used. It is part of the {@code librsvg}, that it is
 * expected being installed.
 *
 * @author claudio.rosati@esss.se
 * @see <a href="https://en.wikipedia.org/wiki/Librsvg">librsvg</a>
 * @see se.europeanspallationsource.xaos.components.SVG
 */
public class SVGBrowser extends Application {

	/**
	 * @param args the command line arguments
	 */
	public static void main( String[] args ) {
		launch(args);
	}

	@Override
	public void start( Stage primaryStage ) throws IOException {

		Parent root = FXMLLoader.load(SVGBrowser.class.getResource("/fxml/SVGBrowser.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setTitle("SVG Browser");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
