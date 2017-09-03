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
import java.util.Date;

/**
 * Used to create tasks and modify them.
 * 
 * @author Soheib El-Harrache
 */
public class Task {
    
    private String title;
    private String description;

    private Date creationDate;
    private boolean limited;
    private Date endDate;

    private boolean repetitive;

    private int numberTasks;
    private ArrayList<Task> taskList;
    private ArrayList<Task> requiredTasks;

    /**
     * Create a new task with a title. By default,
     * it`s not repetitive.
     * @param title 
     */
    public Task(String title) {
        this.title = title;
        this.description = "";
        
        this.creationDate = new Date();
        this.repetitive = false;
        
        this.taskList = new ArrayList();
        this.requiredTasks = new ArrayList();
    }

    /**
     * Creates a complete task with all paramaters.
     * @param title
     * @param description
     * @param limited
     * @param endDate
     * @param repetitive
     * @param numberTasks
     * @param taskList
     * @param requiredTasks 
     */
    public Task(String title, String description,
            boolean limited, Date endDate, boolean repetitive, int numberTasks,
            ArrayList<Task> taskList, ArrayList<Task> requiredTasks) {
        this.title = title;
        this.description = description;
        this.creationDate = new Date();
        this.limited = limited;
        this.endDate = endDate;
        this.repetitive = repetitive;
        this.numberTasks = numberTasks;
        this.taskList = taskList;
        this.requiredTasks = requiredTasks;
        
    }
    
    
    

    
}
