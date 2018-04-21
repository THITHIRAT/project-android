package com.example.thithirat.test;

import android.app.Application;

public class ConnectAPI extends Application {
//    private static String url = "http://161.246.6.1:8088/";
    private static String url = "http://161.246.5.195:3000/";

    public static String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
