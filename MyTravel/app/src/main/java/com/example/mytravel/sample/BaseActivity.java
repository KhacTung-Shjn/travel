package com.example.mytravel.sample;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytravel.R;

public class BaseActivity extends AppCompatActivity implements BaseMvpView {
    public BaseMvpPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        presenter = new BasePresenter(this);
    }
}
