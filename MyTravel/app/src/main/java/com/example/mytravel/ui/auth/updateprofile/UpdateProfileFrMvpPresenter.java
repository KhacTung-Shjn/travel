package com.example.mytravel.ui.auth.updateprofile;

import com.example.mytravel.models.user.UserInformation;

public interface UpdateProfileFrMvpPresenter {
    void onClickUpdateProfile(String name, String email, boolean isMale, boolean isFeMale,
                              String phone, String address, String birth,
                              UserInformation userInformation);
}
