package com.example.mytravel.ui.sample;

public class SamplePresenter implements SampleMvpPresenter {

    private SampleMvpView getMvpView;

    public SamplePresenter(SampleMvpView sampleMvpView) {
        this.getMvpView = sampleMvpView;
    }
}
