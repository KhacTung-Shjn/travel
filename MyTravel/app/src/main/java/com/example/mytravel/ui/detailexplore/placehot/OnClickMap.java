package com.example.mytravel.ui.detailexplore.placehot;

import com.example.mytravel.models.city.PlaceHot;

public interface OnClickMap {
    void onClickOpenMap(String lat, String lng, PlaceHot placeHot);

    void failOpenMap(String msgError);
}
