package com.example.thithirat.test;

public class Notification {
    private int id;
    private String before_after;
    private String number;
    private String type;

    public Notification(int id, String before_after, String number, String type) {
        this.id = id;
        this.before_after = before_after;
        this.number = number;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBefore_after() {
        return before_after;
    }

    public void setBefore_after(String before_after) {
        this.before_after = before_after;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
