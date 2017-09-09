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

import com.soheibo.Model.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Soheib El-Harrache
 */
public class TaskTest {

    private DataModel model;
    private TaskListManager tlm;
    private TaskList taskList;

    public TaskTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        model = new DataModel();
        tlm = model.getTaskListManager();
        taskList = new TaskList("Test");
        tlm.addTaskList(taskList);
    }

    @After
    public void tearDown() {
    }

    /**
     * Adding the task to a list.
     */
    @Test
    public void addingTaskToList() {
        String nameOfTask = "";

        Task task = new Task(nameOfTask);
        tlm.addTaskToList(task, taskList);

        assertTrue(taskList.getTskList().contains(task));
    }

    /**
     * Adding a list to the taskListManager.
     */
    @Test
    public void addingAListToTaskListManager() {
        String nameOfNewTaskList = "New list";

        TaskList newTaskList = new TaskList(nameOfNewTaskList);
        tlm.addTaskList(newTaskList);

        assertTrue(tlm.getMainTaskLists().contains(newTaskList));
    }

    /**
     * Verifies if the ID given to a new task is different.
     */
    @Test
    public void uniqueIDWhenAddingTask() {
        Task task1 = new Task("1");
        Task task2 = new Task("2");
        Task task3 = new Task("3");
        assertTrue(
                task1.getID() != task2.getID()
                && task1.getID() != task3.getID()
                && task2.getID() != task3.getID());
    }

    /**
     * Verifies if, after removing a task, the ID given to a new task is still
     * different.
     */
    @Test
    public void uniqueIDRemovingAndWhenAddingTask() {
        Task task1 = new Task("1");
        Task task2 = new Task("2");

        int idOfDeletedTask = task2.getID();
        //Removes the only reference to task2
        task2 = null;

        //Adding another task
        Task task3 = new Task("3");

        assertTrue(
                task1.getID() != idOfDeletedTask
                && task1.getID() != task3.getID()
                && idOfDeletedTask != task3.getID());
    }

    /**
     * Verifies if the ID given to a new taskList is different.
     */
    @Test
    public void uniqueIDWhenAddingTaskList() {
        TaskList taskList1 = new TaskList("1");
        TaskList taskList2 = new TaskList("2");
        TaskList taskList3 = new TaskList("3");

        assertTrue(
                taskList1.getID() != taskList2.getID()
                && taskList1.getID() != taskList3.getID()
                && taskList2.getID() != taskList3.getID());
    }

    /**
     * Verifies if, after removing a taskList, the ID given to a new taskList is
     * different.
     */
    @Test
    public void uniqueIDRemovingAndWhenAddingTaskList() {
        TaskList taskList1 = new TaskList("1");
        TaskList taskList2 = new TaskList("2");

        int idOfDeletedTaskList = taskList2.getID();
        //Removes the only reference to taskList2
        taskList2 = null;

        //Adding another taskList
        TaskList taskList3 = new TaskList("3");

        assertTrue(
                taskList1.getID() != idOfDeletedTaskList
                && taskList1.getID() != taskList3.getID()
                && idOfDeletedTaskList != taskList3.getID());
    }
}
