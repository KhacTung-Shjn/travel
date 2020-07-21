package com.example.mytravel.models.favorites;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FavoritesPhoto implements Parcelable {

    @SerializedName("idImageHot")
    private String idImagehot;
    @SerializedName("idExplore")
    private String idExplore;


    public String getIdExplore() {
        return idExplore;
    }

    public void setIdExplore(String idExplore) {
        this.idExplore = idExplore;
    }

    public FavoritesPhoto(String idImagehot) {
        this.idImagehot = idImagehot;
    }

    public String getIdImagehot() {
        return idImagehot;
    }

    public void setIdImagehot(String idImagehot) {
        this.idImagehot = idImagehot;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idImagehot);
        dest.writeString(this.idExplore);
    }

    protected FavoritesPhoto(Parcel in) {
        this.idImagehot = in.readString();
        this.idExplore = in.readString();
    }

    public static final Creator<FavoritesPhoto> CREATOR = new Creator<FavoritesPhoto>() {
        @Override
        public FavoritesPhoto createFromParcel(Parcel source) {
            return new FavoritesPhoto(source);
        }

        @Override
        public FavoritesPhoto[] newArray(int size) {
            return new FavoritesPhoto[size];
        }
    };
}
