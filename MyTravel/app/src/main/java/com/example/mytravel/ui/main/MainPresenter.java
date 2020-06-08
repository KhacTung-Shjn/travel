package com.example.mytravel.ui.main;

public class MainPresenter implements MainMvpPresenter {

    private MainMvpView getMvpView;


    public MainPresenter(MainMvpView mainMvpView) {
        this.getMvpView = mainMvpView;
    }
}
