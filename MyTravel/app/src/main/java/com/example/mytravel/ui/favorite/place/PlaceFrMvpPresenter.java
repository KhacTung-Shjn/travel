package com.example.mytravel.ui.favorite.place;

public interface PlaceFrMvpPresenter {
    void getListFavoritesPlace();

    void setLovePlace(String idCity, String idExplore, String idPlace);

    void removeLovePlace(String idPlace);
}
