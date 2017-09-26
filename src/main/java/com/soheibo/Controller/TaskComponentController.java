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
package com.soheibo.Controller;

import com.soheibo.Model.Task;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 *
 * @author Soheib El-Harrache
 */
public class TaskComponentController implements Initializable {

    private Task task;
    private MainController mainController;

    @FXML
    private Text taskText;
    @FXML
    private Button modifyButton;
    @FXML
    private Button deleteButton;

    public void setTask(Task task) {
        this.task = task;
        taskText.setText(task.getTitle());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void addEvents() {
        modifyButton.setOnAction((ActionEvent event) -> {
            mainController.modifySelectedTask(task);
        });
        deleteButton.setOnAction((ActionEvent event) -> {
            mainController.deleteTask(task);
        });
    }
}
