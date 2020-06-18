package com.example.mytravel.ui.favorite.tour;

import com.example.mytravel.base.BasePresenter;

public class TourFrPresenter extends BasePresenter implements TourFrMvpPresenter {

    private TourFrMvpView getMvpView;

    public TourFrPresenter(TourFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
