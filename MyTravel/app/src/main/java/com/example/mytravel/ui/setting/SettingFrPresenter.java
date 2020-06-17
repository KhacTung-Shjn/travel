package com.example.mytravel.ui.setting;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.mytravel.base.BasePresenter;

import java.util.Locale;

public class SettingFrPresenter extends BasePresenter implements SettingFrMvpPresenter {

    private SettingFrMvpView getMvpView;

    public SettingFrPresenter(SettingFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }

    @Override
    public void setLanguageApp(boolean isChecked, Resources resources) {
        String language = "en";
        if (isChecked) {
            language = "vi";
            getDataManager().setLanguageState(language);

        } else {
            language = "en";
            getDataManager().setLanguageState(language);
        }
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(new Locale(language));
        resources.updateConfiguration(configuration, displayMetrics);

        getMvpView.reloadView();
    }
}
