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
package se.europeanspallationsource.xaos.demos.svgbrowser;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import org.controlsfx.control.PropertySheet;


/**
 * FXML Controller class
 *
 * @author claudio.rosati@esss.se
 */
@SuppressWarnings( "ClassWithoutLogger" )
public class SVGBrowserController implements Initializable {

	@FXML
	private TreeView<?> fileTree;
	@FXML
	private HBox pngContainer;
	@FXML
	private PropertySheet propertySheet;
	@FXML
	private HBox svgContainer;
	@FXML
	private TreeView<?> svgTree;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize( URL url, ResourceBundle rb ) {
		// TODO
	}

}
