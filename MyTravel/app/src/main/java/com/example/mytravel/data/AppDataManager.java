package com.example.mytravel.data;

import android.content.Context;

import com.example.mytravel.data.presenter.MyPreference;
import com.example.mytravel.models.user.UserInformation;

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
}
