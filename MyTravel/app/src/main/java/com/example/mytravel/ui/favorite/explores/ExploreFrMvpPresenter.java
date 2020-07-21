package com.example.mytravel.ui.favorite.explores;

public interface ExploreFrMvpPresenter {
    void getListFavoritesExplore();

    void setLoveExplore(String idCity, String idExplore);

    void removeLoveExplore(String idExplore);

}
