package com.example.mytravel.ui.detailexplore.placelist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseBottomSheetDialogFragment;
import com.example.mytravel.models.city.Place;
import com.example.mytravel.utils.OnClickItem;

import java.util.ArrayList;

import static com.example.mytravel.utils.ConstApp.KEY_LIST_PLACE;

public class PlaceListBSFragment extends BaseBottomSheetDialogFragment implements PlaceListBSFrMvpView, OnClickItem<Place>, View.OnTouchListener {
    public static final String TAG = PlaceListBSFragment.class.getSimpleName();

    private PlaceListBSFrMvpPresenter presenter;

    TextView tvNumberPlace;
    RecyclerView rcvListPlace;

    private ArrayList<Place> listPlaces = new ArrayList<>();
    private PlaceListAdapter placeListAdapter;

    public static PlaceListBSFragment newInstance(ArrayList<Place> listPlaces) {
        PlaceListBSFragment placeListBSFragment = new PlaceListBSFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_LIST_PLACE, listPlaces);
        placeListBSFragment.setArguments(bundle);
        return placeListBSFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PlaceListBSFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet_place, container, false);
    }

    @SuppressLint({"DefaultLocale", "ClickableViewAccessibility"})
    @Override
    protected void setUp(View view) {
        tvNumberPlace = view.findViewById(R.id.tvNumberPlace);
        rcvListPlace = view.findViewById(R.id.rcvListPlace);

        if (getArguments() != null) {
            listPlaces = getArguments().getParcelableArrayList(KEY_LIST_PLACE);
        }
        if (listPlaces != null) {
            tvNumberPlace.setText(String.format("%d %s", this.listPlaces.size(), getString(R.string.text_places)));
        }
        rcvListPlace.setHasFixedSize(true);
        rcvListPlace.setOnTouchListener(this);
        placeListAdapter = new PlaceListAdapter(getContext());
        placeListAdapter.setListPlaces(listPlaces);
        placeListAdapter.setPlaceOnClickItem(this);
        rcvListPlace.setAdapter(placeListAdapter);
    }

    @Override
    public void onClickItem(Place place) {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.getParent().requestDisallowInterceptTouchEvent(true);
        v.onTouchEvent(event);
        return true;
    }
}
