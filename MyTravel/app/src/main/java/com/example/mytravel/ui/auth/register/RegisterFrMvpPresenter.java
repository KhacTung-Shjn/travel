package com.example.mytravel.ui.auth.register;

import android.app.Activity;

public interface RegisterFrMvpPresenter {
    void signUpNewUser(Activity activity,String nameUser, String password, String rePassword, String email);
}
