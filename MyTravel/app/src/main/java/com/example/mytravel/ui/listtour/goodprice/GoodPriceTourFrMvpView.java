package com.example.mytravel.ui.listtour.goodprice;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.city.TourPopular;

import java.util.ArrayList;

public interface GoodPriceTourFrMvpView extends MvpView {
    void successListTour(ArrayList<TourPopular> listTourNew);
}
