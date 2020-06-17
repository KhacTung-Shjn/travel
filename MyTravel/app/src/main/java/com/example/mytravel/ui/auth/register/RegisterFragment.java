package com.example.mytravel.ui.auth.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.ui.auth.login.LoginFragment;
import com.example.mytravel.utils.AppUtils;
import com.example.mytravel.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterFragment extends BaseFragment implements RegisterFrMvpView {
    public static final String TAG = RegisterFragment.class.getSimpleName();

    private RegisterFrMvpPresenter presenter;

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etRePassword)
    EditText etRePassword;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.rbPromise)
    RadioButton rbPromise;

    public static RegisterFragment newInstance() {
        RegisterFragment registerFragment = new RegisterFragment();
        return registerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegisterFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }
    }

    @OnClick(R.id.btnSignIn)
    public void onClickSignUp() {
        presenter.signUpNewUser(getActivity(), etName.getText().toString(),
                etPassword.getText().toString(),
                etRePassword.getText().toString(),
                etEmail.getText().toString()
        );
    }

    @Override
    public void focusEdit(int i) {
        if (getContext() != null) {
            switch (i) {
                case 0: {
                    CommonUtils.showSoftInput(etName, getContext());
                    break;
                }
                case 1: {
                    CommonUtils.showSoftInput(etPassword, getContext());
                    break;
                }
                case 2: {
                    CommonUtils.showSoftInput(etRePassword, getContext());
                    break;
                }
                case 3: {
                    CommonUtils.showSoftInput(etEmail, getContext());
                    break;
                }
            }
        }
    }

    @Override
    public void registerSuccess() {
        if (getFragmentManager() != null) {
            AppUtils.replaceFragment(getFragmentManager(),
                    R.id.frAuth,
                    LoginFragment.newInstance(),
                    false,
                    LoginFragment.TAG);
        }
    }
}
