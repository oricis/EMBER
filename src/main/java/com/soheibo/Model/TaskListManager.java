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
 *
 * @author Soheib El-Harrache
 */
public class TaskListManager {
    
    //Used to give unique IDs
    private int lastID;
    private ArrayList<TaskList> mainTaskLists;
    private TaskList collectTaskList;

    /**
     * Default TaskListManager has normally a Collect, a Today,
     * an Upcoming, a Anytime and a Future list.
     */
    public TaskListManager() {
        this.lastID = 0;
        this.mainTaskLists = new ArrayList();
        this.collectTaskList = new TaskList(this.getNewID(), "Collect");
        this.mainTaskLists.add(collectTaskList);
    }
    
    /**
     * Returns true if the name is already
     * used by a taskList.
     * 
     * @param name The name to verify.
     * @return True if the name is used.
     */
    public boolean listNameUsed (String name) {
        for (TaskList tskList : mainTaskLists) {
            if (tskList.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Adds a taskList to the taskListManager.
     * @param tskList 
     */
    public void addTaskList(TaskList tskList) {
        mainTaskLists.add(tskList);
    }
    
    /**
     * Removes a taskList using it's name.
     * @param name The name of the taskList to remove.
     */
    public void removeTaskList(String name) {
        for (TaskList tskList : mainTaskLists) {
            if (tskList.getName().equals(name)) {
                mainTaskLists.remove(tskList);
            }
        }       
    }
    
    /**
     * Returns a new ID, being incrementally given.
     * @return 
     */
    public int getNewID() {
        lastID++;
        return lastID;
    }
    
    /**
     * Returns a taskList with a certain name. Returns null
     * if not found.
     * @param name
     * @return 
     */
    public TaskList getTaskListFromName(String name) {
        for (TaskList tskList : mainTaskLists) {
            if (tskList.getName().equals(name)) {
                return tskList;
            }
        }
        return null;
    }
    
}
