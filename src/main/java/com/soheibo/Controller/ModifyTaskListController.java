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
import com.soheibo.Model.TaskList;
import javafx.fxml.FXML;

/**
 * Controller for the view used to modify task lists.
 * 
 * @author Soheib El-Harrache
 */
public class ModifyTaskListController {
    @FXML
    JFXTextField nameFieldTaskList;
    
    //Task list to modify
    private TaskList taskList;
    
    /**
     * Creates and returns a modified task list if valid.
     * Returns null if not valid.
     * @return Null if not valid or the modified task list if valid.
     */
    public TaskList getModifiedTaskList() {
        //Verifications
        if (!nameFieldTaskList.getText().equals("")) {
            taskList.setName(nameFieldTaskList.getText());
            return taskList;
        } else {
            return null;
        }
    }

    /**
     * Fills the fields using informations from a task list passed by parameter.
     * @param oldTaskList List used to retrieve informations and fill fields.
     */
    public void fillOldTaskListInfos(TaskList oldTaskList) {
        this.taskList = oldTaskList;
        nameFieldTaskList.setText(oldTaskList.getName());
    }
}
