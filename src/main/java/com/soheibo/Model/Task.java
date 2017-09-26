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
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Used to create tasks and modify them.
 * 
 * @author Soheib El-Harrache
 */
public class Task {
    private static AtomicInteger currentIDcount = new AtomicInteger();
    
    private int ID;
    private String title;
    private String description;

    private Date creationDate;
    private boolean limited;
    private Date endDate;

    private boolean repetitive;

    private int numberTasks;
    private ArrayList<Task> taskList;
    private ArrayList<Task> requiredTasks;

    
    public Task() {
        this.ID = currentIDcount.incrementAndGet();
        this.title = "";
        this.description = "";
        this.limited = false;
        
        this.creationDate = new Date();
        this.repetitive = false;
        
        this.taskList = new ArrayList();
        this.requiredTasks = new ArrayList();
    }
    
    /**
     * Create a new task with a title. By default,
     * it's not repetitive.

     * @param title 
     */
    public Task(String title) {
        this.ID = currentIDcount.incrementAndGet();
        this.title = title;
        this.description = "";
        this.limited = false;
        
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
        this.ID = currentIDcount.incrementAndGet();
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
    
    /**
     * Clones the value of a task given as a parameter.
     * @param task Task used to get values for the copy.
     */
    public void clone(Task task) {
        this.ID = task.getID();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.creationDate = (Date) task.getCreationDate().clone();
        this.limited = task.isLimited();
        if (task.getEndDate() != null) {
            this.endDate = (Date) task.getEndDate().clone();
        } else {
            this.endDate = null;
        }
        this.repetitive = task.isRepetitive();
        this.numberTasks = task.getNumberTasks();
        this.taskList = new ArrayList<>(task.getTaskList());
        this.requiredTasks = new ArrayList<>(task.getRequiredTasks());
    }

    //----------------GETTERS AND SETTERS--------------------------------------
    public int getID() {
        return ID;
    }
    
    //Should not set IDs
//    public void setID(int ID) {
//        this.ID = ID;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }
    
    //No usage found yet
//    public void setCreationDate(Date creationDate) {
//        this.creationDate = creationDate;
//    }

    public boolean isLimited() {
        return limited;
    }

    public void setLimited(boolean limited) {
        this.limited = limited;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isRepetitive() {
        return repetitive;
    }

    public void setRepetitive(boolean repetitive) {
        this.repetitive = repetitive;
    }

    public int getNumberTasks() {
        return numberTasks;
    }

    //IMPORTANT: Keeping it to boost performance
    //when adding another list to this task
    public void setNumberTasks(int numberTasks) {
        this.numberTasks = numberTasks;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public ArrayList<Task> getRequiredTasks() {
        return requiredTasks;
    }

    public void setRequiredTasks(ArrayList<Task> requiredTasks) {
        this.requiredTasks = requiredTasks;
    }

    // For now, it'll be used to return the title.
    @Override
    public String toString() {
        return title;
    }
    
}
