package com.example.mytravel.ui.history;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.booktour.BookTour;
import com.example.mytravel.ui.frame.FrameActivity;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryFragment extends BaseFragment implements HistoryFrMvpView, OnClickItemHistory, View.OnClickListener {
    public static final String TAG = HistoryFragment.class.getSimpleName();

    private HistoryFrMvpPresenter presenter;

    @BindView(R.id.rcvListHistory)
    RecyclerView rcvListHistory;

    private HistoryAdapter historyAdapter;
    private ArrayList<BookTour> listBookTours = new ArrayList<>();
    private AlertDialog dialogRemove;

    public static HistoryFragment newInstance() {
        HistoryFragment historyFragment = new HistoryFragment();
        return historyFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HistoryFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
            getActivity().findViewById(R.id.ivBack).setOnClickListener(this);
        }

        listBookTours = getAppDataManager().getListBookTour();
        if (listBookTours != null) {
            historyAdapter = new HistoryAdapter(getContext());
            historyAdapter.setListBookTours(listBookTours);
            historyAdapter.setOnClickItemHistory(this);
            rcvListHistory.setHasFixedSize(true);
            rcvListHistory.setAdapter(historyAdapter);
        }

    }

    @Override
    public void onClickItemHistory(BookTour bookTour) {
        startActivity(FrameActivity.newIntentPayment(getContext(), bookTour, "", "", true));
    }

    @Override
    public void onClickDeleteHistory(BookTour bookTour) {
        if (getActivity() != null && getContext() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View mView = getLayoutInflater().inflate(R.layout.dialog_remove_history, null);
            builder.setView(mView);
            dialogRemove = builder.create();
            Objects.requireNonNull(dialogRemove.getWindow()).setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));

            mView.findViewById(R.id.btnYes).setOnClickListener(v -> {
                if (getActivity() != null) {
                    presenter.removeHistory(bookTour);
                }
            });
            mView.findViewById(R.id.btnNo).setOnClickListener(v -> dialogRemove.dismiss());

            dialogRemove.setCancelable(true);
            dialogRemove.show();
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

    @Override
    public void onDetach() {
        super.onDetach();
        if (dialogRemove != null) {
            dialogRemove.dismiss();
        }
    }

    @Override
    public void succcessRemove(BookTour bookTour) {
        listBookTours.remove(bookTour);
        historyAdapter.notifyDataSetChanged();
        if (dialogRemove != null) {
            dialogRemove.dismiss();
        }
    }
}
