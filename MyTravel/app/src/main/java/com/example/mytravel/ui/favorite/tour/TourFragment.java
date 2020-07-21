package com.example.mytravel.ui.favorite.tour;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.city.TourPopular;
import com.example.mytravel.ui.frame.FrameActivity;
import com.example.mytravel.ui.listtour.ItemTourAdapter;
import com.example.mytravel.ui.listtour.OnClickItemTour;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TourFragment extends BaseFragment implements TourFrMvpView, OnClickItemTour {
    public static final String TAG = TourFragment.class.getSimpleName();

    private TourFrMvpPresenter presenter;

    @BindView(R.id.rcvListFavoritesTour)
    RecyclerView rcvListFavoritesTour;

    private String idCity;
    private String idExplore;
    private ItemTourAdapter itemTourAdapter;

    public static TourFragment newInstance() {
        TourFragment tourFragment = new TourFragment();
        return tourFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TourFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tour, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }

        itemTourAdapter = new ItemTourAdapter(getContext());
        itemTourAdapter.setPopularOnClickItem(this);
        ArrayList<TourPopular> listTour = new ArrayList<>();
        itemTourAdapter.setListTours(listTour);
        rcvListFavoritesTour.setHasFixedSize(true);
        rcvListFavoritesTour.setAdapter(itemTourAdapter);

        presenter.getListFavoritesTour();
    }

    @Override
    public void onClickItem(TourPopular tourPopular) {
        startActivity(FrameActivity.newIntentDetailTour(getContext(), tourPopular));
    }

    @Override
    public void onSetIsLove(String idTour, boolean isLove) {
        if(isLove){
            presenter.setLoveTour(idCity,idExplore,idTour);
        }else{
            presenter.removeLoveTour(idTour);
        }
    }

    @Override
    public void successGetListFavoritesTour(ArrayList<TourPopular> listTours, String idCity, String idExplore) {
        if (listTours != null && idCity != null && idExplore != null) {
            itemTourAdapter.replaceAllData(listTours);
        }
    }
}
