package com.example.mytravel.ui.main;

import com.example.mytravel.base.BasePresenter;

public class MainPresenter extends BasePresenter implements MainMvpPresenter {

    private MainMvpView getMvpView;


    public MainPresenter(MainMvpView mainMvpView) {
        super(mainMvpView);
        this.getMvpView = mainMvpView;
    }

}
