package com.example.mytravel.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.models.city.City;
import com.example.mytravel.utils.CommonUtils;

import java.util.ArrayList;

public class ListCityAdapter extends RecyclerView.Adapter<ListCityAdapter.ListCityViewHolder> {

    private ArrayList<City> listCities;
    private Context context;
    private OnClickItemCity onClickItemCity;

    public ListCityAdapter(Context context) {
        this.context = context;
    }

    public void setOnClickItemCity(OnClickItemCity onClickItemCity) {
        this.onClickItemCity = onClickItemCity;
    }

    public void setListCities(ArrayList<City> listCities) {
        this.listCities = listCities;
        notifyDataSetChanged();
    }

    public void replaceAllData(ArrayList<City> listCities) {
        if (this.listCities != null) {
            this.listCities.clear();
            this.listCities.addAll(listCities);
        } else {
            this.listCities = listCities;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListCityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListCityViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_city, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListCityViewHolder holder, int position) {
        if (listCities != null) {
            City city = listCities.get(position);
            if (city != null) {
                holder.tvNameCity.setText(city.getNameCity());
                CommonUtils.loadImage(context, city.getImageCity(), holder.ivCity);
                holder.itemView.setOnClickListener(v -> onClickItemCity.onClickDetail(city));
            }
        }
    }

    @Override
    public int getItemCount() {
        return listCities.size();
    }

    static class ListCityViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCity;
        private TextView tvNameCity;

        public ListCityViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCity = itemView.findViewById(R.id.ivCity);
            tvNameCity = itemView.findViewById(R.id.tvNameCity);
        }
    }
}
