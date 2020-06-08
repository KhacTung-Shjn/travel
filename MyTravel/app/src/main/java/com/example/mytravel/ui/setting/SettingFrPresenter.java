package com.example.mytravel.ui.setting;

public class SettingFrPresenter implements SettingFrMvpPresenter {

    private SettingFrMvpView getMvpView;

    public SettingFrPresenter(SettingFrMvpView getMvpView) {
        this.getMvpView = getMvpView;
    }
}
