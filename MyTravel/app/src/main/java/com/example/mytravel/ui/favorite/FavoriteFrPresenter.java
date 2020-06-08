package com.example.mytravel.ui.favorite;

public class FavoriteFrPresenter implements FavoriteFrMvpPresenter {

    private FavoriteFrMvpView getMvpView;

    public FavoriteFrPresenter(FavoriteFrMvpView getMvpView) {
        this.getMvpView = getMvpView;
    }
}
