package com.example.mytravel.ui.detailexplore.placelist;

import com.example.mytravel.base.BasePresenter;

public class PlaceListBSFrPresenter extends BasePresenter implements PlaceListBSFrMvpPresenter {

    private PlaceListBSFrMvpView getMvpView;

    public PlaceListBSFrPresenter(PlaceListBSFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
