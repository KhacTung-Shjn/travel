package com.example.mytravel.ui.favorite.tour;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.city.TourPopular;

import java.util.ArrayList;

public interface TourFrMvpView extends MvpView {
    void successGetListFavoritesTour(ArrayList<TourPopular> listTours, String idCity, String idExplore);
}
