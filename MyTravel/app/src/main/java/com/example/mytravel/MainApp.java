package com.example.mytravel;

import android.app.Application;

public class MainApp extends Application {

    private MainApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public MainApp getInstance() {
        return instance;
    }
}
