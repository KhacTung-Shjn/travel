package com.example.mytravel.data.presenter;

import com.example.mytravel.models.user.UserInformation;

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
}
