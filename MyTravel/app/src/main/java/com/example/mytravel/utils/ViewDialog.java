package com.example.mytravel.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.example.mytravel.R;

import java.util.Objects;

public class ViewDialog {
    private Activity activity;
    private Dialog dialog;

    public ViewDialog(Activity activity) {
        this.activity = activity;
    }


    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View mView = activity.getLayoutInflater().inflate(R.layout.dialog_loading, null);
        builder.setView(mView);
        dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));

        ImageView gifImageView = mView.findViewById(R.id.ivLoading);

        Glide.with(activity)
                .load(R.drawable.giphy)
                .placeholder(R.drawable.giphy)
                .centerCrop()
                .into(gifImageView);

        dialog.setCancelable(false);
        dialog.show();
    }

    public void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
