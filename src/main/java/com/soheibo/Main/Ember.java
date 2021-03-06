/*
 * Copyright 2017 Soheib El-Harrache.
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
package com.soheibo.Main;

import com.soheibo.Controller.MainController;
import com.soheibo.Model.DataModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main application.
 * 
 * @author Soheib El-Harrache
 */
public class Ember extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/FXML/main.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainController mainController
                = (MainController) fxmlLoader.getController();

        DataModel model = new DataModel();
        mainController.initModel(model);
        mainController.setStage(stage);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/main.css");
        stage.setTitle("Ember");
        stage.getIcons().add(new Image(
                "/images/EMBER-LOGO-COLOR.png"));
        stage.setScene(scene);
        stage.setMinHeight(550);
        stage.setMinWidth(550);
        stage.show();
        stage.toFront();
    }

}
