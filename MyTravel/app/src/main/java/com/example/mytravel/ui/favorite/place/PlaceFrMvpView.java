package com.example.mytravel.ui.favorite.place;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.city.Place;

import java.util.ArrayList;

public interface PlaceFrMvpView extends MvpView {
    void successGetListFavoritesPlace(ArrayList<Place> listPlaces, String idCity, String idExplore);
}
