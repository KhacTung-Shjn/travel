package com.example.mytravel.ui.detailexplore.placehot;

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
import com.example.mytravel.models.city.PlaceHot;
import com.example.mytravel.utils.CommonUtils;

import java.util.ArrayList;

public class PlaceHotAdapter extends RecyclerView.Adapter<PlaceHotAdapter.PlaceHotViewHolder> {

    private ArrayList<PlaceHot> listPlaceHots;
    private Context context;
    private OnClickMap onClickMap;

    public PlaceHotAdapter(Context context) {
        this.context = context;
    }

    public void setOnClickMap(OnClickMap onClickMap) {
        this.onClickMap = onClickMap;
    }

    public void setListPlaceHots(ArrayList<PlaceHot> listPlaceHots) {
        this.listPlaceHots = listPlaceHots;
        notifyDataSetChanged();
    }


    public void replaceAll(ArrayList<PlaceHot> listPlaceHots) {
        if (this.listPlaceHots != null) {
            this.listPlaceHots.clear();
            this.listPlaceHots.addAll(listPlaceHots);
        } else {
            this.listPlaceHots = listPlaceHots;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaceHotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlaceHotViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_place_hot, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceHotViewHolder holder, int position) {
        PlaceHot placeHot = listPlaceHots.get(position);
        if (placeHot != null) {
            if (!TextUtils.isEmpty(placeHot.getUrlImagePlaceHot())) {
                CommonUtils.loadImage(context, placeHot.getUrlImagePlaceHot(), holder.ivPlaceHot);
            }
            if (!TextUtils.isEmpty(placeHot.getTimeOpen())) {
                holder.tvTimeOpen.setText(String.format("%s %s", context.getString(R.string.text_open), placeHot.getTimeOpen()));
            }
            if (!TextUtils.isEmpty(placeHot.getDesPlaceHot())) {
                holder.tvDestPlaceHot.setText(placeHot.getDesPlaceHot());
            }
            holder.isLovePlaceHot.setSelected(placeHot.isLovePlaceHot());

            holder.isLovePlaceHot.setOnClickListener(v -> holder.isLovePlaceHot.setSelected(!holder.isLovePlaceHot.isSelected()));
            holder.tvMapPlaceHot.setOnClickListener(v -> onClickMap.onClickOpenMap(placeHot.getLat(), placeHot.getLng(), placeHot));
        }
    }

    @Override
    public int getItemCount() {
        return listPlaceHots.size();
    }

    static class PlaceHotViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPlaceHot;
        TextView tvMapPlaceHot;
        TextView isLovePlaceHot;
        TextView tvTimeOpen;
        TextView tvDestPlaceHot;

        public PlaceHotViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPlaceHot = itemView.findViewById(R.id.ivPlaceHot);
            tvMapPlaceHot = itemView.findViewById(R.id.tvMapPlaceHot);
            isLovePlaceHot = itemView.findViewById(R.id.isLovePlaceHot);
            tvTimeOpen = itemView.findViewById(R.id.tvTimeOpen);
            tvDestPlaceHot = itemView.findViewById(R.id.tvDestPlaceHot);
        }
    }
}
