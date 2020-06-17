package com.example.mytravel.base;

public interface MvpView {
    void showMessage(String message);

    void showMessage(int message);

    void showLoading();

    void hideLoading();

}
