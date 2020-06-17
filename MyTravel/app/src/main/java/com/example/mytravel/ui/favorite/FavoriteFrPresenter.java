package com.example.mytravel.ui.favorite;

import com.example.mytravel.base.BasePresenter;

public class FavoriteFrPresenter extends BasePresenter implements FavoriteFrMvpPresenter {

    private FavoriteFrMvpView getMvpView;

    public FavoriteFrPresenter(FavoriteFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
