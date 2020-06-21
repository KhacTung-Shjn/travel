package com.example.mytravel.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.city.City;
import com.example.mytravel.ui.frame.FrameActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements HomeFrMvpView, OnClickItemCity {
    public static final String TAG = HomeFragment.class.getSimpleName();
    private HomeFrMvpPresenter presenter;

    @BindView(R.id.rcvListCity)
    RecyclerView rcvListCity;

    private ListCityAdapter listCityAdapter;

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomeFrPresenter(this);
        listCityAdapter = new ListCityAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }
        listCityAdapter.setOnClickItemCity(this);
        ArrayList<City> listCities = new ArrayList<>();
        listCityAdapter.setListCities(listCities);
        rcvListCity.setHasFixedSize(true);
        rcvListCity.setAdapter(listCityAdapter);

        presenter.getListCity();

    }


    @Override
    public void successGetListCity(ArrayList<City> listCities) {
        if (listCities != null) {
            listCityAdapter.replaceAllData(listCities);
        }
    }

    @Override
    public void onClickDetail(City city) {
        startActivity(FrameActivity.newIntentDetailCity(getContext(), city));
    }
}
