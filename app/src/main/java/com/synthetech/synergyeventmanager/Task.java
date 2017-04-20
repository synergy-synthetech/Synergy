package com.synthetech.synergyeventmanager;

import java.util.Date;

/**
 * Created by Aseem on 19-04-2017.
 */

public class Task {
    String task;
    String key;
    long time;

    public Task() {
    }

    public Task(String task, String key) {
        this.task = task;
        this.key = key;
        this.time = new Date().getTime();
    }

    public String getTask() {
        return task;
    }

    public long getTime() {
        return time;
    }
}
