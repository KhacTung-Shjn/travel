package com.example.mytravel.ui.favorite.place;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.city.Place;
import com.example.mytravel.ui.detailexplore.placelist.OnClickItemPlace;
import com.example.mytravel.ui.detailexplore.placelist.PlaceListAdapter;
import com.example.mytravel.ui.frame.FrameActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mytravel.utils.ConstApp.KEY_LIST_PLACE;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_CITY;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_EXPLORE;

public class PlaceFragment extends BaseFragment implements PlaceFrMvpView, OnClickItemPlace {
    public static final String TAG = PlaceFragment.class.getSimpleName();

    private PlaceFrMvpPresenter presenter;

    @BindView(R.id.rcvListFavoritesPlace)
    RecyclerView rcvListFavoritesPlace;

    private ArrayList<Place> listPlaces = new ArrayList<>();
    private PlaceListAdapter placeListAdapter;
    private String idCity, idExplore;

    public static PlaceFragment newInstance() {
        PlaceFragment placeFragment = new PlaceFragment();
        return placeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PlaceFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }

        rcvListFavoritesPlace.setHasFixedSize(true);
        placeListAdapter = new PlaceListAdapter(getContext());
        placeListAdapter.setListPlaces(listPlaces);
        placeListAdapter.setPlaceOnClickItem(this);
        rcvListFavoritesPlace.setAdapter(placeListAdapter);

        presenter.getListFavoritesPlace();
    }

    @Override
    public void successGetListFavoritesPlace(ArrayList<Place> listPlaces, String idCity, String idExplore) {
        if (listPlaces != null && idCity != null && idExplore != null) {
            this.idCity = idCity;
            this.idExplore = idExplore;
            this.listPlaces = listPlaces;
            placeListAdapter.replaceAllData(this.listPlaces);
        }
    }

    @Override
    public void onClickItem(Place place) {
        startActivity(FrameActivity.newIntentPlaceHot(getContext(), idCity, idExplore, place.getIdPlace()));
    }

    @Override
    public void onClickIsLove(String idPlace, boolean isLove) {
        if (isLove) {
            presenter.setLovePlace(idCity, idExplore, idPlace);
        } else {
            presenter.removeLovePlace(idPlace);
        }
    }
}
