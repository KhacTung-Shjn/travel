package com.example.mytravel.models.favorites;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoritesPlace implements Parcelable {

    @SerializedName("idPlace")
    @Expose
    private String idPlace;
    @SerializedName("idCity")
    @Expose
    private String idCity;
    @SerializedName("idExplore")
    @Expose
    private String idExplore;


    public FavoritesPlace(String idCity, String idExplore, String idPlace) {
        this.idPlace = idPlace;
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

    public String getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(String idPlace) {
        this.idPlace = idPlace;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idPlace);
        dest.writeString(this.idCity);
        dest.writeString(this.idExplore);
    }

    protected FavoritesPlace(Parcel in) {
        this.idPlace = in.readString();
        this.idCity = in.readString();
        this.idExplore = in.readString();
    }

    public static final Creator<FavoritesPlace> CREATOR = new Creator<FavoritesPlace>() {
        @Override
        public FavoritesPlace createFromParcel(Parcel source) {
            return new FavoritesPlace(source);
        }

        @Override
        public FavoritesPlace[] newArray(int size) {
            return new FavoritesPlace[size];
        }
    };
}
