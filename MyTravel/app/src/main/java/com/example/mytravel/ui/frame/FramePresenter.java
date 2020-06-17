package com.example.mytravel.ui.frame;

import com.example.mytravel.base.BasePresenter;

public class FramePresenter extends BasePresenter implements FrameMvpPresenter {

    private FrameMvpView getMvpView;

    public FramePresenter(FrameMvpView frameMvpView) {
        super(frameMvpView);
        this.getMvpView = frameMvpView;
    }
}
