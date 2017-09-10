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

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Contains tasks specified for the lists.
 * @author Soheib El-Harrache
 */
public class TaskList {
    static AtomicInteger nextId = new AtomicInteger();
    private int ID;
    private String name;
    private ArrayList<Task> tskList;
    private boolean necessary;

    /**
     * Creates a default list of tasks.
     * @param name Name of the list
     */
    public TaskList(String name) {
        this.ID = nextId.incrementAndGet();
        this.name = name;
        this.tskList = new ArrayList();
        this.necessary = false;
    }
    
    /**
     * Creates a list of tasks. The list
     * can be necessary (cannot be removed).
     * @param name Name of the list
     * @param necessary True means necessary
     */
    public TaskList(String name, boolean necessary) {
        this.ID = nextId.incrementAndGet();
        this.name = name;
        this.tskList = new ArrayList();
        this.necessary = necessary;
    }
    
    /**
     * Adds a task to the taskList.
     * @param t 
     */
    public void addTask(Task t) {
        tskList.add(t);
    }
    
    /**
     * Removes a task from the taskList.
     * @param t 
     */
    public void removeTask(Task t) {
        tskList.remove(t);
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

    //TODO: Might remove it
//    public void setID(int ID) {
//        this.ID = ID;
//    }
    
    public boolean isNecessary() {
        return this.necessary;
    }
}
