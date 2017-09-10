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

import com.soheibo.Main.Ember;
import com.soheibo.Model.DataModel;
import javafx.application.Application;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Soheib El-Harrache
 */
public class MainControllerTest {

    public MainControllerTest() {
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

    //Model cannot be null
    @Test(expected = NullPointerException.class)
    public void testEmptyDataModelInitModel() {
        DataModel dataModel = null;
        MainController mainController = new MainController();
        mainController.initModel(dataModel);
    }

    //Shouldn't launch any exceptions
//    @Test
//    public void testInitModel() {
//        DataModel dataModel = new DataModel();
//        MainController mainController = new MainController();
//        mainController.initModel(dataModel);
//    }
//
//    //Cannot initiate a model twice
//    @Test (expected = IllegalStateException.class)
//    public void testInitModelTwice() {
//        DataModel dataModel = new DataModel();
//        DataModel dataModel2 = new DataModel();
//        MainController mainController = new MainController();
//        
//        mainController.initModel(dataModel);
//        mainController.initModel(dataModel2);
//    }

}
