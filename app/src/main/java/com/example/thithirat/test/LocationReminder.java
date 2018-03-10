package com.example.thithirat.test;

/**
 * Created by Thithirat on 6/3/2561.
 */

public class LocationReminder {
    private int id;
    private String name;
    private String noti;
    private String task;

    public LocationReminder(int id, String name, String noti, String task) {
        this.id = id;
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
