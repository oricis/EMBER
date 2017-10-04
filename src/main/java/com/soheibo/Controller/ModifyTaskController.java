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

import com.jfoenix.controls.JFXTextField;
import com.soheibo.Model.Task;
import java.time.LocalDateTime;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for the view used to modify tasks.
 * 
 * @author Soheib El-Harrache
 */
public class ModifyTaskController {
    @FXML
    JFXTextField nameFieldNewTask;
    
    @FXML
    AnchorPane taskDetailsAnchorPane;

    //Task to modify
    Task task;
    
    /**
     * Creates and returns a modified task if valid. Returns null if not valid.
     * @return Null if not valid or the modified task if valid.
     */
    public Task getTask() {
        //Verifications
        if (!nameFieldNewTask.getText().equals("")) {
            task.setTitle(nameFieldNewTask.getText());
            task.setLastModifiedDate(LocalDateTime.now());
            return task;
        } else {
            return null;
        }
    }

    /**
     * Fills the fields using informations from a task passed by parameter.
     * @param oldTask Task used to retrieve informations and fill fields.
     */
    public void fillOldTaskInfos(Task oldTask) {
        task = oldTask;
        taskDetailsAnchorPane.setVisible(true);
        nameFieldNewTask.setText(oldTask.getTitle());
    }

    /**
     * Applies subtle graphical modifications.
     */
    public void applyGUIMods() {
        //taskDetailsAnchorPane.managedProperty().bind(taskDetailsAnchorPane.
        //visibleProperty());
    }
}
