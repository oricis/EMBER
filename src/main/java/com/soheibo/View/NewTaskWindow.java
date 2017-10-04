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
package com.soheibo.View;

import com.soheibo.Controller.NewTaskController;
import com.soheibo.Model.Task;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * View used to add tasks.
 * 
 * @author Soheib El-Harrache
 */
public class NewTaskWindow extends Stage {

    private NewTaskController taskController;
    private boolean wantsToAdd;

    /**
     * Builds the window and add events.
     * @throws IOException 
     */
    public NewTaskWindow() throws IOException {
        super();

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/FXML/newTask.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        taskController
                = (NewTaskController) fxmlLoader.getController();
        taskController.applyGUIMods();
        
        Scene scene = new Scene(root);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        setScene(scene);
        setTitle("Add a task");
        setResizable(false);
        initStyle(StageStyle.TRANSPARENT);

        this.wantsToAdd = false;
        //After pressing 'Enter', closes this window (which returns the value)
        scene.setOnKeyPressed((final KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                this.wantsToAdd = true;
                this.close();
            } else if (keyEvent.getCode() == KeyCode.ALT) {
                //Using alt key since tab doesn't work
                taskController.showDetails();
            } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
                this.close();
            }
        });

        //Not focusing the window means closing it
        focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                close();
            }
        });
    }

    /**
     * Returns a task from the controller if the condition is met.
     * @return Task from the controller.
     */
    public Task getTask() {
        if (this.wantsToAdd) {
            return taskController.getTask();
        } else {
            return null;
        }
    }
}
