package com.example.mytravel.ui.listtour;

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
import com.example.mytravel.utils.OnClickItem;

import java.util.ArrayList;

public class ItemTourAdapter extends RecyclerView.Adapter<ItemTourAdapter.ItemTourViewHolder> {

    private ArrayList<TourPopular> listTours;
    private Context context;
    private OnClickItem<TourPopular> popularOnClickItem;

    public ItemTourAdapter(Context context) {
        this.context = context;
    }

    public void setListTours(ArrayList<TourPopular> listTours) {
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

    public void setPopularOnClickItem(OnClickItem<TourPopular> popularOnClickItem) {
        this.popularOnClickItem = popularOnClickItem;
    }

    @NonNull
    @Override
    public ItemTourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemTourViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_tour, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemTourViewHolder holder, int position) {
        TourPopular tour = listTours.get(position);
        if (tour != null) {
            if (!TextUtils.isEmpty(tour.getUrlImage())) {
                CommonUtils.loadImage(context, tour.getUrlImage(), holder.ivItemTour);
            }
            if (!TextUtils.isEmpty(tour.getNameTour())) {
                holder.tvNameTour.setText(tour.getNameTour());
            }
            if (!TextUtils.isEmpty(tour.getTime())) {
                holder.tvAnnotate.setText("( " + tour.getTime() + " )");
            }
            if (tour.getRate() != -1) {
                holder.tvRate.setText(String.valueOf(tour.getRate()));
            }
            if (tour.getMoney() != -1) {
                holder.tvPrice.setText(String.valueOf(tour.getMoney() + " $"));
            }

            holder.isLove.setSelected(tour.isLove());
            holder.isLove.setOnClickListener(v -> holder.isLove.setSelected(!holder.isLove.isSelected()));
            holder.itemView.setOnClickListener(v -> popularOnClickItem.onClickItem(tour));
        }
    }

    @Override
    public int getItemCount() {
        return listTours.size();
    }

    static class ItemTourViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivItemTour;
        private TextView tvNameTour;
        private TextView tvAnnotate;
        private TextView tvRate;
        private TextView tvPrice;
        private TextView isLove;

        public ItemTourViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemTour = itemView.findViewById(R.id.ivItemTour);
            tvNameTour = itemView.findViewById(R.id.tvNameTour);
            tvAnnotate = itemView.findViewById(R.id.tvAnnotate);
            tvRate = itemView.findViewById(R.id.tvRate);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            isLove = itemView.findViewById(R.id.isLove);
        }
    }
}
