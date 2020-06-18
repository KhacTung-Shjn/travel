package com.example.mytravel.ui.favorite.place;

import com.example.mytravel.base.BasePresenter;

public class PlaceFrPresenter extends BasePresenter implements PlaceFrMvpPresenter {

    private PlaceFrMvpView getMvpView;

    public PlaceFrPresenter(PlaceFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
