package com.example.mytravel.ui.detailcity;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.city.Explore;
import com.example.mytravel.models.city.TourPopular;

import java.util.ArrayList;

public interface DetailCityFrMvpView extends MvpView {
    void successGetListExplore(ArrayList<Explore> listExplore);

    void successGetListTourPopular(ArrayList<TourPopular> listTours);
}
