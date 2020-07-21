package com.example.mytravel.ui.favorite.tour;

public interface TourFrMvpPresenter {
    void getListFavoritesTour();

    void setLoveTour(String idCity, String idExplore, String idTour);

    void removeLoveTour(String idTour);
}
