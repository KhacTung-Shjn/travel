package com.example.mytravel.models.city;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Place implements Parcelable {

    @SerializedName("idPlace")
    @Expose
    private String idPlace;
    @SerializedName("namePlace")
    @Expose
    private String namePlace;
    @SerializedName("ratePlace")
    @Expose
    private float ratePlace;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("isLove")
    @Expose
    private boolean isLove;
    @SerializedName("urlImagePlace")
    @Expose
    private String urlImagePlace;

    private String nameExplore;

    public String getNameExplore() {
        return nameExplore;
    }

    public void setNameExplore(String nameExplore) {
        this.nameExplore = nameExplore;
    }

    public String getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(String idPlace) {
        this.idPlace = idPlace;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public float getRatePlace() {
        return ratePlace;
    }

    public void setRatePlace(float ratePlace) {
        this.ratePlace = ratePlace;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public boolean isLove() {
        return isLove;
    }

    public void setLove(boolean love) {
        isLove = love;
    }

    public String getUrlImagePlace() {
        return urlImagePlace;
    }

    public void setUrlImagePlace(String urlImagePlace) {
        this.urlImagePlace = urlImagePlace;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idPlace);
        dest.writeString(this.namePlace);
        dest.writeFloat(this.ratePlace);
        dest.writeString(this.lat);
        dest.writeString(this.lng);
        dest.writeByte(this.isLove ? (byte) 1 : (byte) 0);
        dest.writeString(this.urlImagePlace);
        dest.writeString(this.nameExplore);
    }

    public Place() {
    }

    protected Place(Parcel in) {
        this.idPlace = in.readString();
        this.namePlace = in.readString();
        this.ratePlace = in.readFloat();
        this.lat = in.readString();
        this.lng = in.readString();
        this.isLove = in.readByte() != 0;
        this.urlImagePlace = in.readString();
        this.nameExplore = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
