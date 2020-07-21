package com.example.mytravel.ui.listtour.lowprice;

public interface LowPriceTourFrMvpPresenter {
    void getListLowPriceTour(String idCity, String idExplore);

    void setLoveTour(String idCity, String idExplore, String idTour);

    void removeLoveTour(String idTour);
}
