package com.example.mytravel.ui.detailcity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.models.city.TourPopular;
import com.example.mytravel.utils.CommonUtils;

import java.util.ArrayList;

public class TourPopularAdapter extends RecyclerView.Adapter<TourPopularAdapter.TourPopularViewHolder> {

    private ArrayList<TourPopular> listTours;
    private Context context;
    private OnClickItemInCity onClickItemInCity;

    public TourPopularAdapter(Context context) {
        this.context = context;
    }

    public void setOnClickItemInCity(OnClickItemInCity onClickItemInCity) {
        this.onClickItemInCity = onClickItemInCity;
    }

    public void setListExplores(ArrayList<TourPopular> listTours) {
        this.listTours = listTours;
        notifyDataSetChanged();
    }

    public void replaceAllData(ArrayList<TourPopular> listTours) {
        if (this.listTours != null) {
            this.listTours.clear();
            this.listTours.addAll(listTours);
        } else {
            this.listTours = listTours;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TourPopularAdapter.TourPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TourPopularAdapter.TourPopularViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_tour_popular, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TourPopularAdapter.TourPopularViewHolder holder, int position) {
        TourPopular tourPopular = listTours.get(position);
        if (tourPopular != null) {
            if (!TextUtils.isEmpty(tourPopular.getUrlImage())) {
                CommonUtils.loadImage(context, tourPopular.getUrlImage(), holder.ivCity);
            }

            if (!TextUtils.isEmpty(tourPopular.getNameTour())) {
                holder.tvNameTour.setText(tourPopular.getNameTour());
            }
            if (!TextUtils.isEmpty(tourPopular.getTime())) {
                holder.tvTime.setText(tourPopular.getTime());
            }
            if (tourPopular.getMoney() != -1) {
                holder.tvMoney.setText(context.getString(R.string.text_form) + tourPopular.getMoney() + "$");
            }

            holder.itemView.setOnClickListener(v -> onClickItemInCity.onClickTour(tourPopular));
        }
    }

    @Override
    public int getItemCount() {
        return listTours.size();
    }

    static class TourPopularViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivCity;
        private TextView tvNameTour;
        private TextView tvTime;
        private TextView tvMoney;

        public TourPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCity = itemView.findViewById(R.id.ivCity);
            tvNameTour = itemView.findViewById(R.id.tvNameTour);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvMoney = itemView.findViewById(R.id.tvMoney);
        }
    }
}
