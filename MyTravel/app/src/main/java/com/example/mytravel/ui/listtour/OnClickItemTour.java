package com.example.mytravel.ui.listtour;

import com.example.mytravel.models.city.TourPopular;

public interface OnClickItemTour {
    void onClickItem(TourPopular tourPopular);

    void onSetIsLove(String idTour, boolean isLove);
}
