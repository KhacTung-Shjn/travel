package com.example.mytravel.ui.detailcity;

import com.example.mytravel.models.city.Explore;
import com.example.mytravel.models.city.TourPopular;

public interface OnClickItemInCity {
    void onClickTour(TourPopular tourPopular);

    void onClickExplore(Explore explore);

    void onClickIsLoveExplore(String IdExplore, boolean isLove);
}
