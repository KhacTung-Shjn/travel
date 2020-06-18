package com.example.mytravel.ui.favorite.photo;

import com.example.mytravel.base.BasePresenter;

public class PhotoFrPresenter extends BasePresenter implements PhotoFrMvpPresenter {

    private PhotoFrMvpView getMvpView;

    public PhotoFrPresenter(PhotoFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
