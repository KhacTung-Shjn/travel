package com.example.mytravel.ui.splash;

public class SplashPresenter implements SplashMvpPresenter {

    private SplashMvpView getMvpView;


    public SplashPresenter(SplashMvpView splashMvpView) {
        getMvpView = splashMvpView;
    }
}
