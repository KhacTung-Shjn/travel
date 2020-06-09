package com.example.mytravel.ui.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.ui.auth.register.RegisterFragment;
import com.example.mytravel.ui.main.MainActivity;
import com.example.mytravel.utils.AppUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements LoginFrMvpView {
    public static final String TAG = LoginFragment.class.getSimpleName();

    private LoginFrMvpPresenter presenter;

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }
    }

    @OnClick(R.id.btnSignIn)
    public void onClickLogin() {
        //TODO FAKE
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @OnClick(R.id.tvRegister)
    public void onClickRegister() {
        if (getFragmentManager() != null) {
            AppUtils.replaceFragment(getFragmentManager(),
                    R.id.frAuth, RegisterFragment.newInstance(),
                    true, RegisterFragment.TAG);
        }
    }


}
