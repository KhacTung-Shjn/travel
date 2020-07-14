package com.example.mytravel.ui.detailexplore;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.city.Explore;
import com.example.mytravel.models.city.Place;
import com.example.mytravel.ui.detailexplore.placelist.PlaceListBSFragment;
import com.example.mytravel.ui.frame.FrameActivity;
import com.example.mytravel.utils.AddData;
import com.example.mytravel.utils.CommonUtils;
import com.example.mytravel.utils.ViewDialog;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mytravel.utils.ConstApp.KEY_ITEM_EXPLORE;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_CITY;

public class DetailExploreFragment extends BaseFragment implements DetailExploreFrMvpView, View.OnClickListener {
    public static final String TAG = DetailExploreFragment.class.getSimpleName();

    private DetailExploreFrMvpPresenter presenter;

    @BindView(R.id.ivExploreDetail)
    ImageView ivExploreDetail;
    @BindView(R.id.btnBackExplore)
    ImageView btnBackExplore;
    @BindView(R.id.tvTitleExplore)
    TextView tvTitleExplore;
    @BindView(R.id.cbLove)
    CheckBox cbLove;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvMap)
    TextView tvMap;
    @BindView(R.id.tvPlace)
    TextView tvPlace;
    @BindView(R.id.tvRatePlace)
    TextView tvRatePlace;
    @BindView(R.id.ivDetail1)
    ImageView ivDetail1;
    @BindView(R.id.ivDetail2)
    ImageView ivDetail2;
    @BindView(R.id.ivDetail3)
    ImageView ivDetail3;
    @BindView(R.id.btnFeature)
    Button btnFeature;
    @BindView(R.id.btnBookTour)
    Button btnBookTour;

    private Explore explore;
    private AlertDialog ratingExploreDialog;
    private String idCity;
    private ArrayList<Place> listPlaces;

    public static DetailExploreFragment newInstance(Explore explore, String idCity) {
        DetailExploreFragment detailExploreFragment = new DetailExploreFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_ITEM_EXPLORE, explore);
        bundle.putString(KEY_TYPE_ID_CITY, idCity);
        detailExploreFragment.setArguments(bundle);
        return detailExploreFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DetailExploreFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));

            Window w = getActivity().getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            setUnbinder(ButterKnife.bind(this, getActivity()));

            RelativeLayout rlActionBar = getActivity().findViewById(R.id.rlActionBar);
            View viewActionBar = getActivity().findViewById(R.id.viewActionBar);
            if (rlActionBar != null) {
                rlActionBar.setVisibility(View.GONE);
                viewActionBar.setVisibility(View.GONE);
            }
        }
        if (getArguments() != null) {
            explore = getArguments().getParcelable(KEY_ITEM_EXPLORE);
            idCity = getArguments().getString(KEY_TYPE_ID_CITY);
        }
        if (explore != null) {
            presenter.getListPlaces(idCity, explore.getId(), explore.getNameExplore());
            cbLove.setChecked(explore.isLove());

            if (!TextUtils.isEmpty(explore.getUrlImage())) {
                CommonUtils.loadImage(getContext(), explore.getUrlImage(), ivExploreDetail);
            }
            if (!TextUtils.isEmpty(explore.getDesc())) {
                tvDescription.setText(explore.getDesc());
            }
            if (explore.getHotDestinations() != null) {
                if (!TextUtils.isEmpty(explore.getHotDestinations().get(0)) && explore.getHotDestinations().get(0) != null) {
                    CommonUtils.loadImage(getContext(), explore.getHotDestinations().get(0), ivDetail1);
                }
                if (!TextUtils.isEmpty(explore.getHotDestinations().get(1)) && explore.getHotDestinations().get(1) != null) {
                    CommonUtils.loadImage(getContext(), explore.getHotDestinations().get(1), ivDetail2);
                }
                if (!TextUtils.isEmpty(explore.getHotDestinations().get(2)) && explore.getHotDestinations().get(2) != null) {
                    CommonUtils.loadImage(getContext(), explore.getHotDestinations().get(2), ivDetail3);
                }
            }
            if (!TextUtils.isEmpty(explore.getNameExplore())) {
                tvTitleExplore.setText(explore.getNameExplore());
            }
        }

        cbLove.setOnClickListener(this);
    }


    @OnClick(R.id.btnBackExplore)
    public void onClickBackExplore() {
        if (getActivity() != null && getFragmentManager() != null) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();
            } else {
                getActivity().finish();
            }
        }
    }

    @OnClick(R.id.btnBookTour)
    public void onClickShowListTour() {
        startActivity(FrameActivity.newIntentListTour(getContext(), idCity, explore.getId()));
    }

    @OnClick(R.id.btnFeature)
    public void onClickMore() {
        startActivity(FrameActivity.newIntentExploreMore(getContext(), idCity, explore.getId(), explore.getNameExplore()));
    }

    @OnClick(R.id.tvPlace)
    public void onClickShowListPlace() {
        if (getFragmentManager() != null && listPlaces != null) {
            PlaceListBSFragment.newInstance(idCity, explore.getId(),listPlaces).show(getFragmentManager(), PlaceListBSFragment.TAG);
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(), String.valueOf(cbLove.isChecked()), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tvRatePlace)
    public void onClickRateExplore() {
        if (getActivity() != null && getContext() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View mView = getLayoutInflater().inflate(R.layout.dialog_rate_explore, null);
            builder.setView(mView);
            ratingExploreDialog = builder.create();
            Objects.requireNonNull(ratingExploreDialog.getWindow()).setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));

            RatingBar ratingBar = mView.findViewById(R.id.rbRating);

            //TODO RATING
            mView.findViewById(R.id.btnDoneRating).setOnClickListener(v -> Toast.makeText(getContext(), "Number Star: " + ratingBar.getRating(), Toast.LENGTH_SHORT).show());

            mView.findViewById(R.id.ivClose).setOnClickListener(v -> ratingExploreDialog.dismiss());

            ratingExploreDialog.setCancelable(true);
            ratingExploreDialog.show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (ratingExploreDialog != null) {
            ratingExploreDialog.dismiss();
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void successGetListPlace(ArrayList<Place> listPlaces) {
        if (listPlaces != null) {
            this.listPlaces = listPlaces;
            tvPlace.setText(String.format("%d %s", this.listPlaces.size(), getString(R.string.text_places)));
        }
    }
}
