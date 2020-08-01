package com.example.mytravel.ui.detailcity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.city.City;
import com.example.mytravel.models.city.Explore;
import com.example.mytravel.models.city.TourPopular;
import com.example.mytravel.ui.frame.FrameActivity;
import com.example.mytravel.utils.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mytravel.utils.ConstApp.KEY_ITEM_CITY;

public class DetailCityFragment extends BaseFragment implements DetailCityFrMvpView, OnClickItemInCity {
    public static final String TAG = DetailCityFragment.class.getSimpleName();

    private DetailCityFrMvpPresenter presenter;

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.ivCity)
    ImageView ivCity;
    @BindView(R.id.tvNameCity)
    TextView tvNameCity;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.rcvExplore)
    RecyclerView rcvExplore;
    @BindView(R.id.tvNoExplore)
    TextView tvNoExplore;
    @BindView(R.id.tvRate)
    TextView tvRate;
    @BindView(R.id.rcvListTourPopular)
    RecyclerView rcvListTourPopular;

    private City city;
    private ExploreAdapter exploreAdapter;
    private TourPopularAdapter tourPopularAdapter;

    public static DetailCityFragment newInstance(City city) {
        DetailCityFragment detailCityFragment = new DetailCityFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_ITEM_CITY, city);
        detailCityFragment.setArguments(bundle);
        return detailCityFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DetailCityFrPresenter(this);
        tourPopularAdapter = new TourPopularAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));

            Window w = getActivity().getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            RelativeLayout rlActionBar = getActivity().findViewById(R.id.rlActionBar);
            View viewActionBar = getActivity().findViewById(R.id.viewActionBar);
            if (rlActionBar != null) {
                rlActionBar.setVisibility(View.GONE);
                viewActionBar.setVisibility(View.GONE);
            }
        }
        if (getArguments() != null) {
            city = getArguments().getParcelable(KEY_ITEM_CITY);
        }

        rcvExplore.setHasFixedSize(true);
        rcvExplore.setLayoutManager(new GridLayoutManager(getContext(), 2));
        exploreAdapter = new ExploreAdapter(getContext());
        exploreAdapter.setListExplores(new ArrayList<>());
        exploreAdapter.setOnClickItemInCity(this);
        rcvExplore.setAdapter(exploreAdapter);

        rcvListTourPopular.setHasFixedSize(true);
        rcvListTourPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tourPopularAdapter.setListExplores(new ArrayList<>());
        tourPopularAdapter.setOnClickItemInCity(this);
        rcvListTourPopular.setAdapter(tourPopularAdapter);

        if (city != null) {
            tvRate.setText(String.valueOf(city.getRateCity()));
            if (!TextUtils.isEmpty(city.getImageCity())) {
                CommonUtils.loadImage(getContext(), city.getImageCity(), ivCity);
            }
            if (!TextUtils.isEmpty(city.getNameCity())) {
                tvNameCity.setText(String.format("%s %s", city.getNameCity(), getString(R.string.text_tour)));
            }
            if (!TextUtils.isEmpty(city.getDesc())) {
                tvDesc.setText(city.getDesc());
            }
            presenter.getListExplore(city.getIdCity());
            presenter.getListTourPopular(city.getIdCity());
        }


    }

    @OnClick(R.id.btnBack)
    public void onClickBack() {
        if (getActivity() != null && getFragmentManager() != null) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();
            } else {
                getActivity().finish();
            }
        }
    }

    @Override
    public void successGetListExplore(ArrayList<Explore> listExplore) {
        if (listExplore != null) {
            tvNoExplore.setVisibility(View.GONE);
            rcvExplore.setVisibility(View.VISIBLE);
            exploreAdapter.replaceAllData(listExplore);
        } else {
            tvNoExplore.setVisibility(View.VISIBLE);
            rcvExplore.setVisibility(View.GONE);
        }
    }

    @Override
    public void successGetListTourPopular(ArrayList<TourPopular> listTours) {
        if (listTours != null) {
            tourPopularAdapter.replaceAllData(listTours);
        }
    }

    @Override
    public void onClickTour(TourPopular tourPopular) {
        startActivity(FrameActivity.newIntentDetailTour(getContext(), tourPopular, "", ""));
    }

    @Override
    public void onClickExplore(Explore explore) {
        startActivity(FrameActivity.newIntentDetailExplore(getContext(), explore, city.getIdCity()));
    }

    @Override
    public void onClickIsLoveExplore(String idExplore, boolean isLove) {
        if (isLove) {
            presenter.setLoveExplore(city.getIdCity(), idExplore);
        } else {
            presenter.removeLoveExplore(idExplore);
        }
    }

}
