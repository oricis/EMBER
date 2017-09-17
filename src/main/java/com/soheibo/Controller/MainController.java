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

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.soheibo.Model.DataModel;
import com.soheibo.Model.Task;
import com.soheibo.Model.TaskList;
import com.soheibo.Model.TaskListManager;
import com.soheibo.View.NewTaskListWindow;
import com.soheibo.View.NewTaskWindow;
import com.soheibo.View.TaskListButton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Soheib El-Harrache
 */
public class MainController {

    private final double DISTANCE_BETWEEN_LISTS_BTNS = 25.0;
    private final double DISTANCE_BETWEEN_SEPARATOR = 30.0;
    private double lastLayoutYLists = 0;

    //GUI
    @FXML
    private AnchorPane leftMainAnchorPane;
    @FXML
    private AnchorPane taskListsAnchorPane;
    @FXML
    private ScrollPane taskListsScrollPane;
    @FXML
    private AnchorPane rightContentAnchorPane;
    @FXML
    private Text titleTaskList;
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton addTaskListButton;
    @FXML
    private JFXButton modifyTaskButton;
    @FXML
    private JFXButton deleteTaskButton;
    @FXML
    private JFXListView listView;

    //To modify stage from here
    private Stage stage;

    //Data
    private DataModel model;
    //Currently selected taskList
    private TaskListManager tlm;
    private TaskList currentTaskList;

    public void initModel(DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException(
                    "Model can only be initialized once");
        }

        this.model = model;
        this.tlm = model.getTaskListManager();

        graphicMods();

        addTaskViews();
        addListeners();
        currentTaskList = tlm.getCollectTaskList();
        updateContentPanel();
    }

    /**
     * Adds the necessary taskViews
     */
    private void addTaskViews() {

        addTaskListButton(tlm.getCollectTaskList());
        addSeparator();
        tlm.getBasicViews().forEach((taskList) -> {
            addTaskListButton(taskList);
        });
        addSeparator();
    }

    private void addListeners() {
        addButton.setOnAction((ActionEvent event) -> {
            try {
                addNewTask();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        });
        addTaskListButton.setOnAction((ActionEvent event) -> {
            try {
                addNewTaskList();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        });
        modifyTaskButton.setOnAction((ActionEvent event) -> {
            try {
                modifySelectedTask();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        });
        deleteTaskButton.setOnAction((ActionEvent event) -> {
            deleteSelectedTask();
        });
    }

    private void addNewTask() throws IOException {
        blurIn();
        NewTaskWindow ntWindow = new NewTaskWindow(false);
        ntWindow.showAndWait();
        blurOut();
        Task task = ntWindow.getTask();
        if (task == null) {
            //Rejected
        } else {
            //Added
            listView.getItems().add(task);
            currentTaskList.addTask(task);
        }
    }

    private void addNewTaskList() throws IOException {
        blurIn();
        NewTaskListWindow ntlWindow = new NewTaskListWindow();
        ntlWindow.showAndWait();
        TaskList taskList = ntlWindow.getTaskList();
        blurOut();
        if (taskList == null) {
            //Rejected
        } else {
            //Added
            tlm.addTaskList(taskList);
            addTaskListButton(taskList);
        }
    }

    private void addTaskListButton(TaskList taskList) {
        //Graphic
        TaskListButton newBtn = new TaskListButton(taskList);
        newBtn.setLayoutX(14.0);
        newBtn.setLayoutY(lastLayoutYLists + DISTANCE_BETWEEN_LISTS_BTNS);
        taskListsAnchorPane.getChildren().add(newBtn);

        lastLayoutYLists += DISTANCE_BETWEEN_LISTS_BTNS;

        newBtn.setOnAction((ActionEvent event) -> {
            currentTaskList = taskList;
            updateContentPanel();
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
        titleTaskList.setText(currentTaskList.getName());
        ArrayList<Task> alTasks = currentTaskList.getTskList();
        listView.getItems().clear();
        alTasks.forEach((t) -> {
            listView.getItems().add(t);
        });
    }

    private void modifySelectedTask() throws IOException {
        blurIn();
        Task oldTask = (Task) listView.getSelectionModel().getSelectedItem();
        if (oldTask != null) {
            NewTaskWindow ntWindow = new NewTaskWindow(false, oldTask);
            ntWindow.showAndWait();
            Task modifiedTask = ntWindow.getTask();

            if (modifiedTask == null) {
                //Rejected
            } else {
                //Added
                listView.getItems().remove(oldTask);
                currentTaskList.removeTask(oldTask);
                listView.getItems().add(modifiedTask);
                currentTaskList.addTask(modifiedTask);
                listView.refresh();
            }
        }
        blurOut();
    }

    private void deleteSelectedTask() {
        Task task = (Task) listView.getSelectionModel().getSelectedItem();
        if (task != null) {
            currentTaskList.removeTask(task);
        }
        updateContentPanel();
    }

    /**
     * Modifies the GUI.
     */
    private void graphicMods() {
        taskListsScrollPane.setFitToWidth(true);
        
        DropShadow shadow = new DropShadow();
        shadow.setOffsetY(1.0);
        shadow.setOffsetX(1.0);
        shadow.setColor(Color.GRAY);
        titleTaskList.setEffect(shadow);
        
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void blurIn() {
        GaussianBlur blur = new GaussianBlur(10);
        stage.getScene().getRoot().setEffect(blur);
    }
    
    private void blurOut() {
        GaussianBlur blur = new GaussianBlur(0);
        stage.getScene().getRoot().setEffect(blur);
    }
}
