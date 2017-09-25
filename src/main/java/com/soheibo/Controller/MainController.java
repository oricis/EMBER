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

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.jfoenix.controls.JFXButton;
import com.soheibo.Model.DataModel;
import com.soheibo.Model.Task;
import com.soheibo.Model.TaskList;
import com.soheibo.Model.TaskListManager;
import com.soheibo.View.NewTaskListWindow;
import com.soheibo.View.NewTaskWindow;
import com.soheibo.View.TaskListButton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    private final double DISTANCE_BETWEEN_TASKS = 50.0;
    private double lastLayoutYLists = 0;

    private final String DEFAULT_FILE_NAME = "tasks.bin";

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
    private ScrollPane tasksScrollPane;
    @FXML
    private AnchorPane tasksAnchorPane;

    //To modify stage from here
    private Stage stage;

    //Data
    private DataModel model;
    private Kryo kryo;

    private TaskListManager tlm;
    //Currently selected taskList
    private TaskList currentTaskList;
    private TaskListButton currentTaskListButton;
    private ArrayList<TaskListButton> listOfTaskListBtns;

    public void initModel(DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException(
                    "Model can only be initialized once");
        }

        this.model = model;
        this.tlm = model.getTaskListManager();
        this.kryo = new Kryo();
        this.listOfTaskListBtns = new ArrayList<>();

        //Read from disk if possible
        if (new File(DEFAULT_FILE_NAME).isFile()) {
            loadFromDisk();
        }

        graphicMods();

        addTaskViews();
        addTaskLists();
        addListeners();

        setSelectedList(listOfTaskListBtns.get(0));
        updateContentPanel();
    }

    /**
     * Adds the necessary taskLists (called taskViews).
     */
    private void addTaskViews() {

        addTaskListButton(tlm.getCollectTaskList());
        addSeparator();
        tlm.getBasicViews().forEach((taskList) -> {
            addTaskListButton(taskList);
        });
        addSeparator();
    }

    /**
     * Adds custom taskLists.
     */
    private void addTaskLists() {
        tlm.getMainTaskLists().forEach((taskList) -> {
            addTaskListButton(taskList);
        });
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
            currentTaskList.addTask(task);
            updateContentPanel();
            saveOnDisk();
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
            saveOnDisk();
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
            setSelectedList(newBtn);
            updateContentPanel();
        });
        //TODO: complete
        //newBtn.setContextMenu(value);
        listOfTaskListBtns.add(newBtn);
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
        if (currentTaskList != null) {
            titleTaskList.setText(currentTaskList.getName());
            ArrayList<Task> alTasks = currentTaskList.getTskList();
            tasksAnchorPane.getChildren().clear();
            double distance = 10;
            for (Task t : alTasks) {          
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                            .getResource("/FXML/task.fxml")); 
                    Parent tc = (Parent)fxmlLoader.load();  
                    
                    //Alignements
                    AnchorPane.setLeftAnchor(tc, 10.0);
                    AnchorPane.setRightAnchor(tc, 10.0);
                    AnchorPane.setTopAnchor(tc, distance);
                    
                    //Set task
                    TaskComponentController controller = 
                            fxmlLoader.getController();
                    controller.setTask(t);
                    
                    tasksAnchorPane.getChildren().add(tc);
                    distance += DISTANCE_BETWEEN_TASKS;
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            }
        } else {
            titleTaskList.setText("Blank");
        }
    }

//    private void modifySelectedTask() throws IOException {
//        blurIn();
//        Task oldTask = (Task) listView.getSelectionModel().getSelectedItem();
//        if (oldTask != null) {
//            NewTaskWindow ntWindow = new NewTaskWindow(false, oldTask);
//            ntWindow.showAndWait();
//            Task modifiedTask = ntWindow.getTask();
//
//            if (modifiedTask == null) {
//                //Rejected
//            } else {
//                //Added
//                listView.getItems().remove(oldTask);
//                currentTaskList.removeTask(oldTask);
//                listView.getItems().add(modifiedTask);
//                currentTaskList.addTask(modifiedTask);
//                listView.refresh();
//            }
//        }
//        blurOut();
//    }
//    private void deleteSelectedTask() {
//        Task task = (Task) listView.getSelectionModel().getSelectedItem();
//        if (task != null) {
//            currentTaskList.removeTask(task);
//        }
//        updateContentPanel();
//    }
    /**
     * Modifies the GUI.
     */
    private void graphicMods() {
        taskListsScrollPane.setFitToWidth(true);
        tasksScrollPane.setFitToWidth(true);

        //Effect for the title
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

    /**
     * Saves on a file all tasks.
     */
    private void saveOnDisk() {
        Output output;
        try {
            output = new Output(new FileOutputStream(DEFAULT_FILE_NAME));
            kryo.writeObject(output, model);
            output.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private void loadFromDisk() {
        Input input;
        try {
            input = new Input(new FileInputStream(DEFAULT_FILE_NAME));
            this.model = kryo.readObject(input, DataModel.class);
            this.tlm = model.getTaskListManager();
            System.out.println("Reading complete.");
            input.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    private void setSelectedList(TaskListButton selectedTaskListButton) {
        TaskListButton ancientSelection = this.currentTaskListButton;
        if (ancientSelection != selectedTaskListButton) {
            currentTaskListButton = selectedTaskListButton;
            currentTaskList = selectedTaskListButton.getTaskList();
            //GUI
            if (ancientSelection != null) {
                ancientSelection.isClicked(false);
            }
            currentTaskListButton.isClicked(true);
        }
    }
}
