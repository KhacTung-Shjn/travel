package com.example.mytravel.ui.detailexplore.placelist;

import com.example.mytravel.models.city.Place;

public interface OnClickItemPlace {
    void onClickItem(Place place);

    void onClickIsLove(String idPlace, boolean isLove);
}
