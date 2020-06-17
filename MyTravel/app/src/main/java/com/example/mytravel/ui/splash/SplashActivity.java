package com.example.mytravel.ui.splash;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BaseActivity;
import com.example.mytravel.ui.auth.AuthActivity;
import com.example.mytravel.ui.main.MainActivity;
import com.example.mytravel.ui.privacy.PrivacyFragment;
import com.example.mytravel.utils.AppUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mytravel.utils.ConstApp.KEY_RELOAD_LANGUAGE;

public class SplashActivity extends BaseActivity implements SplashMvpView {
    public SplashMvpPresenter presenter;

    private boolean isReloadLanguage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setUnbinder(ButterKnife.bind(this));
        presenter = new SplashPresenter(this);

        Intent intent = getIntent();
        if (intent != null) {
            isReloadLanguage = intent.getBooleanExtra(KEY_RELOAD_LANGUAGE, false);
        }


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Configuration configuration = getResources().getConfiguration();
        configuration.setLocale(new Locale(MainApp.getInstance().getAppDataManager().getLanguageState()));
        getResources().updateConfiguration(configuration, displayMetrics);

        if (isReloadLanguage) {
            sendToActivity(new Intent(getBaseContext(), MainActivity.class));
        } else {
            sendToActivity(new Intent(getBaseContext(), AuthActivity.class));
        }
    }

    private void sendToActivity(Intent intent) {
        new Handler().postDelayed(() -> {
            startActivity(intent);
            finish();
        }, 600);
    }
}
