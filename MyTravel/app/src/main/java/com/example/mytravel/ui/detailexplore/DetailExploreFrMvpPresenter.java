package com.example.mytravel.ui.detailexplore;

public interface DetailExploreFrMvpPresenter {
    void getListPlaces(String idCity, String idExplore, String nameExplore);

    void getAllListHotMarker();

    void setLoveExplore(String idCity, String idExplore);

    void removeLoveExplore(String idExplore);
}
