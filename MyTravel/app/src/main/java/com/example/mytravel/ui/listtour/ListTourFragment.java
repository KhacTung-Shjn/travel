package com.example.mytravel.ui.listtour;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_CHECK_TOUR;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_CITY;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_EXPLORE;

public class ListTourFragment extends BaseFragment implements ListTourFrMvpView, ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    public static final String TAG = ListTourFragment.class.getSimpleName();

    private ListTourFrMvpPresenter presenter;

    @BindView(R.id.rgTour)
    RadioGroup rgTour;
    @BindView(R.id.rbNew)
    RadioButton rbNew;
    @BindView(R.id.rbGoodPrices)
    RadioButton rbGoodPrices;
    @BindView(R.id.rbHighPrices)
    RadioButton rbHighPrices;
    @BindView(R.id.rbLowPrices)
    RadioButton rbLowPrices;
    @BindView(R.id.vpTours)
    ViewPager vpTours;

    private ListTourViewPager listTourViewPager;
    private String idCity;
    private String idExplore;

    public static ListTourFragment newInstance(String idCity, String idExplore) {
        ListTourFragment listTourFragment = new ListTourFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE_ID_CITY, idCity);
        bundle.putString(KEY_TYPE_ID_EXPLORE, idExplore);
        listTourFragment.setArguments(bundle);
        return listTourFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ListTourFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_tour, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
            ImageView ivBack = getActivity().findViewById(R.id.ivBack);
            ivBack.setOnClickListener(this);
        }
        if (getArguments() != null) {
            idCity = getArguments().getString(KEY_TYPE_ID_CITY);
            idExplore = getArguments().getString(KEY_TYPE_ID_EXPLORE);
        }
        if (getFragmentManager() != null) {
            listTourViewPager = new ListTourViewPager(getFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, idCity, idExplore);
            vpTours.setAdapter(listTourViewPager);
            vpTours.addOnPageChangeListener(this);
        }
        rgTour.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        rgTour.check(rgTour.getChildAt(position).getId());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (vpTours != null) {
            switch (checkedId) {
                case R.id.rbNew: {
                    vpTours.setCurrentItem(0);
                    break;
                }
                case R.id.rbGoodPrices: {
                    vpTours.setCurrentItem(1);
                    break;
                }
                case R.id.rbHighPrices: {
                    vpTours.setCurrentItem(2);
                    break;
                }
                case R.id.rbLowPrices: {
                    vpTours.setCurrentItem(3);
                    break;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (getActivity() != null && getFragmentManager() != null) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();
            } else {
                getActivity().finish();
            }
        }
    }

}
