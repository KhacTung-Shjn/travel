package com.example.mytravel.ui.privacy;

import com.example.mytravel.base.BasePresenter;

public class PrivacyFrPresenter extends BasePresenter implements PrivacyFrMvpPresenter {

    private PrivacyFrMvpView getMvpView;

    public PrivacyFrPresenter(PrivacyFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
