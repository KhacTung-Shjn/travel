package com.example.mytravel.ui.favorite.place;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;

import butterknife.ButterKnife;

public class PlaceFragment extends BaseFragment implements PlaceFrMvpView {
    public static final String TAG = PlaceFragment.class.getSimpleName();

    private PlaceFrMvpPresenter presenter;

    public static PlaceFragment newInstance() {
        PlaceFragment placeFragment = new PlaceFragment();
        return placeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PlaceFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }
    }
}
