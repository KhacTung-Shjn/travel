package com.example.mytravel.ui.detailcity;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.models.city.Explore;
import com.example.mytravel.models.favorites.FavoritesExplore;
import com.example.mytravel.utils.CommonUtils;

import java.util.ArrayList;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> {

    private ArrayList<Explore> listExplores;
    private Context context;
    private OnClickItemInCity onClickItemInCity;
    private ArrayList<FavoritesExplore> favoritesExplores;
    private ArrayList<String> listIdExplore = new ArrayList<>();

    public void setOnClickItemInCity(OnClickItemInCity onClickItemInCity) {
        this.onClickItemInCity = onClickItemInCity;
    }

    public ExploreAdapter(Context context) {
        this.context = context;
        this.favoritesExplores = MainApp.getInstance().getAppDataManager().getListFavoritesExplore();
        getIdExplore();
    }

    private void getIdExplore() {
        for (FavoritesExplore favoritesExplore : this.favoritesExplores) {
            listIdExplore.add(favoritesExplore.getIdExplore());
        }
    }

    public void setListExplores(ArrayList<Explore> listExplores) {
        this.listExplores = listExplores;
        notifyDataSetChanged();
    }

    public void replaceAllData(ArrayList<Explore> listExplores) {
        if (this.listExplores != null) {
            this.listExplores.clear();
            this.listExplores.addAll(listExplores);
        } else {
            this.listExplores = listExplores;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExploreViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_explore, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {
        Explore explore = listExplores.get(position);
        if (explore != null) {
            if (!TextUtils.isEmpty(explore.getUrlImage())) {
                CommonUtils.loadImage(context, explore.getUrlImage(), holder.ivExplore);
            }
            if (!TextUtils.isEmpty(explore.getNameExplore())) {
                holder.tvTitle.setText(explore.getNameExplore());
            }
            holder.tvRate.setText(String.valueOf(explore.getRateExplore()));

            holder.isLove.setSelected(listIdExplore.contains(explore.getId()));

            holder.isLove.setOnClickListener(v -> {
                holder.isLove.setSelected(!holder.isLove.isSelected());
                onClickItemInCity.onClickIsLoveExplore(explore.getId(), holder.isLove.isSelected());
            });

            holder.itemView.setOnClickListener(v -> onClickItemInCity.onClickExplore(explore));
        }
    }

    @Override
    public int getItemCount() {
        return listExplores.size();
    }

    static class ExploreViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivExplore;
        private TextView isLove;
        private TextView tvTitle;
        private TextView tvRate;

        public ExploreViewHolder(@NonNull View itemView) {
            super(itemView);
            ivExplore = itemView.findViewById(R.id.ivExplore);
            isLove = itemView.findViewById(R.id.isLove);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvRate = itemView.findViewById(R.id.tvRate);
        }
    }
}
