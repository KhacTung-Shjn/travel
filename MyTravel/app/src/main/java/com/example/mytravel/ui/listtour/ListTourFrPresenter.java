package com.example.mytravel.ui.listtour;

import com.example.mytravel.base.BasePresenter;

public class ListTourFrPresenter extends BasePresenter implements ListTourFrMvpPresenter {

    private ListTourFrMvpView getMvpView;

    public ListTourFrPresenter(ListTourFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
