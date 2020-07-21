package com.example.mytravel.ui.imagehot;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.imagehot.ImageHot;

import java.util.ArrayList;

public interface ImageHotFrMvpView extends MvpView {
    void successGetListImageHot(ArrayList<ImageHot> listImageHots);
}
