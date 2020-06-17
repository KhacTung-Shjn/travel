package com.example.mytravel.ui.sample;

import com.example.mytravel.base.BasePresenter;

public class SampleFrPresenter extends BasePresenter implements SampleFrMvpPresenter {

    private SampleFrMvpView getMvpView;

    public SampleFrPresenter(SampleFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
