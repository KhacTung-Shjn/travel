package com.example.mytravel.ui.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseActivity;

import butterknife.ButterKnife;

public class SampleActivity extends BaseActivity implements SampleMvpView {
    public static final String TAG = SampleActivity.class.getSimpleName();

    public SampleMvpPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setUnbinder(ButterKnife.bind(this));
        presenter = new SamplePresenter(this);
    }


}
