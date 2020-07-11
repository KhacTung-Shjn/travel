package com.example.mytravel.ui.listtour;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mytravel.ui.listtour.goodprice.GoodPriceTourFragment;
import com.example.mytravel.ui.listtour.highprice.HighPriceTourFragment;
import com.example.mytravel.ui.listtour.lowprice.LowPriceTourFragment;
import com.example.mytravel.ui.listtour.newtour.NewTourFragment;

import java.util.Objects;


public class ListTourViewPager extends FragmentStatePagerAdapter {

    private String idCity;
    private String idExplore;

    public ListTourViewPager(@NonNull FragmentManager fm, int behavior, String idCity, String idExplore) {
        super(fm, behavior);
        this.idCity = idCity;
        this.idExplore = idExplore;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0: {
                fragment = NewTourFragment.newInstance(idCity, idExplore);
                break;
            }
            case 1: {
                fragment = GoodPriceTourFragment.newInstance(idCity, idExplore);
                break;
            }
            case 2: {
                fragment = HighPriceTourFragment.newInstance(idCity, idExplore);
                break;
            }
            case 3: {
                fragment = LowPriceTourFragment.newInstance(idCity, idExplore);
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
