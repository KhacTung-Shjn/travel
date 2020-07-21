package com.example.mytravel.ui.imagehot;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.models.imagehot.ImageHot;
import com.example.mytravel.utils.CommonUtils;
import com.example.mytravel.utils.OnClickItem;

import java.util.ArrayList;

public class ImageHotAdapter extends RecyclerView.Adapter<ImageHotAdapter.ImageHotViewHolder> {

    private ArrayList<ImageHot> listUrlImageHot;
    private Context context;
    private OnClickItem<ImageHot> onClickItem;

    public ImageHotAdapter(Context context) {
        this.context = context;
    }

    public void setOnClickItem(OnClickItem<ImageHot> onClickItem) {
        this.onClickItem = onClickItem;
    }

    public void setListUrlImageHot(ArrayList<ImageHot> listUrlImageHot) {
        this.listUrlImageHot = listUrlImageHot;
        notifyDataSetChanged();
    }

    public void replaceAllData(ArrayList<ImageHot> listUrlImageHot) {
        if (this.listUrlImageHot != null) {
            this.listUrlImageHot.clear();
            this.listUrlImageHot.addAll(listUrlImageHot);
        } else {
            this.listUrlImageHot = listUrlImageHot;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageHotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageHotViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_list_image_hot, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHotViewHolder holder, int position) {
        ImageHot imageHot = listUrlImageHot.get(position);
        if (imageHot != null) {
            if (!TextUtils.isEmpty(imageHot.getUrlImageHot())) {
                CommonUtils.loadImage(context, imageHot.getUrlImageHot(), holder.ivImageHot);
            }
            holder.itemView.setOnClickListener(v -> onClickItem.onClickItem(imageHot));
        }
    }

    @Override
    public int getItemCount() {
        return listUrlImageHot.size();
    }

    static class ImageHotViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImageHot;

        public ImageHotViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImageHot = itemView.findViewById(R.id.ivImageHot);
        }
    }
}
