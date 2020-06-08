package com.example.mytravel.ui.auth.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;

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
    }


}
