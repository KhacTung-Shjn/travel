package com.example.mytravel.ui.detailcity;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.city.Explore;

import java.util.ArrayList;

public interface DetailCityFrMvpView extends MvpView {
    void successGetListExplore(ArrayList<Explore> listExplore);
}
