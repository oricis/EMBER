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
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the Task class.
 * 
 * @author Soheib El-Harrache
 */
public class TaskTest {

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
    }

    @After
    public void tearDown() {
    }

//    @Test
//    public void testGetID() {
//        Task task = new Task("Test");
//        int expResult = 0;
//        int result = task.getID();
//        assertEquals(expResult, result);
//    }
    //Should not set IDs
//    @Test
//    public void testSetID() {
//        int ID = 0;
//        Task task = new Task("Test");
//        task.setID(ID);
//        assertEquals(ID, task.getID());
//    }
    @Test
    public void testGetTitle() {
        String aTitle = "A certain title";
        Task task = new Task(aTitle);

        String result = task.getTitle();
        assertEquals(aTitle, result);
    }

    @Test
    public void testSetTitle() {
        String aTitle = "A certain title";
        Task task = new Task("Another title");

        task.setTitle(aTitle);
        assertEquals(aTitle, task.getTitle());
    }

    @Test
    public void testGetDescription() {
        Task task = new Task("Task");
        String expResult = "A description";

        task.setDescription(expResult);
        String result = task.getDescription();

        assertEquals(expResult, result);
    }

    @Test
    public void testSetDescription() {
        String description = "A description";
        Task task = new Task("Task");
        task.setDescription(description);

        assertEquals(description, task.getDescription());
    }

    //Date should be really close
    @Test
    public void testGetCreationDate() {
        Date closeToRightNow = new Date();
        Task task = new Task("To test dates");
        long diffInMillies = task.getCreationDate().getNano()
                - closeToRightNow.getTime();
        long errorMargin = 2000;
        assertTrue(diffInMillies < errorMargin);
    }
    
    @Test
    public void testGetLastModifiedDate() {
        LocalDateTime aNewDate = LocalDateTime.now();
        Task task = new Task("A modified task");
        task.setLastModifiedDate(aNewDate);
        assertEquals(aNewDate, task.getLastModifiedDate());
    }

    @Test
    public void testIsDoneDefault() {
        Task task = new Task("Normal");
        assertEquals(false, task.isDone());
    }
    
    @Test
    public void testSetDone() {
        Task task = new Task("Should be done!");
        task.setDone(true);
        assertTrue(task.isDone());
    }
    
    @Test
    public void testIsLimitedDefault() {
        Task task = new Task("Normal");
        assertEquals(false, task.isLimited());
    }

    @Test
    public void testSetLimited() {
        Task task = new Task("Should be limited");
        task.setLimited(true);
        assertTrue(task.isLimited());
    }

    @Test
    public void testGetEndDate() {
        Task task = new Task("With end date");
        LocalDateTime endDate = LocalDateTime.now();
        task.setEndDate(endDate);

        assertEquals(endDate, task.getEndDate());
    }
    
    @Test
    public void testGetEndDateDefault() {
        Task task = new Task("Without date");

        assertEquals(null, task.getEndDate());
    }

    @Test
    public void testSetEndDate() {
        Task task = new Task("With end date");
        LocalDateTime endDate = LocalDateTime.now();
        task.setEndDate(endDate);

        assertEquals(endDate, task.getEndDate());
    }

    @Test
    public void testIsRepetitiveDefault() {
        Task task = new Task("Normal");
        assertEquals(false, task.isRepetitive());
    }

    @Test
    public void testSetRepetitive() {
        Task task = new Task("Should be repetitive");
        task.setRepetitive(true);
        assertTrue(task.isRepetitive());
    }

    /**
     * Test of getNumberTasks method, of class Task.
     */
    @Test
    public void testGetNumberTasks() {
        Task task = new Task("Without tasks");
        assertEquals(0, task.getNumberTasks());
    }

    @Test
    public void testSetNumberTasks() {
        Task task = new Task("With a number of tasks");
        task.setNumberTasks(3);
        assertEquals(3, task.getNumberTasks());
    }

    @Test
    public void testGetTaskList() {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Task("A required task"));
        taskList.add(new Task("A required task 2"));
        Task mainTask = new Task("A parent task");
        mainTask.setTaskList(taskList);

        assertEquals(taskList, mainTask.getTaskList());
    }

    @Test
    public void testSetTaskList() {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Task("A required task"));
        taskList.add(new Task("A required task 2"));
        Task mainTask = new Task("A parent task");
        mainTask.setTaskList(taskList);

        assertEquals(taskList, mainTask.getTaskList());
    }

    @Test
    public void testGetRequiredTasks() {
        ArrayList<Task> requiredTasks = new ArrayList<>();
        requiredTasks.add(new Task("A required task"));
        requiredTasks.add(new Task("A required task 2"));
        Task mainTask = new Task("A parent task");
        mainTask.setRequiredTasks(requiredTasks);

        assertEquals(requiredTasks, mainTask.getRequiredTasks());
    }

    @Test
    public void testSetRequiredTasks() {
        ArrayList<Task> requiredTasks = new ArrayList<>();
        requiredTasks.add(new Task("A required task"));
        requiredTasks.add(new Task("A required task 2"));
        Task mainTask = new Task("A parent task");
        mainTask.setRequiredTasks(requiredTasks);

        assertEquals(requiredTasks, mainTask.getRequiredTasks());
    }

    @Test
    public void testToString() {
        String taskTitle = "Title";
        //TODO: Add new elements to task
        Task task = new Task(taskTitle);
        String expResult = "Title";
        String result = task.toString();

        assertEquals(expResult, result);
    }

    //Using a full constructor (TODO: update it with new conditions)
    @Test
    public void testFullTask() {
        String title = "Original Title";
        String description = "Long description here";
        boolean limited = false;
        LocalDateTime noEndDate = null;
        boolean repetitive = false;
        int numberOfTasks = 2;
        ArrayList<Task> list = new ArrayList<>();
        list.add(new Task("Random"));
        ArrayList<Task> requiredTasks = new ArrayList<>();
        requiredTasks.add(new Task("Random required"));
        
        Task task = new Task(title,
                description, limited,
                noEndDate, repetitive,
                numberOfTasks,
                list, 
                requiredTasks);
        
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(limited, task.isLimited());
        assertEquals(noEndDate, task.getEndDate());
        assertEquals(repetitive, task.isRepetitive());
        assertEquals(numberOfTasks, task.getNumberTasks());
        assertEquals(list, task.getTaskList());
        assertEquals(requiredTasks, task.getRequiredTasks());
        
    }

}
