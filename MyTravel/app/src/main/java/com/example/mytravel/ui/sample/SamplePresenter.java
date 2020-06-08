package com.example.mytravel.sample;

public class SamplePresenter implements SampleMvpPresenter {

    private SampleMvpView getMvpView;


    public SamplePresenter(SampleMvpView sampleMvpView) {
        this.getMvpView = sampleMvpView;
    }
}
