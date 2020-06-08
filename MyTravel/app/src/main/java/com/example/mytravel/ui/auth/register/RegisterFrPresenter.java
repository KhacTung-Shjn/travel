package com.example.mytravel.ui.auth.register;

public class RegisterFrPresenter implements RegisterFrMvpPresenter {

    private RegisterFrMvpView getMvpView;

    public RegisterFrPresenter(RegisterFrMvpView getMvpView) {
        this.getMvpView = getMvpView;
    }
}
