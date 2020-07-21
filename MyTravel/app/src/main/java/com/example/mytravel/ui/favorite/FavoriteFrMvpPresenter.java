package com.example.mytravel.ui.favorite;

public interface FavoriteFrMvpPresenter {
    void getFavorites(String email);

    void getFavoritesExplore(String email);

    void getFavoritesPhoto(String email);

    void getFavoritesTour(String email);
}
