package com.example.mytravel.data.presenter;

import android.content.SharedPreferences;

import static com.example.mytravel.utils.ConstApp.KEY_CONFIRM_PASS_CODE;

public class MyPreferencee implements PreferenceHelper {
    private SharedPreferences sharedPreferences;

    public static final String KEY_CHECK_LOGIN = "KEY_CHECK_LOGIN";
    public static final String KEY_LANGUAGE_STATE = "KEY_LANGUAGE_STATE";
    public static final String KEY_CHECK_PRIVACY = "KEY_CHECK_PRIVACY";


    public MyPreferencee(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
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
        return sharedPreferences.getString(KEY_LANGUAGE_STATE, "en");
    }

    @Override
    public void setDefaultLanguage(boolean isDefault) {
        
    }

    @Override
    public boolean isDefaultLanguage() {
        return false;
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
}
