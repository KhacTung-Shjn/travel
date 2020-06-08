package com.example.mytravel.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseActivity;
import com.example.mytravel.ui.auth.AuthActivity;
import com.example.mytravel.ui.main.MainActivity;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashMvpView {
    public SplashMvpPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setUnbinder(ButterKnife.bind(this));
        presenter = new SplashPresenter(this);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(getBaseContext(), AuthActivity.class));
            finish();
        }, 600);

    }
}
