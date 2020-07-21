package com.example.mytravel.ui.listtour.highprice;

public interface HighPriceTourFrMvpPresenter {
    void getListHighPriceTour(String idCity, String idExplore);

    void setLoveTour(String idCity, String idExplore, String idTour);

    void removeLoveTour(String idTour);
}
