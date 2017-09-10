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
package com.soheibo.Main;

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
public class EmberTest {

    public EmberTest() {
    }

    //Needed to test JavaFX
    //Found this here: https://stackoverflow.com/questions/11385604/
    //how-do-you-unit-test-a-javafx-controller-with-junit
    @BeforeClass
    public static void setUpClass() throws InterruptedException {
        // Initialise Java FX

        System.out.printf("About to launch FX App\n");
        Thread t = new Thread("JavaFX Init Thread") {
            @Override
            public void run() {
                Application.launch(Ember.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
        System.out.printf("FX App thread started\n");
        Thread.sleep(1000);
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

    //No exceptions should be thrown
    @Test
    public void testNormalStart() throws Exception {
        Platform.runLater(() -> {
            Ember ember = new Ember();
            Stage stage = new Stage();
            try {
                ember.start(stage);
            } catch (Exception ex) {
                fail("An exception is thrown.");
                Logger.getLogger(
                        EmberTest.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        });

    }

}
