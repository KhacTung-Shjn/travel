package com.example.mytravel.ui.auth.confirrm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.ui.auth.login.LoginFragment;
import com.example.mytravel.ui.auth.register.RegisterFragment;
import com.example.mytravel.utils.AppUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmFragment extends BaseFragment implements ConfirmFrMvpView {
    public static final String TAG = ConfirmFragment.class.getSimpleName();

    private ConfirmFrMvpPresenter presenter;

    public static ConfirmFragment newInstance() {
        ConfirmFragment confirmFragment = new ConfirmFragment();
        return confirmFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ConfirmFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }
    }

    @OnClick(R.id.btnNewTravel)
    public void onClickRegister(View view) {
        if (getFragmentManager() != null) {
            AppUtils.replaceFragment(getFragmentManager(),
                    R.id.frAuth, RegisterFragment.newInstance(),
                    true, RegisterFragment.TAG);
        }
    }

    @OnClick(R.id.btnLogin)
    public void onClickLogin(View view) {
        if (getFragmentManager() != null) {
            AppUtils.replaceFragment(getFragmentManager(),
                    R.id.frAuth, LoginFragment.newInstance(),
                    true, LoginFragment.TAG);
        }
    }


}
