package com.example.mytravel.ui.detailexplore.placelist;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.models.city.Place;
import com.example.mytravel.utils.CommonUtils;
import com.example.mytravel.utils.OnClickItem;

import java.util.ArrayList;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceListViewHolder> {

    private ArrayList<Place> listPlaces;
    private Context context;
    private OnClickItem<Place> placeOnClickItem;

    public PlaceListAdapter(Context context) {
        this.context = context;
    }

    public void setPlaceOnClickItem(OnClickItem<Place> placeOnClickItem) {
        this.placeOnClickItem = placeOnClickItem;
    }

    public void setListPlaces(ArrayList<Place> listPlaces) {
        this.listPlaces = listPlaces;
        notifyDataSetChanged();
    }

    public void replaceAllData(ArrayList<Place> listPlaces) {
        if (this.listPlaces != null) {
            this.listPlaces.clear();
            this.listPlaces.addAll(listPlaces);
        } else {
            this.listPlaces = listPlaces;
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PlaceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlaceListViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_list_place, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceListViewHolder holder, int position) {
        Place place = listPlaces.get(position);
        if (place != null) {
            if (!TextUtils.isEmpty(place.getUrlImagePlace())) {
                CommonUtils.loadImage(context, place.getUrlImagePlace(), holder.ivItemPlace);
            }
            if (!TextUtils.isEmpty(place.getNamePlace())) {
                holder.tvTitlePlace.setText(place.getNamePlace());
            }
            if (!TextUtils.isEmpty(place.getNameExplore())) {
                holder.tvNameExplore.setText(place.getNameExplore());
            }
            if (place.getRatePlace() != -1) {
                holder.tvRate.setText(String.valueOf(place.getRatePlace()));
            }
            holder.cbLove.setSelected(place.isLove());

            holder.cbLove.setOnClickListener(v -> holder.cbLove.setSelected(!holder.cbLove.isSelected()));
            holder.itemView.setOnClickListener(v -> placeOnClickItem.onClickItem(place));
        }
    }

    @Override
    public int getItemCount() {
        return listPlaces.size();
    }

    static class PlaceListViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemPlace;
        TextView tvTitlePlace;
        TextView tvNameExplore;
        TextView tvRate;
        TextView cbLove;

        public PlaceListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemPlace = itemView.findViewById(R.id.ivItemPlace);
            tvTitlePlace = itemView.findViewById(R.id.tvTitlePlace);
            tvNameExplore = itemView.findViewById(R.id.tvNameExplore);
            tvRate = itemView.findViewById(R.id.tvRate);
            cbLove = itemView.findViewById(R.id.cbLove);
        }
    }
}
