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
public class DataModelTest {

    public DataModelTest() {
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

    @Test
    public void testGetTaskListManager() {
        DataModel dataModel = new DataModel();
        TaskListManager newTaskListManager = new TaskListManager();
        dataModel.setTaskListManager(newTaskListManager);
        assertEquals(dataModel.getTaskListManager(), newTaskListManager);
    }

    @Test
    public void testSetTaskListManager() {
        TaskListManager taskListManager = null;
        DataModel dataModel = new DataModel();
        dataModel.setTaskListManager(taskListManager);
        assertEquals(dataModel.getTaskListManager(), taskListManager);
    }

}
