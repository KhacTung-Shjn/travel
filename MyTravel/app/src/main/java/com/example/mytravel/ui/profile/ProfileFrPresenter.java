package com.example.mytravel.ui.profile;

public class ProfileFrPresenter implements ProfileFrMvpPresenter {

    private ProfileFrMvpView getMvpView;

    public ProfileFrPresenter(ProfileFrMvpView getMvpView) {
        this.getMvpView = getMvpView;
    }
}
