package com.example.mytravel.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseActivity;

public class SampleActivity extends BaseActivity implements SampleMvpView {

    public SampleMvpPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        presenter = new SamplePresenter(this);
    }
}
