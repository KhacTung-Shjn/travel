package com.example.mytravel.base;

import com.example.mytravel.MainApp;
import com.example.mytravel.data.AppDataManager;
import com.google.gson.Gson;

public class BasePresenter implements MvpPresenter {

    private MvpView mvpView;
    private AppDataManager appDataManager;
    private Gson gson;

    public BasePresenter(MvpView mvpView) {
        this.mvpView = mvpView;
        appDataManager = MainApp.getInstance().getAppDataManager();
        gson = new Gson();
    }


    @Override
    public void onDetach() {
        this.mvpView = null;
    }

    public AppDataManager getDataManager() {
        return appDataManager;
    }

    public Gson getGSon() {
        return gson;
    }
}
