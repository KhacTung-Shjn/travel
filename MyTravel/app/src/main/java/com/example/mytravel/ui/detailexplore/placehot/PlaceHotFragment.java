package com.example.mytravel.ui.detailexplore.placehot;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.city.Place;
import com.example.mytravel.models.city.PlaceHot;
import com.example.mytravel.ui.frame.FrameActivity;
import com.example.mytravel.utils.ScrollingPagerIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_CITY;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_EXPLORE;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_PLACE;

public class PlaceHotFragment extends BaseFragment implements PlaceHotFrMvpView, OnClickMap, View.OnClickListener {
    public static final String TAG = PlaceHotFragment.class.getSimpleName();

    private PlaceHotFrMvpPresenter presenter;

    @BindView(R.id.rcvListHotPlace)
    RecyclerView rcvListHotPlace;
    @BindView(R.id.indicator)
    ScrollingPagerIndicator indicator;

    private ArrayList<PlaceHot> listPlaceHots = new ArrayList<>();
    private PlaceHotAdapter placeHotAdapter;
    private String idCity;
    private String idExplore;
    private String idPlace;

    public static PlaceHotFragment newInstance(String idCity, String idExplore, String idPlace) {
        PlaceHotFragment placeHotFragment = new PlaceHotFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE_ID_CITY, idCity);
        bundle.putString(KEY_TYPE_ID_EXPLORE, idExplore);
        bundle.putString(KEY_TYPE_ID_PLACE, idPlace);
        placeHotFragment.setArguments(bundle);
        return placeHotFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PlaceHotFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place_hot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
            getActivity().findViewById(R.id.ivBack).setOnClickListener(this);
        }
        if (getArguments() != null) {
            idCity = getArguments().getString(KEY_TYPE_ID_CITY);
            idExplore = getArguments().getString(KEY_TYPE_ID_EXPLORE);
            idPlace = getArguments().getString(KEY_TYPE_ID_PLACE);
        }

        rcvListHotPlace.setHasFixedSize(true);
        placeHotAdapter = new PlaceHotAdapter(getContext());
        placeHotAdapter.setListPlaceHots(listPlaceHots);
        placeHotAdapter.setOnClickMap(this);
        rcvListHotPlace.setAdapter(placeHotAdapter);
        indicator.attachToRecyclerView(rcvListHotPlace);

        if (!TextUtils.isEmpty(idCity) && !TextUtils.isEmpty(idExplore) && !TextUtils.isEmpty(idPlace)) {
            presenter.getListPlaceHot(idCity, idExplore, idPlace);
        }

    }

    @Override
    public void onClickOpenMap(String lat, String lng, PlaceHot placeHot) {
        startActivity(FrameActivity.newIntentMapPlaceHot(getContext(), lat, lng, placeHot));
    }

    @Override
    public void failOpenMap(String msgError) {
        showMessage(msgError);
    }

    @Override
    public void successGetListPlaceHot(ArrayList<PlaceHot> listPlaceHots) {
        if (listPlaceHots != null) {
            placeHotAdapter.replaceAll(listPlaceHots);
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
