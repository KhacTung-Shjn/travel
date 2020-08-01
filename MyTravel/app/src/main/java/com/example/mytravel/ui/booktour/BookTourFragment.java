package com.example.mytravel.ui.booktour;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.booktour.BookTour;
import com.example.mytravel.models.city.TourPopular;
import com.example.mytravel.ui.frame.FrameActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mytravel.utils.ConstApp.KEY_ITEM_TOUR;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_CITY;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_EXPLORE;

public class BookTourFragment extends BaseFragment implements BookTourFrMvpView, View.OnClickListener {
    public static final String TAG = BookTourFragment.class.getSimpleName();

    private BookTourFrMvpPresenter presenter;

    @BindView(R.id.tvChooseCheckIn)
    TextView tvChooseCheckIn;
    @BindView(R.id.tvChooseCheckOut)
    TextView tvChooseCheckOut;
    @BindView(R.id.btnDownAdults)
    ImageView btnDownAdults;
    @BindView(R.id.tvNumberAdults)
    TextView tvNumberAdults;
    @BindView(R.id.btnUpAdults)
    ImageView btnUpAdults;
    @BindView(R.id.btnDownChildren)
    ImageView btnDownChildren;
    @BindView(R.id.tvNumberChildren)
    TextView tvNumberChildren;
    @BindView(R.id.btnUpChildren)
    ImageView btnUpChildren;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.btnRequestBookTour)
    Button btnRequestBookTour;

    private TextView tvTitle;
    private Calendar calendar;
    private Context context;
    private TourPopular tour;
    private long miniSecondCheckIn, miniSecondCheckOut, miniSecondCurrent;
    private String idCity, idExplore;

    public static BookTourFragment newInstance(TourPopular tour, String idCity, String idExplore) {
        BookTourFragment bookTourFragment = new BookTourFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_ITEM_TOUR, tour);
        bundle.putString(KEY_TYPE_ID_CITY, idCity);
        bundle.putString(KEY_TYPE_ID_EXPLORE, idExplore);
        bookTourFragment.setArguments(bundle);
        return bookTourFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BookTourFrPresenter(this);
        calendar = Calendar.getInstance();
        miniSecondCurrent = calendar.getTimeInMillis();
        this.context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booktour, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
            tvTitle = getActivity().findViewById(R.id.tvTitleFrame);
            ImageView ivBack = getActivity().findViewById(R.id.ivBack);
            ivBack.setOnClickListener(this);
        }
        if (getArguments() != null) {
            tour = getArguments().getParcelable(KEY_ITEM_TOUR);
            idCity = getArguments().getString(KEY_TYPE_ID_CITY);
            idExplore = getArguments().getString(KEY_TYPE_ID_EXPLORE);

            if (tour != null && !TextUtils.isEmpty(tour.getNameTour())) {
                tvTitle.setText(tour.getNameTour());
            }
            if (MainApp.getInstance().getPresenter().getUserInformation().getName() != null) {
                etName.setText(MainApp.getInstance().getPresenter().getUserInformation().getName());
            }
            if (MainApp.getInstance().getPresenter().getUserInformation().getEmail() != null) {
                etEmail.setText(MainApp.getInstance().getPresenter().getUserInformation().getEmail());
            }
            if (MainApp.getInstance().getPresenter().getUserInformation().getPhone() != null) {
                etPhone.setText(MainApp.getInstance().getPresenter().getUserInformation().getPhone());
            }

        }
        tvChooseCheckIn.setOnClickListener(this);
        tvChooseCheckOut.setOnClickListener(this);
        btnDownAdults.setOnClickListener(this);
        btnUpAdults.setOnClickListener(this);
        btnDownChildren.setOnClickListener(this);
        btnUpChildren.setOnClickListener(this);
        btnRequestBookTour.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int mYear;
        int mMonth;
        int mDay;
        switch (v.getId()) {
            case R.id.ivBack: {
                if (getActivity() != null && getFragmentManager() != null) {
                    if (getFragmentManager().getBackStackEntryCount() > 0) {
                        getFragmentManager().popBackStackImmediate();
                    } else {
                        getActivity().finish();
                    }
                }
                break;
            }
            case R.id.tvChooseCheckIn: {
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                @SuppressLint("DefaultLocale") DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth, 0, 0, 0);
                    if (miniSecondCheckOut > 0 && miniSecondCheckOut < calendar.getTimeInMillis())
                        return;
                    miniSecondCheckIn = calendar.getTimeInMillis();
                    tvChooseCheckIn.setText(String.format("%d/%d/%d", dayOfMonth, (month + 1), year));
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(miniSecondCurrent);
                if (miniSecondCheckOut > 0) {
                    datePickerDialog.getDatePicker().setMaxDate(miniSecondCheckOut);
                }
                datePickerDialog.show();
                break;
            }
            case R.id.tvChooseCheckOut: {
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                @SuppressLint("DefaultLocale") DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth, 23, 59, 59);
                    if (miniSecondCheckIn > 0 && miniSecondCheckIn > calendar.getTimeInMillis())
                        return;
                    miniSecondCheckOut = calendar.getTimeInMillis();
                    tvChooseCheckOut.setText(String.format("%d/%d/%d", dayOfMonth, (month + 1), year));
                }, mYear, mMonth, mDay);

                if (miniSecondCheckIn > 0) {
                    datePickerDialog.getDatePicker().setMinDate(miniSecondCheckIn);
                } else {
                    datePickerDialog.getDatePicker().setMinDate(miniSecondCurrent);
                }
                datePickerDialog.show();
                break;
            }
            case R.id.btnDownAdults: {
                int numberAdults = Integer.parseInt(tvNumberAdults.getText().toString());
                if (numberAdults == 0) return;
                tvNumberAdults.setText(String.valueOf(--numberAdults));
                break;
            }
            case R.id.btnUpAdults: {
                int numberAdults = Integer.parseInt(tvNumberAdults.getText().toString());
                tvNumberAdults.setText(String.valueOf(++numberAdults));
                break;
            }
            case R.id.btnDownChildren: {
                int numberAdults = Integer.parseInt(tvNumberChildren.getText().toString());
                if (numberAdults == 0) return;
                tvNumberChildren.setText(String.valueOf(--numberAdults));
                break;
            }
            case R.id.btnUpChildren: {
                int numberAdults = Integer.parseInt(tvNumberChildren.getText().toString());
                tvNumberChildren.setText(String.valueOf(++numberAdults));
                break;
            }
            case R.id.btnRequestBookTour: {
                presenter.requestBookTour(tvChooseCheckIn.getText().toString(),
                        tvChooseCheckOut.getText().toString(),
                        tvNumberAdults.getText().toString(),
                        tvNumberChildren.getText().toString(),
                        etName.getText().toString(),
                        etEmail.getText().toString(), etPhone.getText().toString(), miniSecondCheckIn, miniSecondCheckOut);
                break;
            }
        }
    }

    @Override
    public void successRequestBookTour(BookTour bookTour) {
        if (bookTour != null) {
            bookTour.setId(tour.getId());
            bookTour.setNameTour(tour.getNameTour());
            bookTour.setUrlImageTour(tour.getUrlImage());
            bookTour.setPriceTour(String.valueOf(tour.getMoney()));
            startActivity(FrameActivity.newIntentPayment(getContext(), bookTour, idCity, idExplore, false));
        }
    }
}
