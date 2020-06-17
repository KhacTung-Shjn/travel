package com.example.mytravel.base;

import com.example.mytravel.MainApp;
import com.example.mytravel.data.AppDataManager;

public class BasePresenter implements MvpPresenter {

    private MvpView mvpView;

    private AppDataManager appDataManager;

    public BasePresenter(MvpView mvpView) {
        this.mvpView = mvpView;
        appDataManager = MainApp.getInstance().getAppDataManager();
    }


    @Override
    public void onDetach() {
        this.mvpView = null;
    }

    public AppDataManager getDataManager() {
        return appDataManager;
    }
}
