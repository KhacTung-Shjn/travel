package com.example.mytravel.sample;

public class SampleFrPresenter implements SampleFrMvpPresenter {

    private SampleFrMvpView getMvpView;

    public SampleFrPresenter(SampleFrMvpView getMvpView) {
        this.getMvpView = getMvpView;
    }
}
