package com.example.mytravel.models.favorites;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoritesTour implements Parcelable {

    @SerializedName("idCity")
    @Expose
    private String idCity;
    @SerializedName("idExplore")
    @Expose
    private String idExplore;
    @SerializedName("idTour")
    @Expose
    private String idTour;

    public FavoritesTour(String idCity, String idExplore, String idTour) {
        this.idCity = idCity;
        this.idExplore = idExplore;
        this.idTour = idTour;
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

    public String getIdTour() {
        return idTour;
    }

    public void setIdTour(String idTour) {
        this.idTour = idTour;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idCity);
        dest.writeString(this.idExplore);
        dest.writeString(this.idTour);
    }

    protected FavoritesTour(Parcel in) {
        this.idCity = in.readString();
        this.idExplore = in.readString();
        this.idTour = in.readString();
    }

    public static final Creator<FavoritesTour> CREATOR = new Creator<FavoritesTour>() {
        @Override
        public FavoritesTour createFromParcel(Parcel source) {
            return new FavoritesTour(source);
        }

        @Override
        public FavoritesTour[] newArray(int size) {
            return new FavoritesTour[size];
        }
    };
}
