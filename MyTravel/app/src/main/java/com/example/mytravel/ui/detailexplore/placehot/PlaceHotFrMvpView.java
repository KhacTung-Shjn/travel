package com.example.mytravel.ui.detailexplore.placehot;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.city.PlaceHot;

import java.util.ArrayList;

public interface PlaceHotFrMvpView extends MvpView {
    void successGetListPlaceHot(ArrayList<PlaceHot> listPlaceHots);
}
