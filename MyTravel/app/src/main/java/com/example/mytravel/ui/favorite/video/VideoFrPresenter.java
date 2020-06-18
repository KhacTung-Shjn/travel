package com.example.mytravel.ui.favorite.video;

import com.example.mytravel.base.BasePresenter;

public class VideoFrPresenter extends BasePresenter implements VideoFrMvpPresenter {

    private VideoFrMvpView getMvpView;

    public VideoFrPresenter(VideoFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
