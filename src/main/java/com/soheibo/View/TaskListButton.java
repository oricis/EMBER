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

import com.jfoenix.controls.JFXButton;
import com.soheibo.Model.TaskList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

/**
 *
 * @author Soheib El-Harrache
 */
public class TaskListButton extends JFXButton {
    
    private TaskList taskList;
    
    public TaskListButton(TaskList taskList) {
        this.setText(taskList.getName());
        AnchorPane.setLeftAnchor(this, 17.0);
        AnchorPane.setRightAnchor(this, 17.0);
        this.setFont(new Font("Arial Bold", 12.0));
    }

    //----------------GETTERS AND SETTERS--------------------------------------
    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }  
    
}
