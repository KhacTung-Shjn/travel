package com.example.mytravel.ui.favorite;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mytravel.models.city.Place;
import com.example.mytravel.ui.favorite.photo.PhotoFragment;
import com.example.mytravel.ui.favorite.place.PlaceFragment;
import com.example.mytravel.ui.favorite.tour.TourFragment;
import com.example.mytravel.ui.favorite.explores.ExploreFragment;

import java.util.ArrayList;
import java.util.Objects;

public class FavoritesViewPagerAdapter extends FragmentStatePagerAdapter {

    public FavoritesViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0: {
                fragment = PlaceFragment.newInstance();
                break;
            }
            case 1: {
                fragment = ExploreFragment.newInstance();
                break;
            }
            case 2: {
                fragment = PhotoFragment.newInstance();
                break;
            }
            case 3: {
                fragment = TourFragment.newInstance();
                break;
            }
        }
        return Objects.requireNonNull(fragment);
    }

    @Override
    public int getCount() {
        return 4;
    }

}
