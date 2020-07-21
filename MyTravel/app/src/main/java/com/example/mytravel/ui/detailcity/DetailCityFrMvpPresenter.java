package com.example.mytravel.ui.detailcity;

public interface DetailCityFrMvpPresenter {
    void getListExplore(String idCity);

    void getListTourPopular(String idCity);

    void setLoveExplore(String idCity, String idExplore);

    void removeLoveExplore(String idExplore);
}
