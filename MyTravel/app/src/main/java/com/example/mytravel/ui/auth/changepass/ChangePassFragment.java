package com.example.mytravel.ui.auth.changepass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.user.UserInformation;
import com.example.mytravel.utils.CommonUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePassFragment extends BaseFragment implements ChangePassFrMvpView, View.OnClickListener {
    public static final String TAG = ChangePassFragment.class.getSimpleName();

    private ChangePassFrMvpPresenter presenter;

    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.etRePassword)
    EditText etRePassword;

    private String currentPassWord;
    private ImageView ivBack;

    public static ChangePassFragment newInstance() {
        ChangePassFragment changePassFragment = new ChangePassFragment();
        return changePassFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ChangePassFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }
        ivBack = Objects.requireNonNull(getActivity()).findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        currentPassWord = getAppDataManager().getPasswordCurrent();
    }

    @Override
    public void onClick(View v) {
        getOnBack();
    }

    private void getOnBack() {
        if (getActivity() != null) {
            if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            } else {
                getActivity().finish();
            }
        }
    }

    @OnClick(R.id.btnChange)
    public void onClickChangePassword() {
        presenter.onClickChangePassword(etPassword.getText().toString(),
                etNewPassword.getText().toString(),
                etRePassword.getText().toString(), currentPassWord);
    }

    @Override
    public void focusEdit(int i) {
        if (getContext() != null)
            switch (i) {
                case 0: {
                    CommonUtils.showSoftInput(etPassword, getContext());
                    break;
                }
                case 1: {
                    CommonUtils.showSoftInput(etNewPassword, getContext());
                    break;
                }
                case 2: {
                    CommonUtils.showSoftInput(etRePassword, getContext());
                    break;
                }
            }
    }

    @Override
    public void changePasswordSuccess() {
        FirebaseUser user = MainApp.getInstance().getAuth().getCurrentUser();
        UserInformation userInformation = CommonUtils.getUserInfo(user);
        getAppDataManager().setUserInformation(userInformation);
        getOnBack();
    }
}
