package com.example.mytravel.ui.listtour.lowprice;

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
import com.example.mytravel.utils.OnClickItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_CITY;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_EXPLORE;

public class LowPriceTourFragment extends BaseFragment implements LowPriceTourFrMvpView, OnClickItem<TourPopular> {
    public static final String TAG = LowPriceTourFragment.class.getSimpleName();

    private LowPriceTourFrMvpPresenter presenter;

    @BindView(R.id.rcvLowPriceListTour)
    RecyclerView rcvListTour;

    private String idCity;
    private String idExplore;
    private ItemTourAdapter itemTourAdapter;

    public static LowPriceTourFragment newInstance( String idCity, String idExplore) {
        LowPriceTourFragment lowPriceTourFragment = new LowPriceTourFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE_ID_CITY, idCity);
        bundle.putString(KEY_TYPE_ID_EXPLORE, idExplore);
        lowPriceTourFragment.setArguments(bundle);
        return lowPriceTourFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LowPriceTourFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_low_price_tour, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
            View viewGui = getActivity().findViewById(R.id.viewActionBar);
            viewGui.setVisibility(View.GONE);
        }
        if (getArguments() != null) {
            idCity = getArguments().getString(KEY_TYPE_ID_CITY);
            idExplore = getArguments().getString(KEY_TYPE_ID_EXPLORE);
        }


        itemTourAdapter = new ItemTourAdapter(getContext());
        itemTourAdapter.setPopularOnClickItem(this);
        ArrayList<TourPopular> listTour = new ArrayList<>();
        itemTourAdapter.setListTours(listTour);
        rcvListTour.setHasFixedSize(true);
        rcvListTour.setAdapter(itemTourAdapter);

        presenter.getListLowPriceTour(idCity, idExplore);

    }

    @Override
    public void successListTour(ArrayList<TourPopular> listTourNew) {
        if (listTourNew != null) {
            itemTourAdapter.replaceAllData(listTourNew);
        }
    }

    @Override
    public void onClickItem(TourPopular tourPopular) {
        startActivity(FrameActivity.newIntentDetailTour(getContext(), tourPopular));
    }
}
