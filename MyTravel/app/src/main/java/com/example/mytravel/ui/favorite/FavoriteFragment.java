package com.example.mytravel.ui.favorite;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.ui.home.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class FavoriteFragment extends BaseFragment implements FavoriteFrMvpView, ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    public static final String TAG = FavoriteFragment.class.getSimpleName();
    private FavoriteFrMvpPresenter presenter;

    @BindView(R.id.vpFavorites)
    ViewPager vpFavorites;
    @BindView(R.id.rgFavorites)
    RadioGroup rgFavorites;
    @BindView(R.id.rbPlaces)
    RadioButton rbPlaces;
    @BindView(R.id.rbVideos)
    RadioButton rbVideos;
    @BindView(R.id.rbPhotos)
    RadioButton rbPhotos;
    @BindView(R.id.rbTours)
    RadioButton rbTours;

    private FavoritesViewPagerAdapter favoritesViewPagerAdapter;

    public static FavoriteFragment newInstance() {
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        return favoriteFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FavoriteFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }
        if (getFragmentManager() != null) {
            favoritesViewPagerAdapter = new FavoritesViewPagerAdapter(getFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            vpFavorites.setAdapter(favoritesViewPagerAdapter);
            vpFavorites.addOnPageChangeListener(this);
        }
        rgFavorites.setOnCheckedChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        rgFavorites.check(rgFavorites.getChildAt(position).getId());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (vpFavorites != null) {
            switch (checkedId) {
                case R.id.rbPlaces: {
                    vpFavorites.setCurrentItem(0);
                    break;
                }
                case R.id.rbVideos: {
                    vpFavorites.setCurrentItem(1);
                    break;
                }
                case R.id.rbPhotos: {
                    vpFavorites.setCurrentItem(2);
                    break;
                }
                case R.id.rbTours: {
                    vpFavorites.setCurrentItem(3);
                    break;
                }
            }
        }
    }
}
