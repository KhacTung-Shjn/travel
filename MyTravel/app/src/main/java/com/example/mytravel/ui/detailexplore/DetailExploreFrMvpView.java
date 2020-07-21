package com.example.mytravel.ui.detailexplore;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.city.Place;
import com.example.mytravel.models.city.PlaceHot;

import java.util.ArrayList;

public interface DetailExploreFrMvpView extends MvpView {
    void successGetListPlace(ArrayList<Place> listPlaces);

    void successGetHotMarker(ArrayList<PlaceHot> listHotMarker);
}
