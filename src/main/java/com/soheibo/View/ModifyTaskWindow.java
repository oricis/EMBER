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

import com.soheibo.Controller.ModifyTaskController;
import com.soheibo.Model.Task;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Soheib El-Harrache
 */
public class ModifyTaskWindow extends Stage {
    
    private ModifyTaskController taskController;
    private boolean wantsToAdd;
    
    public ModifyTaskWindow(Task oldTask)
            throws IOException {
        super();

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/modifyTask.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        taskController
                = (ModifyTaskController) fxmlLoader.getController();
        
        Scene scene = new Scene(root);
        setScene(scene);
        setResizable(false);
        initStyle(StageStyle.TRANSPARENT);
        taskController.fillOldTaskInfos(oldTask);
        
        //After pressing 'Enter', closes this window (which returns the value)
        scene.setOnKeyPressed((final KeyEvent keyEvent) -> {
            if (null != keyEvent.getCode()) switch (keyEvent.getCode()) {
                case ENTER:
                    this.wantsToAdd = true;
                    this.close();
                    break;
                case ESCAPE:
                    this.close();
                    break;
                default:
                    break;
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
        if (this.wantsToAdd) {
            return taskController.getTask();
        } else {
            return null;
        }
    }
}
