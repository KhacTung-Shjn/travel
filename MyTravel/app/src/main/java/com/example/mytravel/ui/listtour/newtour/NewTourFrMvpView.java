package com.example.mytravel.ui.listtour.newtour;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.city.TourPopular;

import java.util.ArrayList;

public interface NewTourFrMvpView extends MvpView {
    void successListTour(ArrayList<TourPopular> listTourNew);
}
