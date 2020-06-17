package com.example.mytravel.ui.auth.login;

import com.example.mytravel.base.MvpView;
import com.google.firebase.auth.FirebaseUser;

public interface LoginFrMvpView extends MvpView {
    void focusEdit(int i);

    void loginSuccess(FirebaseUser user);

}
