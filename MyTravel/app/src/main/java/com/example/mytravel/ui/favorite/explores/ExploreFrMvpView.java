package com.example.mytravel.ui.favorite.explores;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.city.Explore;

import java.util.ArrayList;

public interface ExploreFrMvpView extends MvpView {
    void successGetListFavoritesExplore(ArrayList<Explore> listExplore, String idCity, String idExplore);
}
