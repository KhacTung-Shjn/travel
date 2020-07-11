package com.example.mytravel.ui.listtour.lowprice;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.city.TourPopular;

import java.util.ArrayList;

public interface LowPriceTourFrMvpView extends MvpView {
    void successListTour(ArrayList<TourPopular> listTourNew);
}
