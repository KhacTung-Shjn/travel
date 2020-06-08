package com.example.mytravel.ui.auth.login;

public class LoginFrPresenter implements LoginFrMvpPresenter {

    private LoginFrMvpView getMvpView;

    public LoginFrPresenter(LoginFrMvpView getMvpView) {
        this.getMvpView = getMvpView;
    }
}
