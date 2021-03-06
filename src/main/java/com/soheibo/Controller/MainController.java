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
import com.soheibo.View.ModifyTaskListWindow;
import com.soheibo.View.ModifyTaskWindow;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Main controller used to link the model and views.
 * 
 * @author Soheib El-Harrache
 */
public class MainController {

    private final double DISTANCE_BETWEEN_LISTS_BTNS = 25.0;
    private final double DISTANCE_BETWEEN_SEPARATOR = 30.0;
    private final double DISTANCE_BETWEEN_TASKS = 50.0;

    private final int BLUR_INTENSITY = 10;
    
    private final String DEFAULT_FILE_NAME = "tasks.bin";

    //Used to vertically align lists and separators
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

    /**
     * Initiliazes the model and builds the interface. Can only be done when 
     * the model is null.
     * @param model Model to be used to initialiaze.
     */
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

        addListeners();
        updateTaskListsPanel();       

        setSelectedListButton(listOfTaskListBtns.get(0));
        updateContentPanel();
    }

    /**
     * Adds the necessary task lists (called task views).
     */
    private void addTaskViews() {

        addTaskListButton(tlm.getCollectTaskList(), false, false);
        addSeparator();
        tlm.getBasicViews().forEach((taskList) -> {
            addTaskListButton(taskList, true, false);
        });
        addSeparator();

        //Today
        listOfTaskListBtns.get(1).setOnAction((ActionEvent event) -> {
            addButton.setVisible(false);
            setSelectedListButton(listOfTaskListBtns.get(1));
            listOfTaskListBtns.get(1).getTaskList()
                    .setTskList(tlm.getViewTodayTasks());
            updateContentPanel();
        });
        //Upcoming
        listOfTaskListBtns.get(2).setOnAction((ActionEvent event) -> {
            addButton.setVisible(false);
            setSelectedListButton(listOfTaskListBtns.get(2));
            listOfTaskListBtns.get(2).getTaskList()
                    .setTskList(tlm.getViewUpcomingTasks());
            updateContentPanel();
        });
        //Anytime       
        listOfTaskListBtns.get(3).setOnAction((ActionEvent event) -> {
            addButton.setVisible(false);
            setSelectedListButton(listOfTaskListBtns.get(3));
            listOfTaskListBtns.get(3).getTaskList()
                    .setTskList(tlm.getViewAnytimeTasks());
            updateContentPanel();
        });
        //Future
        listOfTaskListBtns.get(4).setOnAction((ActionEvent event) -> {
            addButton.setVisible(false);
            setSelectedListButton(listOfTaskListBtns.get(4));
            listOfTaskListBtns.get(4).getTaskList()
                    .setTskList(tlm.getViewFutureTasks());
            updateContentPanel();
        });
    }

    /**
     * Adds lists of tasks created by the user.
     */
    private void addTaskLists() {
        tlm.getMainTaskLists().forEach((taskList) -> {
            addTaskListButton(taskList, false, true);
        });
    }

    /**
     * Adds listeners on interface buttons.
     */
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

    /**
     * Shows a dialog to add a task and treats the input.
     * Updates visually and saves on disk.
     * @throws IOException 
     */
    private void addNewTask() throws IOException {
        blurIn();
        NewTaskWindow ntWindow = new NewTaskWindow();
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

    /**
     * Shows a dialog to add a list of tasks and treats the input.
     * Updates visually and saves on disk.
     * @throws IOException 
     */
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
            addTaskListButton(taskList, false, true);
            saveOnDisk();
        }
    }

    /**
     * Adds a task list button on the left panel as the next element.
     * @param taskList Task list associated with the button.
     * @param viewRestricted If true, cannot manually add tasks to the list.
     * @param canBeModified If true, has a context menu to modify and delete.
     */
    private void addTaskListButton(TaskList taskList,
            boolean viewRestricted, boolean canBeModified) {
        //Graphic
        TaskListButton newBtn = new TaskListButton(taskList);
        newBtn.setLayoutX(14.0);
        newBtn.setLayoutY(lastLayoutYLists + DISTANCE_BETWEEN_LISTS_BTNS);
        taskListsAnchorPane.getChildren().add(newBtn);

        lastLayoutYLists += DISTANCE_BETWEEN_LISTS_BTNS;

        newBtn.setOnAction((ActionEvent event) -> {
            if (viewRestricted) {
                addButton.setVisible(false);
            } else {
                addButton.setVisible(true);
            }
            setSelectedListButton(newBtn);
            updateContentPanel();
        });

        if (canBeModified) {
            //Context Menu
            newBtn.setContextMenu(createTaskListContextMenu(newBtn));
        }

        listOfTaskListBtns.add(newBtn);
    }

    /**
     * Creates and returns a context menu for task lists and adds events
     * referencing the button passed as parameter.
     * @param btn The button used as reference for events.
     * @return Context menu created.
     */
    private ContextMenu createTaskListContextMenu(TaskListButton btn) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem itemModify = new MenuItem("Modify");
        itemModify.setOnAction((ActionEvent e) -> {
            modifyTaskList(btn);
        });
        
        MenuItem itemDelete = new MenuItem("Delete");
        itemDelete.setOnAction((ActionEvent e) -> {
            deleteTaskList(btn.getTaskList());
        });
        
        contextMenu.getItems().addAll(itemModify, itemDelete);
        return contextMenu;
    }

    /**
     * Adds a separator on the list of tasks as the next element.
     */
    private void addSeparator() {
        Separator separator = new Separator();
        AnchorPane.setLeftAnchor(separator, 17.0);
        AnchorPane.setRightAnchor(separator, 17.0);
        separator.setLayoutY(lastLayoutYLists + DISTANCE_BETWEEN_SEPARATOR);
        separator.setPrefWidth(200.0);
        taskListsAnchorPane.getChildren().add(separator);
        lastLayoutYLists += 15;
    }

    /**
     * Updates content on right panel by loading tasks and changing
     * the title.
     * TODO: Improve loading performance.
     */
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
                    Parent tc = (Parent) fxmlLoader.load();

                    //Alignements
                    AnchorPane.setLeftAnchor(tc, 10.0);
                    AnchorPane.setRightAnchor(tc, 10.0);
                    AnchorPane.setTopAnchor(tc, distance);

                    //Set task
                    TaskComponentController controller
                            = fxmlLoader.getController();
                    controller.setTask(t);
                    controller.setMainController(this);
                    controller.addEvents();

                    //Saves performance
                    tc.setCache(true);
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

    /**
     * Shows a dialog to modify a task and treats the input.
     * Updates visually and saves on disk.
     * @param oldTask The old task to modify.
     */
    public void modifySelectedTask(Task oldTask) {
        blurIn();

        if (oldTask != null) {
            ModifyTaskWindow mWindow;
            try {
                mWindow = new ModifyTaskWindow(oldTask);
                mWindow.showAndWait();
                Task modifiedTask = mWindow.getTask();

                if (modifiedTask == null) {
                    //Rejected
                } else {
                    //Accepted
                    TaskListManager.modifyTask(oldTask, modifiedTask);
                    updateContentPanel();
                    saveOnDisk();
                }
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
        blurOut();
    }

    /**
     * Deletes a task from model, updates visual appearance and
     * saves on disk.
     * @param t 
     */
    public void deleteTask(Task t) {
        tlm.removeTaskFromList(t, currentTaskList);
        updateContentPanel();
        saveOnDisk();
    }
    
    /**
     * Shows a dialog to modify the list of tasks and treats the input.
     * Updates visually and saves on disk.
     * @param tlb The button used to modify the list.
     */
    public void modifyTaskList(TaskListButton tlb) {       
        TaskList oldTaskList = tlb.getTaskList();
        blurIn();

        if (oldTaskList != null) {
            ModifyTaskListWindow mlWindow;
            try {
                mlWindow = new ModifyTaskListWindow(oldTaskList);
                mlWindow.showAndWait();
                TaskList modifiedTaskList = mlWindow.getTaskList();

                if (modifiedTaskList == null) {
                    //Rejected
                } else {
                    //Accepted
                    tlb.setTaskList(modifiedTaskList);
                    updateTaskListsPanel();
                    updateContentPanel();
                    saveOnDisk();
                }
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
        blurOut();
    }
    
    /**
     * Deletes a task lists from model, updates visual appearance and
     * saves on disk.
     * @param tl The task list to delete.
     */
    public void deleteTaskList(TaskList tl) {
        tlm.removeTaskList(tl);
        updateTaskListsPanel();       
        
        setSelectedListButton(listOfTaskListBtns.get(0));
        updateContentPanel();
        saveOnDisk();
    }
    
    /**
     * Updates visually the panel with lists of tasks.
     */
    public void updateTaskListsPanel() {
        lastLayoutYLists = 0;
        
        taskListsAnchorPane.getChildren().clear();
        addTaskViews();
        addTaskLists();      
    }

    /**
     * Modifies the GUI with minor fixes and styles.
     */
    private void graphicMods() {
        taskListsScrollPane.setFitToWidth(true);
        tasksScrollPane.setFitToWidth(true);

        //Colors
        tasksScrollPane.setStyle("-fx-background-color: transparent;");
        tasksAnchorPane.setStyle("-fx-background-color: white;");

        //Effect for the title
        DropShadow shadow = new DropShadow();
        shadow.setOffsetY(1.0);
        shadow.setOffsetX(1.0);
        shadow.setColor(Color.GRAY);
        titleTaskList.setEffect(shadow);
    }

    /**
     * Sets the current stage.
     * @param stage Stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Adds a blur effect on stage.
     */
    private void blurIn() {
        GaussianBlur blur = new GaussianBlur(BLUR_INTENSITY);
        stage.getScene().getRoot().setEffect(blur);
    }

    /**
     * Removes the blur effect on stage.
     */
    private void blurOut() {
        GaussianBlur blur = new GaussianBlur(0);
        stage.getScene().getRoot().setEffect(blur);
    }

    /**
     * Saves on a file the whole model.
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

    /**
     * Loads the model from disk.
     */
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
        } catch (Exception ex) {
            System.out.println("Reading tasks file incomplete.");           
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE,
                    null, ex);
            System.exit(1);
        }
    }

    /**
     * Sets a taskListButton as selected both visually and in the controller.
     * @param selectedTaskListButton The button of a list to select.
     */
    private void setSelectedListButton(TaskListButton selectedTaskListButton) {
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
