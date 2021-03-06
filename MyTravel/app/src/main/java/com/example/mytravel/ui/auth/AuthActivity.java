package com.example.mytravel.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BaseActivity;
import com.example.mytravel.ui.auth.confirrm.ConfirmFragment;
import com.example.mytravel.ui.main.MainActivity;
import com.example.mytravel.ui.privacy.PrivacyFragment;
import com.example.mytravel.utils.AppUtils;

import butterknife.ButterKnife;

public class AuthActivity extends BaseActivity implements AuthMvpView {
    public static final String TAG = AuthActivity.class.getSimpleName();

    public AuthMvpPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setUnbinder(ButterKnife.bind(this));
        presenter = new AuthPresenter(this);

        if (MainApp.getInstance().getAppDataManager().isCheckLogin()) {
            if (MainApp.getInstance().getAppDataManager().isPrivacy()) {
                AppUtils.replaceFragment(getSupportFragmentManager(), R.id.frAuth, PrivacyFragment.newInstance(true), false, PrivacyFragment.TAG);
            } else {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        } else {
            AppUtils.replaceFragment(getSupportFragmentManager(), R.id.frAuth, ConfirmFragment.newInstance(), false, ConfirmFragment.TAG);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

    }


}
