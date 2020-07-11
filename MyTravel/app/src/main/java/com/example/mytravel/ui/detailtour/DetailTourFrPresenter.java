package com.example.mytravel.ui.detailtour;

import com.example.mytravel.base.BasePresenter;

public class DetailTourFrPresenter extends BasePresenter implements DetailTourFrMvpPresenter {

    private DetailTourFrMvpView getMvpView;

    public DetailTourFrPresenter(DetailTourFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
