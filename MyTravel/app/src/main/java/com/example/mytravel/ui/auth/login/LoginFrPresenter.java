package com.example.mytravel.ui.auth.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.City;
import com.example.mytravel.models.favorites.FavoritesPlace;
import com.example.mytravel.models.user.UserInformation;
import com.example.mytravel.utils.CommonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

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

    @Override
    public void getProfile(UserInformation userInformation) {
        MainApp.getInstance().getFirebaseFireStore()
                .collection("user")
                .whereEqualTo("email", userInformation.getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            if (task.getResult().getDocuments().size() != 0) {
                                DocumentSnapshot snapshot = task.getResult().getDocuments().get(0);
                                String address = String.valueOf(snapshot.get("address"));
                                String avatar = String.valueOf(snapshot.get("avatar"));
                                String birth = String.valueOf(snapshot.get("birth"));
                                String gender = String.valueOf(snapshot.get("gender"));
                                String phone = String.valueOf(snapshot.get("phone"));
                                userInformation.setAddress(address);
                                userInformation.setAvatar(avatar);
                                userInformation.setBirth(birth);
                                userInformation.setGender(Integer.parseInt(gender));
                                userInformation.setPhone(phone);
                                getMvpView.getFinalLogin(userInformation);
                            } else {
                                pushUserToFirebase(userInformation);
                            }
                        }
                    }
                });
    }

    public void pushUserToFirebase(UserInformation userInformation) {
        HashMap<String, Object> user = new HashMap<>();
        user.put("address", "");
        user.put("avatar", "");
        user.put("birth", "");
        user.put("email", userInformation.getEmail());
        user.put("gender", 1);
        user.put("name", "Customer");
        user.put("phone", "");

        MainApp.getInstance().getFirebaseFireStore()
                .collection("user")
                .add(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                getProfile(userInformation);
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
