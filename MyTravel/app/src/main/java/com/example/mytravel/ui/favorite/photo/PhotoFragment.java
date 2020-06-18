package com.example.mytravel.ui.favorite.photo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;

import butterknife.ButterKnife;

public class PhotoFragment extends BaseFragment implements PhotoFrMvpView {
    public static final String TAG = PhotoFragment.class.getSimpleName();

    private PhotoFrMvpPresenter presenter;

    public static PhotoFragment newInstance() {
        PhotoFragment photoFragment = new PhotoFragment();
        return photoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PhotoFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }
    }
}
