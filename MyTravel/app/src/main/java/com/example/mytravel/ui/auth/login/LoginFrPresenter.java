package com.example.mytravel.ui.auth.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.City;
import com.example.mytravel.utils.CommonUtils;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

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
                new LoadLoginAsyncTask(activity, email, password, isKeep).execute();
            } else {
                getMvpView.showMessage(R.string.msg_error_network);
            }
        }
    }

    @Override
    public void firebaseAuthWithGoogle(Activity activity, String idToken, boolean isKeep) {
        new LoadLoginGoogleAsyncTask(activity, idToken, isKeep).execute();
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

    @SuppressLint("StaticFieldLeak")
    class LoadLoginAsyncTask extends AsyncTask<Void, Void, Void> {
        private String email;
        private String password;
        private Activity activity;
        private boolean isKeep;

        public LoadLoginAsyncTask(Activity activity, String email, String password, boolean isKeep) {
            this.email = email;
            this.password = password;
            this.activity = activity;
            this.isKeep = isKeep;
        }

        @Override
        protected void onPreExecute() {
            getMvpView.showLoading();
        }

        @Override
        protected Void doInBackground(Void... voids) {
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getMvpView.hideLoading();
        }
    }

    @SuppressLint("StaticFieldLeak")
    class LoadLoginGoogleAsyncTask extends AsyncTask<Void, Void, Void> {

        private Activity activity;
        private String idToken;
        private boolean isKeep;

        public LoadLoginGoogleAsyncTask(Activity activity, String idToken, boolean isKeep) {
            this.activity = activity;
            this.idToken = idToken;
            this.isKeep = isKeep;
        }

        @Override
        protected void onPreExecute() {
            getMvpView.showLoading();
        }

        @Override
        protected Void doInBackground(Void... voids) {
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getMvpView.hideLoading();
        }
    }
}
