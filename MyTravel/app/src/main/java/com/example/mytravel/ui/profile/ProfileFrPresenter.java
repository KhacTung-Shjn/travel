package com.example.mytravel.ui.profile;

import com.example.mytravel.base.BasePresenter;

public class ProfileFrPresenter extends BasePresenter implements ProfileFrMvpPresenter {

    private ProfileFrMvpView getMvpView;

    public ProfileFrPresenter(ProfileFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
