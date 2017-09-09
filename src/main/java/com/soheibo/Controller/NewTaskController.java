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
import javafx.fxml.FXML;

/**
 *
 * @author Soheib El-Harrache
 */
public class NewTaskController {

    @FXML
    JFXTextField nameFieldNewTask;

    public Task getTask() {
        //Verifications
        if (!nameFieldNewTask.getText().equals("")) {
            return new Task(nameFieldNewTask.getText());
        } else {
            return null;
        }       
    }
}
