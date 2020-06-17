package com.example.mytravel.ui.splash;

import com.example.mytravel.base.BasePresenter;

public class SplashPresenter extends BasePresenter implements SplashMvpPresenter {

    private SplashMvpView getMvpView;


    public SplashPresenter(SplashMvpView splashMvpView) {
        super(splashMvpView);
        getMvpView = splashMvpView;
    }
}
