package com.example.mytravel.ui.favorite.tour;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;

import butterknife.ButterKnife;

public class TourFragment extends BaseFragment implements TourFrMvpView {
    public static final String TAG = TourFragment.class.getSimpleName();

    private TourFrMvpPresenter presenter;

    public static TourFragment newInstance() {
        TourFragment tourFragment = new TourFragment();
        return tourFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TourFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tour, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }
    }
}
