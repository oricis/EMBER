/*
 * Copyright 2017 .
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

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.soheibo.Model.DataModel;
import com.soheibo.Model.Task;
import com.soheibo.Model.TaskList;
import com.soheibo.Model.TaskListManager;
import com.soheibo.View.NewTaskListWindow;
import com.soheibo.View.NewTaskWindow;
import com.soheibo.View.TaskListButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Soheib El-Harrache
 */
public class MainController implements Initializable {

    private final double DISTANCE_BETWEEN_LISTS_BTNS = 25.0;
    private final double DISTANCE_BETWEEN_SEPARATOR = 30.0;
    private double lastLayoutYLists = 0;

    //GUI
    @FXML
    private AnchorPane taskListsAnchorPane;
    @FXML
    private AnchorPane rightContentAnchorPane;
    @FXML
    private Label titleTaskListContent;
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton addTaskListButton;
    @FXML
    private JFXListView listView;
           
    private DataModel model;
    //Currently selected taskList
    private TaskListManager tlm;
    private TaskList currentTaskList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Blank for now
    }

    public void initModel(DataModel model) {
        this.model = model;
        this.tlm = model.getTaskListManager();

        addTaskViews();
        addListeners();
        currentTaskList = tlm.getTaskListFromName("Collect");
        updateContentPanel();
    }

    private void addTaskViews() {
        addTaskListButton("Collect");
        addSeparator();
        addTaskListButton("Today");
        addTaskListButton("Upcoming");
        addTaskListButton("Anytime");
        addTaskListButton("Future");
        addSeparator();
        addTaskListButton("Exemple");
    }

    private void addListeners() {
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    addNewTask();
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        });
        addTaskListButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    addNewTaskList();
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addNewTask() throws IOException {
        NewTaskWindow ntWindow = new NewTaskWindow();
        ntWindow.showAndWait();
        String taskTitle = ntWindow.getTaskTitle();
        if (taskTitle == null || taskTitle.equals("")) {
            //Rejected
        } else {
            //Added
            Task t = new Task(tlm.getNewID(), taskTitle);
            listView.getItems().add(t);
            currentTaskList.addTask(t);
        }
    }
    
    private void addNewTaskList() throws IOException {
        NewTaskListWindow ntlWindow = new NewTaskListWindow();
        ntlWindow.showAndWait();
        String taskListTitle = ntlWindow.getTaskListTitle();
        if (taskListTitle == null || taskListTitle.equals("")) {
            //Rejected
        } else {
            //Added
            addTaskListButton(taskListTitle);
        }
    }

    private void addTaskListButton(String name) {
        //Logic
        TaskList newTaskList = new TaskList(tlm.getNewID(), name);
        tlm.addTaskList(newTaskList);
        //Graphic
        TaskListButton newBtn = new TaskListButton(newTaskList);
        newBtn.setLayoutX(14.0);
        newBtn.setLayoutY(lastLayoutYLists + DISTANCE_BETWEEN_LISTS_BTNS);
        taskListsAnchorPane.getChildren().add(newBtn);

        lastLayoutYLists += DISTANCE_BETWEEN_LISTS_BTNS;
        
        newBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentTaskList = newTaskList;
                updateContentPanel();
            }
        });
    }

    private void addSeparator() {
        Separator separator = new Separator();
        AnchorPane.setLeftAnchor(separator, 17.0);
        AnchorPane.setRightAnchor(separator, 17.0);
        separator.setLayoutY(lastLayoutYLists + DISTANCE_BETWEEN_SEPARATOR);
        separator.setPrefWidth(200.0);
        taskListsAnchorPane.getChildren().add(separator);
        lastLayoutYLists += 15;
    }

    private void updateContentPanel() {
        titleTaskListContent.setText(currentTaskList.getName());
        ArrayList<Task> alTasks = currentTaskList.getTskList();
        listView.getItems().clear();
        for (Task t : alTasks) {
            listView.getItems().add(t);
        }
    }   
}
