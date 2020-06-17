package com.example.mytravel.ui.auth;

import com.example.mytravel.base.BasePresenter;

public class AuthPresenter extends BasePresenter implements AuthMvpPresenter {

    private AuthMvpView getMvpView;

    public AuthPresenter(AuthMvpView authMvpView) {
        super(authMvpView);
        this.getMvpView = authMvpView;
    }
}
