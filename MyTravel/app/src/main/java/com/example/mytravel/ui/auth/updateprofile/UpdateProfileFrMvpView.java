package com.example.mytravel.ui.auth.updateprofile;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.user.UserInformation;

public interface UpdateProfileFrMvpView extends MvpView {
    void focusEdit(int i);

    void updateProfileSuccess(UserInformation userInformation);
}
