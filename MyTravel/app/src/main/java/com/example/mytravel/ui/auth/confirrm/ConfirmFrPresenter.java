package com.example.mytravel.ui.auth.confirrm;

import com.example.mytravel.base.BasePresenter;

public class ConfirmFrPresenter extends BasePresenter implements ConfirmFrMvpPresenter {

    private ConfirmFrMvpView getMvpView;

    public ConfirmFrPresenter(ConfirmFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
