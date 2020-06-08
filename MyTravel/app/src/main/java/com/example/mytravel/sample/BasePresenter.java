package com.example.mytravel.sample;

public class BasePresenter implements BaseMvpPresenter {

    private BaseMvpView getMvpView;


    public BasePresenter(BaseMvpView baseMvpView) {
        getMvpView = baseMvpView;
    }
}
