package com.example.mytravel.data;

import android.content.Context;

import com.example.mytravel.data.presenter.MyPreference;
import com.example.mytravel.models.booktour.BookTour;
import com.example.mytravel.models.favorites.FavoritesExplore;
import com.example.mytravel.models.favorites.FavoritesPhoto;
import com.example.mytravel.models.favorites.FavoritesPlace;
import com.example.mytravel.models.favorites.FavoritesTour;
import com.example.mytravel.models.user.UserInformation;

import java.util.ArrayList;

public class AppDataManager implements DataManager {

    public static final String TAG = AppDataManager.class.getSimpleName();
    private MyPreference preference;
    private Context context;

    public AppDataManager(MyPreference preference, Context context) {
        this.preference = preference;
        this.context = context;
    }

    @Override
    public boolean isCheckLogin() {
        return preference.isCheckLogin();
    }

    @Override
    public void setCheckLogin(boolean checkLogin) {
        preference.setCheckLogin(checkLogin);
    }

    @Override
    public void setLanguageState(String language) {
        preference.setLanguageState(language);
    }

    @Override
    public String getLanguageState() {
        return preference.getLanguageState();
    }

    @Override
    public void setPrivacy(boolean isPrivacy) {
        preference.setPrivacy(isPrivacy);
    }

    @Override
    public boolean isPrivacy() {
        return preference.isPrivacy();
    }

    @Override
    public void setPassCode(String passCode) {
        preference.setPassCode(passCode);
    }

    @Override
    public String getPassCode() {
        return preference.getPassCode();
    }

    @Override
    public UserInformation getUserInformation() {
        return preference.getUserInformation();
    }

    @Override
    public void setUserInformation(UserInformation userInformation) {
        preference.setUserInformation(userInformation);
    }

    @Override
    public String getPasswordCurrent() {
        return preference.getPasswordCurrent();
    }

    @Override
    public void setPasswordCurrent(String passwordCurrent) {
        preference.setPasswordCurrent(passwordCurrent);
    }

    @Override
    public ArrayList<FavoritesPlace> getListFavoritesPlace() {
        return preference.getListFavoritesPlace();
    }

    @Override
    public void setListFavoritesPlace(ArrayList<FavoritesPlace> listFavoritesPlace) {
        preference.setListFavoritesPlace(listFavoritesPlace);
    }

    @Override
    public ArrayList<FavoritesExplore> getListFavoritesExplore() {
        return preference.getListFavoritesExplore();
    }

    @Override
    public void setListFavoritesExplore(ArrayList<FavoritesExplore> listFavoritesExplore) {
        preference.setListFavoritesExplore(listFavoritesExplore);
    }

    @Override
    public ArrayList<FavoritesPhoto> getListFavoritesPhoto() {
        return preference.getListFavoritesPhoto();
    }

    @Override
    public void setListFavoritesPhoto(ArrayList<FavoritesPhoto> listFavoritesPhoto) {
        preference.setListFavoritesPhoto(listFavoritesPhoto);
    }

    @Override
    public ArrayList<FavoritesTour> getListFavoritesTour() {
        return preference.getListFavoritesTour();
    }

    @Override
    public void setListFavoritesTour(ArrayList<FavoritesTour> favoritesTours) {
        preference.setListFavoritesTour(favoritesTours);
    }

    @Override
    public ArrayList<BookTour> getListBookTour() {
        return preference.getListBookTour();
    }

    @Override
    public void setListBookTour(ArrayList<BookTour> listBookTour) {
        preference.setListBookTour(listBookTour);
    }

    @Override
    public boolean isNotification() {
        return preference.isNotification();
    }

    @Override
    public void setNotification(boolean notification) {
        preference.setNotification(notification);
    }
}
