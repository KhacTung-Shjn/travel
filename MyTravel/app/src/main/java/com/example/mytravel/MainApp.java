package com.example.mytravel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;

import com.example.mytravel.data.AppDataManager;
import com.example.mytravel.data.presenter.MyPreference;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainApp extends Application {
    public static final String TAG = MainApp.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    public static MainApp instance;
    private Activity mCurrentActivity = null;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private MyPreference presenter;
    private AppDataManager appDataManager;
    private FirebaseFirestore firebaseFirestore;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        presenter = new MyPreference(getSharedPreferences(TAG, MODE_PRIVATE));
        appDataManager = new AppDataManager(presenter, getApplicationContext());
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public static MainApp getInstance() {
        return instance;
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public MyPreference getPresenter() {
        return presenter;
    }

    public AppDataManager getAppDataManager() {
        return appDataManager;
    }

    public FirebaseFirestore getFirebaseFireStore() {
        return firebaseFirestore;
    }
}
