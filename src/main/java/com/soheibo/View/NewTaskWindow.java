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

/**
 *
 * @author Soheib El-Harrache
 */
public class NewTaskWindow extends Stage {
    private NewTaskController taskController;
    
    public NewTaskWindow() throws IOException {
        super();
        
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/newTask.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        taskController
                = (NewTaskController) fxmlLoader.getController();
        
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Add a task");
        setResizable(false);
        
        //After pressing 'Enter', closes this window (which returns the value)
        scene.setOnKeyPressed((final KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
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
    
    public Task getTask() {
        return taskController.getTask();
    }
}
