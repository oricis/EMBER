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
    
    ArrayList<TaskList> mainTaskList;

    /**
     * Default TaskListManager has a Collect, a Today,
     * an Upcoming, a Anytime and a Future list.
     */
    public TaskListManager() {
        this.mainTaskList = new ArrayList();
    }
    
    /**
     * Returns true if the name is already
     * used by a taskList.
     * 
     * @param name The name to verify.
     * @return True if the name is used.
     */
    public boolean listNameUsed (String name) {
        for (TaskList tskList : mainTaskList) {
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
        mainTaskList.add(tskList);
    }
    
    /**
     * Removes a taskList using it's name.
     * @param name The name of the taskList to remove.
     */
    public void removeTaskList(String name) {
        for (TaskList tskList : mainTaskList) {
            if (tskList.getName().equals(name)) {
                mainTaskList.remove(tskList);
            }
        }       
    }
    
    
}
