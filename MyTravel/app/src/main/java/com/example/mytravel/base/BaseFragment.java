package com.example.mytravel.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mytravel.MainApp;
import com.example.mytravel.data.AppDataManager;
import com.example.mytravel.utils.CommonUtils;
import com.example.mytravel.utils.ViewDialog;

import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements MvpView {

    private Unbinder unbinder;
    private BaseActivity activity;
    private AppDataManager appDataManager;
    private ViewDialog viewDialog;

    public BaseActivity getBaseActivity() {
        return activity;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.activity = (BaseActivity) context;
        }
    }

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appDataManager = MainApp.getInstance().getAppDataManager();
        viewDialog = new ViewDialog(activity);
    }

    public AppDataManager getAppDataManager() {
        return appDataManager;
    }

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void showMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void showLoading() {
        if (getBaseActivity() != null)
            getBaseActivity().runOnUiThread(() -> {
                hideLoading();
                if (viewDialog != null) {
                    viewDialog.showDialog();
                }
            });
    }

    @Override
    public void hideLoading() {
        if (getBaseActivity() != null)
            getBaseActivity().runOnUiThread(() -> {
                if (viewDialog != null) {
                    viewDialog.hideDialog();
                }
            });

    }
}
