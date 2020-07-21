package com.example.mytravel.ui.listtour.newtour;

public interface NewTourFrMvpPresenter {
    void getListTour(String idCity, String idExplore);

    void setLoveTour(String idCity, String idExplore, String idTour);

    void removeLoveTour(String idTour);

}
