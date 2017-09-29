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

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Soheib El-Harrache
 */
public class TaskListManager {

    private final ArrayList<TaskList> mainTaskLists;
    //Saves performance in the long run
    private final TaskList collectTaskList;
    private final ArrayList<TaskList> basicViews;

    private final int UPCOMING_DAYS_DISTANCE = 5;

    /**
     * Default TaskListManager has normally a "Collect", a "Today", an
     * "Upcoming", a "Anytime" and a "Future" list.
     */
    public TaskListManager() {
        this.mainTaskLists = new ArrayList();
        this.collectTaskList = new TaskList("Collect", true);
        this.basicViews = new ArrayList();
        this.basicViews.add(new TaskList("Today", true));
        this.basicViews.add(new TaskList("Upcoming", true));
        this.basicViews.add(new TaskList("Anytime", true));
        this.basicViews.add(new TaskList("Future", true));
    }

    /**
     * Returns true if the name is already used by a taskList.
     *
     * @param name The name to verify.
     * @return True if the name is used.
     */
    public boolean listNameUsed(String name) {
        for (TaskList tskList : mainTaskLists) {
            if (tskList.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a taskList to the taskListManager.
     *
     * @param tskList
     */
    public void addTaskList(TaskList tskList) {
        mainTaskLists.add(tskList);
    }

    public void addTaskToList(Task task, TaskList taskList) {
        //TODO: add triggers or mechanism to future plannings
        taskList.addTask(task);
    }

    public void removeTaskList(TaskList taskList) {
        mainTaskLists.remove(taskList);
    }

    public void removeTaskFromList(Task task, TaskList taskList) {
        //TODO: add triggers or mechanism to future plannings
        taskList.removeTask(task);
    }

    public ArrayList<Task> getViewTodayTasks() {
        LocalDateTime nowDate = LocalDateTime.now();
        int todayDate = nowDate.getDayOfYear();
        ArrayList<Task> todayTasks = new ArrayList<>();

        for (TaskList tskList : mainTaskLists) {
            ArrayList<Task> tasks = tskList.getTskList();
            for (Task t : tasks) {
                if (t.getEndDate() != null) {
                    if (t.getEndDate().getDayOfYear() == todayDate) {
                        todayTasks.add(t);
                    }
                }
            }
        }
        return todayTasks;
    }

    public ArrayList<Task> getViewTomorrowTasks() {
        LocalDateTime nowDate = LocalDateTime.now();
        int tomorrowDate = nowDate.getDayOfYear() + 1;
        ArrayList<Task> tomorrowTasks = new ArrayList<>();

        for (TaskList tskList : mainTaskLists) {
            ArrayList<Task> tasks = tskList.getTskList();
            for (Task t : tasks) {
                if (t.getEndDate() != null) {
                    if (t.getEndDate().getDayOfYear() == tomorrowDate) {
                        tomorrowTasks.add(t);
                    }
                }
            }
        }
        return tomorrowTasks;
    }

    public ArrayList<Task> getViewUpcomingTasks() {
        LocalDateTime nowDate = LocalDateTime.now();
        int maxUpcomingDate = nowDate.getDayOfYear() + UPCOMING_DAYS_DISTANCE;
        ArrayList<Task> upcomingTasks = new ArrayList<>();

        for (TaskList tskList : mainTaskLists) {
            ArrayList<Task> tasks = tskList.getTskList();
            for (Task t : tasks) {
                if (t.getEndDate() != null) {
                    if (t.getEndDate().getDayOfYear() <= maxUpcomingDate) {
                        upcomingTasks.add(t);
                    }
                }
            }
        }
        return upcomingTasks;
    }

    public ArrayList<Task> getViewFutureTasks() {
        LocalDateTime nowDate = LocalDateTime.now();
        int minFutureDate = nowDate.getDayOfYear() + UPCOMING_DAYS_DISTANCE;
        ArrayList<Task> futureTasks = new ArrayList<>();

        for (TaskList tskList : mainTaskLists) {
            ArrayList<Task> tasks = tskList.getTskList();
            for (Task t : tasks) {
                if (t.getEndDate() != null) {
                    if (t.getEndDate().getDayOfYear() > minFutureDate) {
                        futureTasks.add(t);
                    }
                }
            }
        }
        return futureTasks;
    }

    public ArrayList<Task> getViewAnytimeTasks() {
        ArrayList<Task> anytimeTasks = new ArrayList<>();

        for (TaskList tskList : mainTaskLists) {
            ArrayList<Task> tasks = tskList.getTskList();
            for (Task t : tasks) {
                if (t.getEndDate() == null) {
                    anytimeTasks.add(t);
                }
            }
        }
        return anytimeTasks;
    }

    //----------------GETTERS AND SETTERS--------------------------------------
    public TaskList getCollectTaskList() {
        return collectTaskList;
    }

    public ArrayList<TaskList> getBasicViews() {
        return basicViews;
    }

    public ArrayList<TaskList> getMainTaskLists() {
        return mainTaskLists;
    }
}
