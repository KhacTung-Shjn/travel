package com.example.mytravel.ui.listtour.highprice;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.city.TourPopular;

import java.util.ArrayList;

public interface HighPriceTourFrMvpView extends MvpView {
    void successListTour(ArrayList<TourPopular> listTourNew);
}
