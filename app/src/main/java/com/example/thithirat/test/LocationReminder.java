package com.example.thithirat.test;

/**
 * Created by Thithirat on 6/3/2561.
 */

public class LocationReminder {
    private int id;
    private String name;

    public LocationReminder(int id, String name) {
        this.id = id;
        this.name = name;
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
}
