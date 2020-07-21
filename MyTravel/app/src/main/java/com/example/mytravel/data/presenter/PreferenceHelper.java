package com.example.mytravel.data.presenter;

import com.example.mytravel.models.favorites.FavoritesExplore;
import com.example.mytravel.models.favorites.FavoritesPhoto;
import com.example.mytravel.models.favorites.FavoritesPlace;
import com.example.mytravel.models.favorites.FavoritesTour;
import com.example.mytravel.models.user.UserInformation;

import java.util.ArrayList;

public interface PreferenceHelper {

    boolean isCheckLogin();

    void setCheckLogin(boolean checkLogin);

    void setLanguageState(String language);

    String getLanguageState();

    void setPrivacy(boolean isPrivacy);

    boolean isPrivacy();

    void setPassCode(String passCode);

    String getPassCode();

    UserInformation getUserInformation();

    void setUserInformation(UserInformation userInformation);

    String getPasswordCurrent();

    void setPasswordCurrent(String passwordCurrent);

    ArrayList<FavoritesPlace> getListFavoritesPlace();

    void setListFavoritesPlace(ArrayList<FavoritesPlace> listFavoritesPlace);

    ArrayList<FavoritesExplore> getListFavoritesExplore();

    void setListFavoritesExplore(ArrayList<FavoritesExplore> listFavoritesExplore);

    ArrayList<FavoritesPhoto> getListFavoritesPhoto();

    void setListFavoritesPhoto(ArrayList<FavoritesPhoto> listFavoritesPhoto);

    ArrayList<FavoritesTour> getListFavoritesTour();

    void setListFavoritesTour(ArrayList<FavoritesTour> favoritesTours);
}
