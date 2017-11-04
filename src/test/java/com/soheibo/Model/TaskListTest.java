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
package com.soheibo.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the TaskList class.
 * 
 * @author Soheib El-Harrache
 */
public class TaskListTest {

    private DataModel model;
    private TaskListManager tlm;
    private TaskList taskList;

    public TaskListTest() {
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

    @Test
    public void testTaskListDefaultValues() {
        TaskList normalList = new TaskList();
        
        assertEquals(normalList.getTskList().size(), 0);
        assertFalse(normalList.isNecessary());
    }
    /**
     * Adding the task to a list.
     */
    @Test
    public void testAddingTaskToList() {
        String nameOfTask = "A name";

        Task task = new Task(nameOfTask);
        tlm.addTaskToList(task, taskList);

        assertTrue(taskList.getTskList().contains(task));
    }

    @Test
    public void testRemovingTaskFromList() {
        String nameOfTask = "To remove";

        Task task = new Task(nameOfTask);
        tlm.addTaskToList(task, taskList);
        tlm.removeTaskFromList(task, taskList);

        assertFalse(taskList.getTskList().contains(task));
    }

    /**
     * Adding a list to the taskListManager.
     */
    @Test
    public void testAddingAListToTaskListManager() {
        String nameOfNewTaskList = "New list";

        TaskList newTaskList = new TaskList(nameOfNewTaskList);
        tlm.addTaskList(newTaskList);

        assertTrue(tlm.getMainTaskLists().contains(newTaskList));
    }

    @Test
    public void testRemoveAListFromTaskListManager() {
        String nameOfNewTaskList = "Temporary List";

        TaskList newTaskList = new TaskList(nameOfNewTaskList);
        tlm.addTaskList(newTaskList);
        tlm.removeTaskList(newTaskList);

        assertFalse(tlm.getMainTaskLists().contains(newTaskList));
    }

    @Test
    public void testTaskListNameUsed() {
        String nameOfNewTaskList = "A name used";

        TaskList newTaskList = new TaskList(nameOfNewTaskList);
        tlm.addTaskList(newTaskList);
        assertTrue(tlm.listNameUsed(nameOfNewTaskList));
    }

    @Test
    public void testTaskListNameNotUsed() {
        String nameOfNewTaskList = "A name not used";

        assertFalse(tlm.listNameUsed(nameOfNewTaskList));
    }

    /**
     * Verifies if the ID given to a new task is different.
     */
    @Test
    public void testUniqueIDWhenAddingTask() {
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
    public void testUniqueIDRemovingAndWhenAddingTask() {
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
    public void testUniqueIDWhenAddingTaskList() {
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
    public void testUniqueIDRemovingAndWhenAddingTaskList() {
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

    @Test
    public void testCloningTaskReplacesWithSameValues() {
        Task original = new Task();

        Task newTask = new Task();
        newTask.setTitle("A new List");
        newTask.setDescription("A beautiful description");
        newTask.setDone(true);
        newTask.setLimited(true);
        newTask.setLastModifiedDate(LocalDateTime.now());
        newTask.setEndDate(LocalDateTime.now());
        newTask.setRepetitive(true);
        newTask.setNumberTasks(3);

        //Adding required tasks in the task
        Task subRequiredTask1 = new Task("TestR1");
        Task subRequiredTask2 = new Task("TestR2");
        ArrayList<Task> requiredSubTasks = new ArrayList<>();
        requiredSubTasks.add(subRequiredTask1);
        requiredSubTasks.add(subRequiredTask2);
        newTask.setRequiredTasks(requiredSubTasks);

        //Adding tasks in the task
        Task subTask1 = new Task("Test1");
        Task subTask2 = new Task("Test2");
        ArrayList<Task> subTasks = new ArrayList<>();
        subTasks.add(subTask1);
        subTasks.add(subTask2);
        newTask.setTaskList(subTasks);

        TaskListManager.modifyTask(original, newTask);

        //Comparing values in both tasks
        testValuesEqualsBetween2Tasks(original, newTask);

        //Comparing values in required sub-tasks
        for (int i = 0; i < requiredSubTasks.size(); i++) {
            testValuesEqualsBetween2Tasks(
                    original.getRequiredTasks().get(i),
                    newTask.getRequiredTasks().get(i));
        }

        //Comparing values in sub-tasks
        for (int i = 0; i < subTasks.size(); i++) {
            testValuesEqualsBetween2Tasks(
                    original.getTaskList().get(i),
                    newTask.getTaskList().get(i));
        }
    }

    /**
     * Asserts the values of 2 tasks are equal (except sub-lists).
     *
     * @param original A task used to assert.
     * @param newTask Another task used to assert.
     */
    public void testValuesEqualsBetween2Tasks(Task original, Task newTask) {
        assertEquals(original.getID(), newTask.getID());
        assertTrue(original.getTitle().equals(newTask.getTitle()));
        assertTrue(original.getDescription().equals(newTask.getDescription()));
        assertEquals(original.isLimited(), newTask.isLimited());
        assertEquals(original.isDone(), newTask.isDone());
        assertEquals(original.isLimited(), newTask.isLimited());
        assertTrue(original.getCreationDate().
                equals(newTask.getCreationDate()));
        if (newTask.getLastModifiedDate() != null) {
            assertTrue(original.getLastModifiedDate().
                    equals(newTask.getLastModifiedDate()));
        } else {
            assertEquals(original.getLastModifiedDate(),
                    newTask.getLastModifiedDate());
        }
        assertEquals(original.isRepetitive(), newTask.isRepetitive());
        assertEquals(original.getNumberTasks(), newTask.getNumberTasks());
    }

    //GETTERS AND SETTERS-------------------------------------------------------
    //By default shouldn't be necessary
    @Test
    public void testIsNecessaryDefault() {
        TaskList unnecessaryTaskList = new TaskList("Not necessary");
        assertFalse(unnecessaryTaskList.isNecessary());
    }

    @Test
    public void testSetName() {
        TaskList renamingThisTaskList = new TaskList("Ugly name");
        String aSexyName = "Beautiful name";
        renamingThisTaskList.setName(aSexyName);
        assertEquals(renamingThisTaskList.getName(), aSexyName);
    }

    @Test
    public void testSetTaskList() {
        TaskList aTaskList = new TaskList("Needs some change");
        ArrayList<Task> refreshingChangeList = new ArrayList<>();
        aTaskList.setTskList(refreshingChangeList);

        assertEquals(refreshingChangeList, aTaskList.getTskList());
    }

}
