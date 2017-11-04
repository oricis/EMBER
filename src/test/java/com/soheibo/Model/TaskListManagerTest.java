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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the TaskListManager class. Mostly about timing.
 *
 * @Warning: Running tests at a specified time between days might break tests.
 * Shouldn't run them near 23h59. Sometimes, performance changes timestamps.
 * @author Soheib El-Harrache
 */
public class TaskListManagerTest {

    private DataModel model;
    private TaskListManager tlm;

    public TaskListManagerTest() {
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
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testTodayTasks() {
        //Temporary task list
        TaskList localTaskList = new TaskList("TestTodayTasks");
        tlm.addTaskList(localTaskList);

        LocalDateTime nowDate = LocalDateTime.now();

        //Today tasks
        Task todayTask1 = new Task();
        Task todayTask2 = new Task();
        Task todayTask3 = new Task();

        todayTask1.setEndDate(nowDate);
        todayTask2.setEndDate(nowDate);
        todayTask3.setEndDate(nowDate);

        //Tomorrow tasks
        Task tomorrowTask = new Task();
        tomorrowTask.setEndDate(nowDate.plusDays(1).plusMinutes(1));

        //Yesterday tasks
        Task yesterdayTask = new Task();
        yesterdayTask.setEndDate(nowDate.minusDays(1).minusMinutes(1));

        //Anytime tasks
        Task anytimeTask = new Task();

        tlm.addTaskToList(todayTask1, localTaskList);
        tlm.addTaskToList(todayTask2, localTaskList);
        tlm.addTaskToList(todayTask3, localTaskList);
        tlm.addTaskToList(tomorrowTask, localTaskList);
        tlm.addTaskToList(yesterdayTask, localTaskList);
        tlm.addTaskToList(anytimeTask, localTaskList);

        ArrayList<Task> outputList = tlm.getViewTodayTasks();

        assertEquals(3, outputList.size());

        long distanceDays;
        for (Task t : outputList) {
            distanceDays = ChronoUnit.DAYS.between(
                    nowDate.toLocalDate().atStartOfDay(),
                    t.getEndDate());
            assertTrue(distanceDays == 0);
        }

        //To keep this list only for the test
        tlm.removeTaskList(localTaskList);
    }

    @Test
    public void testTomorrowTasks() {
        //Temporary task list
        TaskList localTaskList = new TaskList("TestTomorrowTasks");
        tlm.addTaskList(localTaskList);

        LocalDateTime nowDate = LocalDateTime.now();

        //Today tasks
        Task todayTask = new Task();
        todayTask.setEndDate(nowDate);

        //Tomorrow tasks
        Task tomorrowTask1 = new Task();
        Task tomorrowTask2 = new Task();
        Task tomorrowTask3 = new Task();
        tomorrowTask1.setEndDate(nowDate.plusDays(1).plusMinutes(1));
        tomorrowTask2.setEndDate(nowDate.plusDays(1).plusMinutes(1));
        tomorrowTask3.setEndDate(nowDate.plusDays(1).plusMinutes(1));

        //Yesterday tasks
        Task yesterdayTask = new Task();
        yesterdayTask.setEndDate(nowDate.minusDays(1));

        //Anytime tasks
        Task anytimeTask = new Task();

        tlm.addTaskToList(todayTask, localTaskList);
        tlm.addTaskToList(tomorrowTask1, localTaskList);
        tlm.addTaskToList(tomorrowTask2, localTaskList);
        tlm.addTaskToList(tomorrowTask3, localTaskList);
        tlm.addTaskToList(yesterdayTask, localTaskList);
        tlm.addTaskToList(anytimeTask, localTaskList);

        ArrayList<Task> outputList = tlm.getViewTomorrowTasks();

        assertEquals(3, outputList.size());

        long distanceDays;
        for (Task t : outputList) {
            distanceDays = ChronoUnit.DAYS.between(
                    nowDate.toLocalDate().atStartOfDay(),
                    t.getEndDate());
            assertTrue(distanceDays == 1);
        }

        //To keep this list only for the test
        tlm.removeTaskList(localTaskList);
    }

    @Test
    public void testAnytimeTasks() {
        //Temporary task list
        TaskList localTaskList = new TaskList("TestAnytimeTasks");
        tlm.addTaskList(localTaskList);

        LocalDateTime nowDate = LocalDateTime.now();

        //Today tasks
        Task todayTask = new Task();
        todayTask.setEndDate(nowDate);

        //Tomorrow tasks
        Task tomorrowTask = new Task();
        tomorrowTask.setEndDate(nowDate.plusDays(1));

        //Yesterday tasks
        Task yesterdayTask = new Task();
        yesterdayTask.setEndDate(nowDate.minusDays(1));

        //Anytime tasks
        Task anytimeTask1 = new Task();
        Task anytimeTask2 = new Task();
        Task anytimeTask3 = new Task();

        tlm.addTaskToList(todayTask, localTaskList);
        tlm.addTaskToList(tomorrowTask, localTaskList);
        tlm.addTaskToList(yesterdayTask, localTaskList);
        tlm.addTaskToList(anytimeTask1, localTaskList);
        tlm.addTaskToList(anytimeTask2, localTaskList);
        tlm.addTaskToList(anytimeTask3, localTaskList);

        ArrayList<Task> outputList = tlm.getViewAnytimeTasks();

        assertEquals(outputList.size(), 3);

        for (Task t : outputList) {
            assertEquals(null, t.getEndDate());
        }

        //To keep this list only for the test
        tlm.removeTaskList(localTaskList);
    }

    @Test
    public void testUpcomingTasks() {
        //Temporary task list
        TaskList localTaskList = new TaskList("TestUpcomingTasks");
        tlm.addTaskList(localTaskList);

        LocalDateTime nowDate = LocalDateTime.now();

        //Today tasks
        Task todayTask = new Task();
        todayTask.setEndDate(nowDate);

        //Tomorrow tasks
        Task tomorrowTask = new Task();
        tomorrowTask.setEndDate(nowDate.plusDays(1));

        //Yesterday tasks
        Task yesterdayTask = new Task();
        yesterdayTask.setEndDate(nowDate.minusDays(1));

        //Anytime tasks
        Task anytimeTask = new Task();

        //Upcoming tasks
        Task upcomingTask1 = new Task();
        upcomingTask1.setEndDate(nowDate.plusDays(2));
        Task upcomingTask2 = new Task();
        upcomingTask2.setEndDate(nowDate.plusDays(3));
        Task upcomingTask3 = new Task();
        upcomingTask3.setEndDate(nowDate.plusDays(4));

        //Future tasks
        Task futureTask1 = new Task();
        futureTask1.setEndDate(nowDate.plusDays(7));
        Task futureTask2 = new Task();
        futureTask2.setEndDate(nowDate.plusDays(20));

        tlm.addTaskToList(todayTask, localTaskList);
        tlm.addTaskToList(tomorrowTask, localTaskList);
        tlm.addTaskToList(yesterdayTask, localTaskList);
        tlm.addTaskToList(anytimeTask, localTaskList);
        tlm.addTaskToList(upcomingTask1, localTaskList);
        tlm.addTaskToList(upcomingTask2, localTaskList);
        tlm.addTaskToList(upcomingTask3, localTaskList);
        tlm.addTaskToList(futureTask1, localTaskList);
        tlm.addTaskToList(futureTask2, localTaskList);

        ArrayList<Task> outputList = tlm.getViewUpcomingTasks();

        assertEquals(5, outputList.size());

        long distanceDays, distanceSeconds;
        for (Task t : outputList) {
            distanceDays
                    = ChronoUnit.DAYS.between(
                            nowDate.toLocalDate().atStartOfDay(),
                            t.getEndDate());
            distanceSeconds = ChronoUnit.SECONDS.between(
                    nowDate.toLocalDate().atStartOfDay(),
                    t.getEndDate());
            assertTrue(
                    distanceDays <= TaskListManager.UPCOMING_DAYS_DISTANCE
                    && distanceSeconds >= 0
            );
        }

        //To keep this list only for the test
        tlm.removeTaskList(localTaskList);
    }

    @Test
    public void testFutureTasks() {
        //Temporary task list
        TaskList localTaskList = new TaskList("TestFutureTasks");
        tlm.addTaskList(localTaskList);

        LocalDateTime nowDate = LocalDateTime.now();

        //Today tasks
        Task todayTask = new Task();
        todayTask.setEndDate(nowDate);

        //Tomorrow tasks
        Task tomorrowTask = new Task();
        tomorrowTask.setEndDate(nowDate.plusDays(1));

        //Yesterday tasks
        Task yesterdayTask = new Task();
        yesterdayTask.setEndDate(nowDate.minusDays(1));

        //Anytime tasks
        Task anytimeTask = new Task();

        //Upcoming tasks
        Task upcomingTask = new Task();
        upcomingTask.setEndDate(nowDate.plusDays(2));

        //Future tasks
        Task futureTask1 = new Task();
        futureTask1.setEndDate(nowDate.plusDays(7));
        Task futureTask2 = new Task();
        futureTask2.setEndDate(nowDate.plusDays(20));
        Task futureTask3 = new Task();
        futureTask3.setEndDate(nowDate.plusDays(50));

        tlm.addTaskToList(todayTask, localTaskList);
        tlm.addTaskToList(tomorrowTask, localTaskList);
        tlm.addTaskToList(yesterdayTask, localTaskList);
        tlm.addTaskToList(anytimeTask, localTaskList);
        tlm.addTaskToList(upcomingTask, localTaskList);
        tlm.addTaskToList(futureTask1, localTaskList);
        tlm.addTaskToList(futureTask2, localTaskList);
        tlm.addTaskToList(futureTask3, localTaskList);

        ArrayList<Task> outputList = tlm.getViewFutureTasks();

        assertEquals(3, outputList.size());

        long distanceDays;
        for (Task t : outputList) {
            distanceDays = ChronoUnit.DAYS.between(nowDate, t.getEndDate());
            assertTrue(
                    distanceDays > TaskListManager.UPCOMING_DAYS_DISTANCE
            );
        }

        //To keep this list only for the test
        tlm.removeTaskList(localTaskList);
    }

    @Test
    public void testListNameUsed() {
        String nameUsed = "Popular name";
        String nameNotUsed = "A unique name";

        //Temporary task list
        TaskList localTaskList = new TaskList(nameUsed);
        tlm.addTaskList(localTaskList);

        assertTrue(tlm.listNameUsed(nameUsed));
        assertFalse(tlm.listNameUsed(nameNotUsed));

        //To keep this list only for the test
        tlm.removeTaskList(localTaskList);
    }

    @Test
    public void testAddAndRemoveTaskFromTaskList() {
        Task taskToRemove = new Task("Delete me pls");

        //Temporary task list
        TaskList localTaskList = new TaskList("A list with the task to delete");
        tlm.addTaskList(localTaskList);

        tlm.addTaskToList(taskToRemove, localTaskList);
        assertTrue(localTaskList.getTskList().contains(taskToRemove));
        tlm.removeTaskFromList(taskToRemove, localTaskList);
        assertFalse(localTaskList.getTskList().contains(taskToRemove));

        //To keep this list only for the test
        tlm.removeTaskList(localTaskList);
    }
    
    @Test
    public void testModifyTask() {
        String newTitle = "Cooler title";
        Task toModify = new Task("I'll modify this");       
        Task wantedTask = new Task(newTitle);
        
        TaskListManager.modifyTask(toModify, wantedTask);
        
        assertTrue(toModify.getTitle().equals(newTitle));
    }
    
    @Test
    public void testGetCollectViewAndAddTask() {
        Task temporaryTask = new Task("To collect");
        tlm.getCollectTaskList().addTask(temporaryTask);
        assertTrue(tlm.getCollectTaskList().
                getTskList().contains(temporaryTask));
        tlm.getCollectTaskList().getTskList().remove(temporaryTask);
    }
    
    //Normally, there's only 4 basic views.
    @Test
    public void testGetBasicViews() {
        assertEquals(4, tlm.getBasicViews().size());
    }
    
    @Test
    public void testGetMainTaskLists() {
        TaskList taskList1 = new TaskList();
        TaskList taskList2 = new TaskList();
        TaskList taskList3 = new TaskList();
        TaskList taskList4 = new TaskList();
        TaskList taskList5 = new TaskList();
        tlm.addTaskList(taskList1);
        tlm.addTaskList(taskList2);
        tlm.addTaskList(taskList3);
        tlm.addTaskList(taskList4);
        tlm.addTaskList(taskList5);
        
        ArrayList<TaskList> mainLists = tlm.getMainTaskLists();
        assertEquals(5, mainLists.size());
        
        //Should contain the same lists
        assertTrue(mainLists.contains(taskList1));
        assertTrue(mainLists.contains(taskList2));
        assertTrue(mainLists.contains(taskList3));
        assertTrue(mainLists.contains(taskList4));
        assertTrue(mainLists.contains(taskList5));
    }
}
