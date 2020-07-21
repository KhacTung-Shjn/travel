package com.example.mytravel.data.presenter;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.mytravel.models.favorites.FavoritesExplore;
import com.example.mytravel.models.favorites.FavoritesPhoto;
import com.example.mytravel.models.favorites.FavoritesPlace;
import com.example.mytravel.models.favorites.FavoritesTour;
import com.example.mytravel.models.user.UserInformation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.mytravel.utils.ConstApp.KEY_CHECK_LOGIN;
import static com.example.mytravel.utils.ConstApp.KEY_CHECK_PRIVACY;
import static com.example.mytravel.utils.ConstApp.KEY_CONFIRM_PASS_CODE;
import static com.example.mytravel.utils.ConstApp.KEY_LANGUAGE_STATE;
import static com.example.mytravel.utils.ConstApp.KEY_LIST_FAVORITES_EXPLORE;
import static com.example.mytravel.utils.ConstApp.KEY_LIST_FAVORITES_PHOTO;
import static com.example.mytravel.utils.ConstApp.KEY_LIST_FAVORITES_PLACE;
import static com.example.mytravel.utils.ConstApp.KEY_LIST_FAVORITES_TOUR;
import static com.example.mytravel.utils.ConstApp.KEY_PASS_CURRENT;
import static com.example.mytravel.utils.ConstApp.KEY_USER_INFORMATION;

public class MyPreference implements PreferenceHelper {
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public MyPreference(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        gson = new Gson();
    }

    @Override
    public boolean isCheckLogin() {
        return sharedPreferences.getBoolean(KEY_CHECK_LOGIN, false);
    }

    @Override
    public void setCheckLogin(boolean checkLogin) {
        sharedPreferences.edit().putBoolean(KEY_CHECK_LOGIN, checkLogin).apply();
    }

    @Override
    public void setLanguageState(String language) {
        sharedPreferences.edit().putString(KEY_LANGUAGE_STATE, language).apply();
    }

    @Override
    public String getLanguageState() {
        return sharedPreferences.getString(KEY_LANGUAGE_STATE, Locale.getDefault().getLanguage());
    }

    @Override
    public void setPrivacy(boolean isPrivacy) {
        sharedPreferences.edit().putBoolean(KEY_CHECK_PRIVACY, isPrivacy).apply();
    }

    @Override
    public boolean isPrivacy() {
        return sharedPreferences.getBoolean(KEY_CHECK_PRIVACY, false);
    }

    @Override
    public void setPassCode(String passCode) {
        sharedPreferences.edit().putString(KEY_CONFIRM_PASS_CODE, passCode).apply();
    }

    @Override
    public String getPassCode() {
        return sharedPreferences.getString(KEY_CONFIRM_PASS_CODE, "");
    }

    @Override
    public UserInformation getUserInformation() {
        String s = sharedPreferences.getString(KEY_USER_INFORMATION, null);
        if (!TextUtils.isEmpty(s)) {
            return gson.fromJson(s, UserInformation.class);
        }
        return null;
    }

    @Override
    public void setUserInformation(UserInformation userInformation) {
        if (userInformation == null) return;
        String dataJson = gson.toJson(userInformation);
        sharedPreferences.edit().putString(KEY_USER_INFORMATION, dataJson).apply();
    }

    @Override
    public String getPasswordCurrent() {
        return sharedPreferences.getString(KEY_PASS_CURRENT, "");
    }

    @Override
    public void setPasswordCurrent(String passwordCurrent) {
        sharedPreferences.edit().putString(KEY_PASS_CURRENT, passwordCurrent).apply();
    }

    @Override
    public ArrayList<FavoritesPlace> getListFavoritesPlace() {
        ArrayList<FavoritesPlace> favoritesPlaces;
        String s = sharedPreferences.getString(KEY_LIST_FAVORITES_PLACE, null);
        if (!TextUtils.isEmpty(s)) {
            Type type = new TypeToken<ArrayList<FavoritesPlace>>() {
            }.getType();
            favoritesPlaces = gson.fromJson(s, type);
            return favoritesPlaces;
        }
        return null;
    }

    @Override
    public void setListFavoritesPlace(ArrayList<FavoritesPlace> listFavoritesPlace) {
        if (listFavoritesPlace == null) return;
        String dataJson = gson.toJson(listFavoritesPlace);
        sharedPreferences.edit().putString(KEY_LIST_FAVORITES_PLACE, dataJson).apply();
    }

    @Override
    public ArrayList<FavoritesExplore> getListFavoritesExplore() {
        ArrayList<FavoritesExplore> favoritesExplores;
        String s = sharedPreferences.getString(KEY_LIST_FAVORITES_EXPLORE, null);
        if (!TextUtils.isEmpty(s)) {
            Type type = new TypeToken<ArrayList<FavoritesExplore>>() {
            }.getType();
            favoritesExplores = gson.fromJson(s, type);
            return favoritesExplores;
        }
        return null;
    }

    @Override
    public void setListFavoritesExplore(ArrayList<FavoritesExplore> listFavoritesExplore) {
        if (listFavoritesExplore == null) return;
        String dataJson = gson.toJson(listFavoritesExplore);
        sharedPreferences.edit().putString(KEY_LIST_FAVORITES_EXPLORE, dataJson).apply();
    }

    @Override
    public ArrayList<FavoritesPhoto> getListFavoritesPhoto() {
        ArrayList<FavoritesPhoto> favoritesPhotos;
        String s = sharedPreferences.getString(KEY_LIST_FAVORITES_PHOTO, null);
        if (!TextUtils.isEmpty(s)) {
            Type type = new TypeToken<ArrayList<FavoritesPhoto>>() {
            }.getType();
            favoritesPhotos = gson.fromJson(s, type);
            return favoritesPhotos;
        }
        return null;
    }

    @Override
    public void setListFavoritesPhoto(ArrayList<FavoritesPhoto> listFavoritesPhoto) {
        if (listFavoritesPhoto == null) return;
        String dataJson = gson.toJson(listFavoritesPhoto);
        sharedPreferences.edit().putString(KEY_LIST_FAVORITES_PHOTO, dataJson).apply();
    }

    @Override
    public ArrayList<FavoritesTour> getListFavoritesTour() {
        ArrayList<FavoritesTour> favoritesTours;
        String s = sharedPreferences.getString(KEY_LIST_FAVORITES_TOUR, null);
        if (!TextUtils.isEmpty(s)) {
            Type type = new TypeToken<ArrayList<FavoritesTour>>() {
            }.getType();
            favoritesTours = gson.fromJson(s, type);
            return favoritesTours;
        }
        return null;
    }

    @Override
    public void setListFavoritesTour(ArrayList<FavoritesTour> favoritesTours) {
        if (favoritesTours == null) return;
        String dataJson = gson.toJson(favoritesTours);
        sharedPreferences.edit().putString(KEY_LIST_FAVORITES_TOUR, dataJson).apply();
    }
}
