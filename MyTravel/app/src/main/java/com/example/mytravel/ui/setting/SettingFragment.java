package com.example.mytravel.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.user.UserInformation;
import com.example.mytravel.ui.auth.AuthActivity;
import com.example.mytravel.ui.frame.FrameActivity;
import com.example.mytravel.ui.splash.SplashActivity;
import com.facebook.login.LoginManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.example.mytravel.utils.ConstApp.KEY_CONFIRM_PASS_CODE;
import static com.example.mytravel.utils.ConstApp.KEY_RELOAD_LANGUAGE;
import static com.example.mytravel.utils.ConstApp.REQUEST_CODE_PRIVACY;

public class SettingFragment extends BaseFragment implements SettingFrMvpView, CompoundButton.OnCheckedChangeListener {
    public static final String TAG = SettingFragment.class.getSimpleName();
    private SettingFrMvpPresenter presenter;
    private AlertDialog dialogLogout;


    @BindView(R.id.switchLanguage)
    Switch switchLanguage;
    @BindView(R.id.switchPrivacy)
    Switch switchPrivacy;
    @BindView(R.id.switchNoti)
    Switch switchNoti;

    public static SettingFragment newInstance() {
        SettingFragment settingFragment = new SettingFragment();
        return settingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SettingFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }

        if (MainApp.getInstance().getAppDataManager().getLanguageState().equals("vi")) {
            switchLanguage.setChecked(true);
        } else {
            switchLanguage.setChecked(false);
        }

        switchPrivacy.setChecked(MainApp.getInstance().getAppDataManager().isPrivacy());
        switchNoti.setChecked(MainApp.getInstance().getAppDataManager().isNotification());
        switchLanguage.setOnCheckedChangeListener(this);
        switchPrivacy.setOnCheckedChangeListener(this);
        switchNoti.setOnCheckedChangeListener(this);
    }

    @OnClick(R.id.tvLogOut)
    public void onCLickLogOut() {
        if (getActivity() != null && getContext() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View mView = getLayoutInflater().inflate(R.layout.dialog_logout, null);
            builder.setView(mView);
            dialogLogout = builder.create();
            Objects.requireNonNull(dialogLogout.getWindow()).setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));

            mView.findViewById(R.id.btnYes).setOnClickListener(v -> {
                if (getActivity() != null) {
                    getAppDataManager().setUserInformation(new UserInformation());
                    getAppDataManager().setPasswordCurrent("");
                    getAppDataManager().setCheckLogin(false);
                    MainApp.getInstance().getAuth().signOut();
                    LoginManager.getInstance().logOut();
                    startActivity(new Intent(getActivity(), AuthActivity.class));
                    getActivity().finish();
                    showMessage(R.string.msg_success_log_out);
                }
            });
            mView.findViewById(R.id.btnNo).setOnClickListener(v -> dialogLogout.dismiss());

            dialogLogout.setCancelable(true);
            dialogLogout.show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (dialogLogout != null)
            dialogLogout.dismiss();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switchLanguage: {
                presenter.setLanguageApp(isChecked, getResources());
                break;
            }
            case R.id.switchNoti: {
                MainApp.getInstance().getAppDataManager().setNotification(isChecked);
                break;
            }
            case R.id.switchPrivacy: {
                if (isChecked) {
                    startActivityForResult(FrameActivity.newIntentPrivacy(getContext()), REQUEST_CODE_PRIVACY);
                } else {
                    MainApp.getInstance().getAppDataManager().setPrivacy(false);
                    MainApp.getInstance().getAppDataManager().setPassCode("");
                }
                break;
            }
        }
    }

    @Override
    public void reloadView() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            intent.putExtra(KEY_RELOAD_LANGUAGE, true);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PRIVACY && resultCode == RESULT_OK && data != null) {
            boolean isPrivacy = data.getBooleanExtra(KEY_CONFIRM_PASS_CODE, false);
            MainApp.getInstance().getAppDataManager().setPrivacy(isPrivacy);
            switchPrivacy.setChecked(isPrivacy);
        }
    }

    @OnClick(R.id.tvInformation)
    public void onClickShowInformation() {
        if (getActivity() != null && getContext() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View mView = getLayoutInflater().inflate(R.layout.dialog_information_app, null);
            builder.setView(mView);
            dialogLogout = builder.create();
            Objects.requireNonNull(dialogLogout.getWindow()).setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
            dialogLogout.setCancelable(true);
            dialogLogout.show();
        }
    }

    @OnClick(R.id.tvChangePassword)
    public void onClickChangePassword() {
        if (getContext() != null)
            startActivity(FrameActivity.newIntentChangePass(getContext()));
    }
}
