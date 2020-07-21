package com.example.mytravel.ui.favorite.explores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.city.Explore;
import com.example.mytravel.models.city.TourPopular;
import com.example.mytravel.ui.detailcity.ExploreAdapter;
import com.example.mytravel.ui.detailcity.OnClickItemInCity;
import com.example.mytravel.ui.frame.FrameActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExploreFragment extends BaseFragment implements ExploreFrMvpView, OnClickItemInCity {
    public static final String TAG = ExploreFragment.class.getSimpleName();

    private ExploreFrMvpPresenter presenter;

    @BindView(R.id.rcvFavoritesExplore)
    RecyclerView rcvFavoritesExplore;

    private ExploreAdapter exploreAdapter;
    private String idCity;
    private String idExplore;

    public static ExploreFragment newInstance() {
        ExploreFragment exploreFragment = new ExploreFragment();
        return exploreFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ExploreFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }


        rcvFavoritesExplore.setHasFixedSize(true);
        rcvFavoritesExplore.setLayoutManager(new GridLayoutManager(getContext(), 2));
        exploreAdapter = new ExploreAdapter(getContext());
        exploreAdapter.setListExplores(new ArrayList<>());
        exploreAdapter.setOnClickItemInCity(this);
        rcvFavoritesExplore.setAdapter(exploreAdapter);

        presenter.getListFavoritesExplore();
    }

    @Override
    public void onClickTour(TourPopular tourPopular) {

    }

    @Override
    public void onClickExplore(Explore explore) {
        startActivity(FrameActivity.newIntentDetailExplore(getContext(), explore, idCity));
    }

    @Override
    public void onClickIsLoveExplore(String IdExplore, boolean isLove) {
        if (isLove) {
            presenter.setLoveExplore(idCity, idExplore);
        } else {
            presenter.removeLoveExplore(idExplore);
        }
    }

    @Override
    public void successGetListFavoritesExplore(ArrayList<Explore> listExplore, String idCity, String idExplore) {
        if (listExplore != null && idCity != null && idExplore != null) {
            exploreAdapter.replaceAllData(listExplore);
            this.idCity = idCity;
            this.idExplore = idExplore;
        }
    }
}
