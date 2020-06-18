package com.example.mytravel.ui.favorite.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;

import butterknife.ButterKnife;

public class VideoFragment extends BaseFragment implements VideoFrMvpView {
    public static final String TAG = VideoFragment.class.getSimpleName();

    private VideoFrMvpPresenter presenter;

    public static VideoFragment newInstance() {
        VideoFragment videoFragment = new VideoFragment();
        return videoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new VideoFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }
    }
}
