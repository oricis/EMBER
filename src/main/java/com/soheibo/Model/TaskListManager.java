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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Used to manage task lists and tasks. Has default task lists.
 *
 * @author Soheib El-Harrache
 */
public class TaskListManager {

    private final ArrayList<TaskList> mainTaskLists;
    //Saves performance in the long run
    private final TaskList collectTaskList;
    private final ArrayList<TaskList> basicViews;

    public static final int UPCOMING_DAYS_DISTANCE = 5;

    /**
     * Basic constructor. Default TaskListManager has normally a "Collect", a
     * "Today", an "Upcoming", a "Anytime" and a "Future" list.
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
     * Adds a taskList to the TaskListManager.
     *
     * @param tskList Task list to add.
     */
    public void addTaskList(TaskList tskList) {
        mainTaskLists.add(tskList);
    }

    /**
     * Adds a task to a list in the TaskListManager.
     *
     * @param task Task to add.
     * @param taskList Task list used to add a task.
     */
    public void addTaskToList(Task task, TaskList taskList) {
        //TODO: add triggers or mechanism to future plannings
        if (task != null && taskList != null) {
            taskList.addTask(task);
        }
    }

    /**
     * Removes a task list.
     *
     * @param taskList Task list to remove.
     */
    public void removeTaskList(TaskList taskList) {
        mainTaskLists.remove(taskList);
    }

    /**
     * Removes a task from a task list.
     *
     * @param task Task to remove.
     * @param taskList Task list from which the task is removed.
     */
    public void removeTaskFromList(Task task, TaskList taskList) {
        //TODO: add triggers or mechanism to future plannings
        taskList.removeTask(task);
    }

    /**
     * Returns a list of tasks collected from custom task lists that are meant
     * for today.
     *
     * @return List of tasks that are meant for today.
     */
    public ArrayList<Task> getViewTodayTasks() {
        LocalDateTime nowDate = LocalDateTime.now();
        ArrayList<Task> todayTasks = new ArrayList<>();
        long distanceDays, distanceSeconds;

        for (TaskList tskList : mainTaskLists) {
            ArrayList<Task> tasks = tskList.getTskList();
            for (Task t : tasks) {
                if (t.getEndDate() != null) {

                    distanceDays = ChronoUnit.DAYS.between(
                            nowDate.toLocalDate().atStartOfDay(),
                            t.getEndDate());
                    distanceSeconds = ChronoUnit.SECONDS.between(
                            nowDate.toLocalDate().atStartOfDay(),
                            t.getEndDate());
                    if (distanceDays == 0 && distanceSeconds >= 0) {
                        todayTasks.add(t);
                    }
                }
            }
        }
        return todayTasks;
    }

    /**
     * Returns a list of tasks collected from custom task lists that are meant
     * for tomorrow.
     *
     * @return List of tasks that are meant for tomorrow.
     */
    public ArrayList<Task> getViewTomorrowTasks() {
        LocalDateTime nowDate = LocalDateTime.now();
        ArrayList<Task> tomorrowTasks = new ArrayList<>();
        long distanceDays;

        for (TaskList tskList : mainTaskLists) {
            ArrayList<Task> tasks = tskList.getTskList();
            for (Task t : tasks) {
                if (t.getEndDate() != null) {
                    distanceDays = ChronoUnit.DAYS.between(
                            nowDate.toLocalDate().atStartOfDay(),
                            t.getEndDate());
                    if (distanceDays == 1) {
                        tomorrowTasks.add(t);
                    }
                }
            }
        }
        return tomorrowTasks;
    }

    /**
     * Returns a list of tasks collected from custom task lists that are meant
     * for the upcoming days.
     *
     * @return List of tasks that are meant for the upcoming days.
     */
    public ArrayList<Task> getViewUpcomingTasks() {
        return getTasksFromTodayToCertainDay(UPCOMING_DAYS_DISTANCE);
    }

    /**
     * Returns a list of tasks collected from custom task lists that are meant
     * for the period between today plus the distance passed by parameter.
     *
     * @param distanceInDays Length of the period.
     * @return List of tasks that are meant for the specified period.
     */
    private ArrayList<Task> getTasksFromTodayToCertainDay(int distanceInDays) {
        LocalDateTime nowDate = LocalDateTime.now();
        ArrayList<Task> upcomingTasks = new ArrayList<>();
        long distanceDays, distanceSeconds;

        for (TaskList tskList : mainTaskLists) {
            ArrayList<Task> tasks = tskList.getTskList();
            for (Task t : tasks) {
                if (t.getEndDate() != null) {
                    distanceDays
                            = ChronoUnit.DAYS.between(
                                    nowDate.toLocalDate().atStartOfDay(),
                                    t.getEndDate());
                    distanceSeconds = ChronoUnit.SECONDS.between(
                                    nowDate.toLocalDate().atStartOfDay(),
                                    t.getEndDate());
                    if (distanceDays <= distanceInDays
                            && distanceSeconds >= 0) {
                        upcomingTasks.add(t);
                    }
                }
            }
        }
        return upcomingTasks;
    }

    /**
     * Returns a list of tasks collected from custom task lists that are meant
     * for the future.
     *
     * @return List of tasks that are meant for the future.
     */
    public ArrayList<Task> getViewFutureTasks() {
        LocalDateTime nowDate = LocalDateTime.now();
        ArrayList<Task> futureTasks = new ArrayList<>();
        long distanceDays;

        for (TaskList tskList : mainTaskLists) {
            ArrayList<Task> tasks = tskList.getTskList();
            for (Task t : tasks) {
                if (t.getEndDate() != null) {
                    distanceDays
                            = ChronoUnit.DAYS.between(
                                    nowDate.toLocalDate().atStartOfDay(),
                                    t.getEndDate());
                    if (distanceDays > UPCOMING_DAYS_DISTANCE) {
                        futureTasks.add(t);
                    }
                }
            }
        }
        return futureTasks;
    }

    /**
     * Returns a list of tasks collected from custom task lists that are meant
     * for any time.
     *
     * @return List of tasks that are meant for any time.
     */
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

    /**
     * Clones the values of a modified task and replace the old task values with
     * it.
     *
     * @param oldTask Old task to replace with new values.
     * @param modifiedTask Modified task from which the values are cloned.
     */
    public static void modifyTask(Task oldTask, Task modifiedTask) {
        oldTask.clone(modifiedTask);
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
