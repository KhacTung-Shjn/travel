package com.example.mytravel.ui.listtour.goodprice;

public interface GoodPriceTourFrMvpPresenter {
    void getListGoodPriceTour(String idCity, String idExplore);

    void setLoveTour(String idCity, String idExplore, String idTour);

    void removeLoveTour(String idTour);
}
