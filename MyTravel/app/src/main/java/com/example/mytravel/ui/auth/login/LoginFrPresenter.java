package com.example.mytravel.ui.auth.login;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.utils.CommonUtils;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginFrPresenter extends BasePresenter implements LoginFrMvpPresenter {

    private LoginFrMvpView getMvpView;

    public LoginFrPresenter(LoginFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }

    @Override
    public void signInUser(Activity activity, String email, String password, boolean isKeep) {
        if (isValidate(email, password)) {
            if (CommonUtils.isNetworkConnected(activity.getBaseContext())) {
                MainApp.getInstance().getAuth().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity, task -> {
                            if (task.isSuccessful()) {
                                getMvpView.showMessage(R.string.msg_success_login);
                                FirebaseUser user = MainApp.getInstance().getAuth().getCurrentUser();
                                getDataManager().setCheckLogin(isKeep);
                                getMvpView.loginSuccess(user);
                            } else {
                                getMvpView.showMessage(R.string.msg_error_login);
                            }
                        });
            } else {
                getMvpView.showMessage(R.string.msg_error_network);
            }
        }
    }

    @Override
    public void firebaseAuthWithGoogle(Activity activity, String idToken, boolean isKeep) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        MainApp.getInstance().getAuth().signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        getMvpView.showMessage(R.string.msg_success_login);
                        FirebaseUser user = MainApp.getInstance().getAuth().getCurrentUser();
                        getDataManager().setCheckLogin(isKeep);
                        getMvpView.loginSuccess(user);
                    } else {
                        getMvpView.showMessage(R.string.msg_error_authentication);
                        Log.w(LoginFragment.TAG, "signInWithCredential:failure", task.getException());
                    }
                });
    }

    private boolean isValidate(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            getMvpView.showMessage(R.string.msg_error_enter_email);
            getMvpView.focusEdit(0);
            return false;
        }

        if (!CommonUtils.isEmailValid(email)) {
            getMvpView.showMessage(R.string.msg_error_enter_email);
            getMvpView.focusEdit(0);
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            getMvpView.showMessage(R.string.msg_error_enter_password);
            getMvpView.focusEdit(1);
            return false;
        }
        return true;
    }


}
