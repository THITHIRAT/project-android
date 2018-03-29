package com.example.thithirat.test;

/**
 * Created by Thithirat on 27/3/2561.
 */

public class LocationReminderComplete {
    private int id;
    private int reminder_id;
    private String name;
    private String noti;
    private String task;

    public LocationReminderComplete(int id, int reminder_id, String name, String noti, String task) {
        this.id = id;
        this.reminder_id = reminder_id;
        this.name = name;
        this.noti = noti;
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(int reminder_id) {
        this.reminder_id = reminder_id;
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
}
