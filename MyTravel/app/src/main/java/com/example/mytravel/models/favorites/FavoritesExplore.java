package com.example.mytravel.models.favorites;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoritesExplore implements Parcelable {

    @SerializedName("idCity")
    @Expose
    private String idCity;
    @SerializedName("idExplore")
    @Expose
    private String idExplore;

    public FavoritesExplore(String idCity, String idExplore) {
        this.idCity = idCity;
        this.idExplore = idExplore;
    }

    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(String idCity) {
        this.idCity = idCity;
    }

    public String getIdExplore() {
        return idExplore;
    }

    public void setIdExplore(String idExplore) {
        this.idExplore = idExplore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idCity);
        dest.writeString(this.idExplore);
    }

    protected FavoritesExplore(Parcel in) {
        this.idCity = in.readString();
        this.idExplore = in.readString();
    }

    public static final Creator<FavoritesExplore> CREATOR = new Creator<FavoritesExplore>() {
        @Override
        public FavoritesExplore createFromParcel(Parcel source) {
            return new FavoritesExplore(source);
        }

        @Override
        public FavoritesExplore[] newArray(int size) {
            return new FavoritesExplore[size];
        }
    };
}
