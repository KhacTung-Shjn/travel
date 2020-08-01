package com.example.mytravel.ui.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.models.booktour.BookTour;
import com.example.mytravel.utils.CommonUtils;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private ArrayList<BookTour> listBookTours;
    private Context context;
    private OnClickItemHistory onClickItemHistory;

    public void setOnClickItemHistory(OnClickItemHistory onClickItemHistory) {
        this.onClickItemHistory = onClickItemHistory;
    }

    public void setListBookTours(ArrayList<BookTour> listBookTours) {
        this.listBookTours = listBookTours;
        notifyDataSetChanged();
    }

    public void replaceAll(ArrayList<BookTour> listBookTours) {
        if (this.listBookTours != null) {
            this.listBookTours.clear();
            this.listBookTours.addAll(listBookTours);
        } else {
            this.listBookTours = listBookTours;
        }
        notifyDataSetChanged();
    }

    public HistoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_list_history, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        BookTour bookTour = listBookTours.get(position);
        if (bookTour != null) {
            if (!TextUtils.isEmpty(bookTour.getNameTour())) {
                holder.tvNameTour.setText(bookTour.getNameTour());
            }
            if (!TextUtils.isEmpty(bookTour.getCheckIn()) && !TextUtils.isEmpty(bookTour.getCheckOut())) {
                holder.tvDate.setText(bookTour.getCheckIn() + " - " + bookTour.getCheckOut());
            }
            if (!TextUtils.isEmpty(bookTour.getPriceTour())) {
                holder.tvPrice.setText("$" + bookTour.getPriceTour());
            }
            if (!TextUtils.isEmpty(bookTour.getUrlImageTour())) {
                CommonUtils.loadImage(context, bookTour.getUrlImageTour(), holder.ivPhoto);
            }

            holder.itemView.setOnClickListener(v -> onClickItemHistory.onClickItemHistory(bookTour));
            holder.btnDelete.setOnClickListener(v -> onClickItemHistory.onClickDeleteHistory(bookTour));

        }
    }

    @Override
    public int getItemCount() {
        return listBookTours.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvNameTour;
        TextView tvDate;
        TextView tvPrice;
        RelativeLayout btnDelete;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvNameTour = itemView.findViewById(R.id.tvNameTour);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
