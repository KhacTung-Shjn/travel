package com.example.mytravel.ui.sample;

import com.example.mytravel.base.BasePresenter;

public class SamplePresenter extends BasePresenter implements SampleMvpPresenter {

    private SampleMvpView getMvpView;

    public SamplePresenter(SampleMvpView sampleMvpView) {
        super(sampleMvpView);
        this.getMvpView = sampleMvpView;
    }
}
