package com.example.mytravel.ui.detailtour;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.city.TourPopular;
import com.example.mytravel.ui.frame.FrameActivity;
import com.example.mytravel.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mytravel.utils.ConstApp.KEY_ITEM_TOUR;

public class DetailTourFragment extends BaseFragment implements DetailTourFrMvpView, View.OnClickListener {
    public static final String TAG = DetailTourFragment.class.getSimpleName();

    private DetailTourFrMvpPresenter presenter;

    @BindView(R.id.ivTour)
    ImageView ivTour;
    @BindView(R.id.isLove)
    TextView tvLove;
    @BindView(R.id.tvNameTour)
    TextView tvNameTour;
    @BindView(R.id.tvRateTour)
    TextView tvRateTour;
    @BindView(R.id.cvLocation)
    CardView cvLocation;
    @BindView(R.id.cvCalender)
    CardView cvCalender;
    @BindView(R.id.cvMoney)
    CardView cvMoney;
    @BindView(R.id.tvPlace)
    TextView tvPlace;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvMoney)
    TextView tvMoney;
    @BindView(R.id.tvContentIntroduction)
    TextView tvContentIntroduction;
    @BindView(R.id.tvContentSchedule)
    TextView tvContentSchedule;
    @BindView(R.id.tvContentTraffic)
    TextView tvContentTraffic;
    @BindView(R.id.btnRateTour)
    Button btnRateTour;
    @BindView(R.id.btnBookTour)
    Button btnBookTour;

    private TourPopular tourPopular;

    public static DetailTourFragment newInstance(TourPopular tourPopular) {
        DetailTourFragment detailTourFragment = new DetailTourFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_ITEM_TOUR, tourPopular);
        detailTourFragment.setArguments(bundle);
        return detailTourFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DetailTourFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_tour, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
            ImageView ivBack = getActivity().findViewById(R.id.ivBack);
            ivBack.setOnClickListener(this);
        }
        if (getArguments() != null) {
            tourPopular = getArguments().getParcelable(KEY_ITEM_TOUR);
        }
        if (tourPopular != null) {
            getTourDetail(tourPopular);
        }
    }


    @SuppressLint("SetTextI18n")
    private void getTourDetail(TourPopular tour) {
        if (!TextUtils.isEmpty(tour.getUrlImage())) {
            CommonUtils.loadImage(getContext(), tour.getUrlImage(), ivTour);
        }
        if (!TextUtils.isEmpty(tour.getNameTour())) {
            tvNameTour.setText(tour.getNameTour());
        }
        if (tour.getRate() != -1) {
            tvRateTour.setText(String.valueOf(tour.getRate()));
        }
        tvLove.setSelected(tour.isLove());

        if (!TextUtils.isEmpty(tour.getTime())) {
            tvDate.setText(tour.getTime());
        }
        if (tour.getMoney() != -1) {
            tvMoney.setText(tour.getMoney() + " $ ");
        }
        if (!TextUtils.isEmpty(tour.getIntroduction())) {
            tvContentIntroduction.setText(tour.getIntroduction());
        }
        if (!TextUtils.isEmpty(tour.getSchedule())) {
            tvContentSchedule.setText(tour.getSchedule());
        }
        if (!TextUtils.isEmpty(tour.getTraffic())) {
            tvContentTraffic.setText(tour.getTraffic());
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

    @OnClick(R.id.btnBookTour)
    public void onClickBookTour() {
        startActivity(FrameActivity.newIntentBookTour(getContext(), tvNameTour.getText().toString()));
    }
}
