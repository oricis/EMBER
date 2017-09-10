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

import com.soheibo.Main.Ember;
import com.soheibo.Model.TaskList;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
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
public class NewTaskListWindowTest {

    public NewTaskListWindowTest() {
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

    /**
     * Test of getTaskList method, of class NewTaskListWindow.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testGetTaskList() throws IOException {
        Platform.runLater(() -> {
            Ember ember = new Ember();
            NewTaskListWindow stage;
            try {
                stage = new NewTaskListWindow();
                //TODO: Need to change this call
                ember.start(stage);
                assertEquals(stage.getTaskList(), null);
            } catch (Exception ex) {
                fail("An exception is thrown.");
                Logger.getLogger(NewTaskListWindowTest.
                        class.getName()).log(Level.
                                SEVERE, null, ex);
            }
        });
    }

}
