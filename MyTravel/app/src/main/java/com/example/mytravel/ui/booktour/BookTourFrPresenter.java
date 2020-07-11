package com.example.mytravel.ui.booktour;

import com.example.mytravel.base.BasePresenter;

public class BookTourFrPresenter extends BasePresenter implements BookTourFrMvpPresenter {

    private BookTourFrMvpView getMvpView;

    public BookTourFrPresenter(BookTourFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }
}
