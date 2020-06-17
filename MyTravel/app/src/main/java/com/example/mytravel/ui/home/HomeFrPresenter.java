package com.example.mytravel.ui.home;

import com.example.mytravel.base.BasePresenter;

public class HomeFrPresenter extends BasePresenter implements HomeFrMvpPresenter {

    private HomeFrMvpView getMvpView;

    public HomeFrPresenter(HomeFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
