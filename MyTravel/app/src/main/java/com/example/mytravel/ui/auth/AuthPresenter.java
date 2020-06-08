package com.example.mytravel.ui.auth;

public class AuthPresenter implements AuthMvpPresenter {

    private AuthMvpView getMvpView;

    public AuthPresenter(AuthMvpView authMvpView) {
        this.getMvpView = authMvpView;
    }
}
