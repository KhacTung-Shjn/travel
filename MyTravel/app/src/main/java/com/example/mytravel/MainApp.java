package com.example.mytravel;

import android.app.Activity;
import android.app.Application;

public class MainApp extends Application {

    private MainApp instance;
    private Activity mCurrentActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public MainApp getInstance() {
        return instance;
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }


}
