package com.example.mytravel.ui.home;

public class HomeFrPresenter implements HomeFrMvpPresenter {

    private HomeFrMvpView getMvpView;

    public HomeFrPresenter(HomeFrMvpView getMvpView) {
        this.getMvpView = getMvpView;
    }
}
