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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mytravel.utils.ConstApp.KEY_NAME_TOUR;

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
    private long miniSecondCheckIn, miniSecondCheckOut, miniSecondCurrent;

    public static BookTourFragment newInstance(String nameTour) {
        BookTourFragment bookTourFragment = new BookTourFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_NAME_TOUR, nameTour);
        bookTourFragment.setArguments(bundle);
        return bookTourFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BookTourFrPresenter(this);
        calendar = Calendar.getInstance();
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
            String nameTour = getArguments().getString(KEY_NAME_TOUR);
            if (!TextUtils.isEmpty(nameTour)) {
                tvTitle.setText(nameTour);
            }
        }
        tvChooseCheckIn.setOnClickListener(this);
        tvChooseCheckOut.setOnClickListener(this);
        btnDownAdults.setOnClickListener(this);
        btnUpAdults.setOnClickListener(this);
        btnDownChildren.setOnClickListener(this);
        btnUpChildren.setOnClickListener(this);
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
                    tvChooseCheckIn.setText(String.format("%d/%d/%d", dayOfMonth, (month + 1), year));
                    Toast.makeText(getContext(), String.valueOf(miniSecondCheckIn), Toast.LENGTH_SHORT).show();
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            }
            case R.id.tvChooseCheckOut: {
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                @SuppressLint("DefaultLocale") DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                    tvChooseCheckOut.setText(String.format("%d/%d/%d", dayOfMonth, (month + 1), year));
                    Toast.makeText(getContext(), String.valueOf(miniSecondCheckIn), Toast.LENGTH_SHORT).show();
                }, mYear, mMonth, mDay);
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
        }
    }
}
