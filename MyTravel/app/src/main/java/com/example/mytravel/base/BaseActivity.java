package com.example.mytravel.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytravel.MainApp;

import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    @Override
    protected void onDestroy() {

        if (unbinder != null) {
            unbinder.unbind();
        }

        clearCurrentActivities();
        super.onDestroy();
    }

    private void clearCurrentActivities() {
        Activity currActivity = ((MainApp) getApplication()).getCurrentActivity();
        if (this.equals(currActivity)) {
            ((MainApp) getApplication()).setCurrentActivity(null);
        }
    }

    @Override
    protected void onResume() {
        ((MainApp) getApplication()).setCurrentActivity(this);
        super.onResume();
    }


}
