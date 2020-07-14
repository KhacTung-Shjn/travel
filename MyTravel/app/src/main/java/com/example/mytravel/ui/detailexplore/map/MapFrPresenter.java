package com.example.mytravel.ui.detailexplore.map;

import com.example.mytravel.base.BasePresenter;

public class MapFrPresenter extends BasePresenter implements MapFrMvpPresenter {

    private SampleFrMvpView getMvpView;

    public MapFrPresenter(SampleFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
