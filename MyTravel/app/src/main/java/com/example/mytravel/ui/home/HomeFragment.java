package com.example.mytravel.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.city.City;
import com.example.mytravel.ui.frame.FrameActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements HomeFrMvpView, OnClickItemCity, View.OnClickListener, TextWatcher {
    public static final String TAG = HomeFragment.class.getSimpleName();
    private HomeFrMvpPresenter presenter;

    @BindView(R.id.rcvListCity)
    RecyclerView rcvListCity;
    @BindView(R.id.etSearch)
    EditText etSearch;

    private ListCityAdapter listCityAdapter;
    private ImageView ivSearch;
    private List<City> listCities = new ArrayList<>();

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
            ivSearch = getActivity().findViewById(R.id.ivSearch);
            ivSearch.setVisibility(View.VISIBLE);
            ivSearch.setOnClickListener(this);
        }
        listCityAdapter.setOnClickItemCity(this);
        listCityAdapter.setListCities((ArrayList<City>) listCities);
        rcvListCity.setHasFixedSize(true);
        rcvListCity.setAdapter(listCityAdapter);
        rcvListCity.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && etSearch.getVisibility() == View.VISIBLE) {
                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_to_top);
                    etSearch.setText("");
                    etSearch.setVisibility(View.GONE);
                    etSearch.startAnimation(animation);
                }
            }
        });
        presenter.getListCity();
        presenter.getListBookTour();

        etSearch.addTextChangedListener(this);
    }


    @Override
    public void successGetListCity(ArrayList<City> listCities) {
        if (listCities != null) {
            this.listCities = listCities;
            listCityAdapter.replaceAllData(listCities);
        }
    }

    @Override
    public void onClickDetail(City city) {
        startActivity(FrameActivity.newIntentDetailCity(getContext(), city));
    }

    @Override
    public void onClick(View v) {
        //onClickSearch
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.top_to_bottom);
        etSearch.setVisibility(View.VISIBLE);
        etSearch.startAnimation(animation);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        List<City> listFilters = new ArrayList<>();
        if (!TextUtils.isEmpty(s)) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                listFilters = listCities.stream()
                        .filter(city -> city.getNameCity().matches(".*" + s + ".*"))
                        .collect(Collectors.toList());
            } else {
                for (City item : this.listCities) {
                    if (item.getNameCity().matches(".*" + s + ".*")) {
                        listCities.add(item);
                    }
                }
            }
        } else {
            listFilters = this.listCities;
        }

        if (listFilters.size() != 0) {
            listCityAdapter.replaceAllData((ArrayList<City>) listFilters);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
