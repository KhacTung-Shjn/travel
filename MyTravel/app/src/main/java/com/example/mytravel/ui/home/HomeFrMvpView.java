package com.example.mytravel.ui.home;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.city.City;

import java.util.ArrayList;

public interface HomeFrMvpView extends MvpView {
    void successGetListCity(ArrayList<City> listCities);
}
