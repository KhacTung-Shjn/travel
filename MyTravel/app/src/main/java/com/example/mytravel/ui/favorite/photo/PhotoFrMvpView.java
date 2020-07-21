package com.example.mytravel.ui.favorite.photo;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.imagehot.ImageHot;

import java.util.ArrayList;

public interface PhotoFrMvpView extends MvpView {
    void successGetListFavoritesPhoto(ArrayList<ImageHot> listPhotos);
}
