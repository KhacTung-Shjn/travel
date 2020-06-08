package com.example.mytravel.splash;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytravel.R;

public class SplashActivity extends AppCompatActivity implements SplashMvpView {
    public SplashMvpPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        presenter = new SplashPresenter(this);
    }
}
