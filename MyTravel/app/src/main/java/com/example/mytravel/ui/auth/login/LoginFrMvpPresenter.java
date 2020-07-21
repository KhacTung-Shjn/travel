package com.example.mytravel.ui.auth.login;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

import com.example.mytravel.models.user.UserInformation;
import com.facebook.CallbackManager;

public interface LoginFrMvpPresenter {
    void signInUser(Activity activity, String email, String password, boolean isKeep);

    void firebaseAuthWithGoogle(Activity activity, String idToken, boolean isKeep);

    void getProfile(UserInformation userInformation);

}
