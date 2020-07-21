package com.example.mytravel.ui.detailexplore.map;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.models.city.PlaceHot;
import com.example.mytravel.utils.CommonUtils;
import com.example.mytravel.utils.OnClickItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HotMarkerAdapter extends RecyclerView.Adapter<HotMarkerAdapter.HotMarkerViewHolder> {

    private ArrayList<PlaceHot> listHotMarker;
    private Context context;
    private OnClickItem<PlaceHot> onClickItem;

    public HotMarkerAdapter(Context context) {
        this.context = context;
    }

    public void setOnClickItem(OnClickItem<PlaceHot> onClickItem) {
        this.onClickItem = onClickItem;
    }

    public void setListHotMarker(ArrayList<PlaceHot> listHotMarker) {
        this.listHotMarker = listHotMarker;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public HotMarkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HotMarkerViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_list_hot_marker, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HotMarkerViewHolder holder, int position) {
        PlaceHot placeHot = listHotMarker.get(position);
        if (placeHot != null) {
            if (!TextUtils.isEmpty(placeHot.getUrlImagePlaceHot())) {
                CommonUtils.loadImage(context, placeHot.getUrlImagePlaceHot(), holder.ivHotMarker);
            }
            if (!TextUtils.isEmpty(placeHot.getNamePlaceHot())) {
                holder.tvNamePlace.setText(placeHot.getNamePlaceHot());
            }

            holder.itemView.setOnClickListener(v -> onClickItem.onClickItem(placeHot));
        }
    }

    @Override
    public int getItemCount() {
        return listHotMarker.size();
    }

    static class HotMarkerViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivHotMarker;
        TextView tvNamePlace;

        public HotMarkerViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHotMarker = itemView.findViewById(R.id.ivHotMarker);
            tvNamePlace = itemView.findViewById(R.id.tvNamePlace);
        }
    }
}
