package com.example.mytravel.ui.fearture;

import com.example.mytravel.base.BasePresenter;

public class MoreFrPresenter extends BasePresenter implements MoreFrMvpPresenter {

    private MoreFrMvpView getMvpView;

    public MoreFrPresenter(MoreFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
