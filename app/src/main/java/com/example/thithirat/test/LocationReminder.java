package com.example.thithirat.test;

import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by Thithirat on 6/3/2561.
 */

public class LocationReminder {
    private int id;
    private int reminder_id;
    private String name;
    private String noti;
    private String task;
    private String type;


    public LocationReminder(int id, int reminder_id, String name, String noti, String task, String type) {
        this.id = id;
        this.reminder_id = reminder_id;
        this.name = name;
        this.noti = noti;
        this.task = task;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoti() {
        return noti;
    }

    public void setNoti(String noti) {
        this.noti = noti;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(int reminder_id) {
        this.reminder_id = reminder_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
