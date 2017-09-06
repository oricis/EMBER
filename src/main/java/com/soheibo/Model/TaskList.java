/*
 * Copyright 2017 .
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

import java.util.ArrayList;

/**
 * Contains tasks specified for the lists.
 * @author Soheib El-Harrache
 */
public class TaskList {
    private int ID;
    private String name;
    private ArrayList<Task> tskList;

    /**
     * Creates a default list of tasks.
     * @param ID
     * @param name 
     */
    public TaskList(int ID, String name) {
        this.ID = ID;
        this.name = name;
        this.tskList = new ArrayList();
    }
    
    /**
     * Adds a task to the taskList.
     * @param t 
     */
    public void addTask(Task t) {
        tskList.add(t);
    }
    
    //----------------GETTERS AND SETTERS--------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Task> getTskList() {
        return tskList;
    }

    public void setTskList(ArrayList<Task> taskList) {
        this.tskList = taskList;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }   
}
