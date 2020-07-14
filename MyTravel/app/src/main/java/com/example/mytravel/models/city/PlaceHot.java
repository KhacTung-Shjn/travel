package com.example.mytravel.models.city;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceHot implements Parcelable {
    @SerializedName("idPlaceHot")
    @Expose
    private String idPlaceHot;
    @SerializedName("timeOpen")
    @Expose
    private String timeOpen;
    @SerializedName("desPlaceHot")
    @Expose
    private String desPlaceHot;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("isLovePlaceHot")
    @Expose
    private boolean isLovePlaceHot;
    @SerializedName("urlImagePlaceHot")
    @Expose
    private String urlImagePlaceHot;

    @SerializedName("namePlaceHot")
    @Expose
    private String namePlaceHot;


    public String getNamePlaceHot() {
        return namePlaceHot;
    }

    public void setNamePlaceHot(String namePlaceHot) {
        this.namePlaceHot = namePlaceHot;
    }

    public String getIdPlaceHot() {
        return idPlaceHot;
    }

    public void setIdPlaceHot(String idPlaceHot) {
        this.idPlaceHot = idPlaceHot;
    }

    public String getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(String timeOpen) {
        this.timeOpen = timeOpen;
    }

    public String getDesPlaceHot() {
        return desPlaceHot;
    }

    public void setDesPlaceHot(String desPlaceHot) {
        this.desPlaceHot = desPlaceHot;
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

    public boolean isLovePlaceHot() {
        return isLovePlaceHot;
    }

    public void setLovePlaceHot(boolean lovePlaceHot) {
        isLovePlaceHot = lovePlaceHot;
    }

    public String getUrlImagePlaceHot() {
        return urlImagePlaceHot;
    }

    public void setUrlImagePlaceHot(String urlImagePlaceHot) {
        this.urlImagePlaceHot = urlImagePlaceHot;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idPlaceHot);
        dest.writeString(this.timeOpen);
        dest.writeString(this.desPlaceHot);
        dest.writeString(this.lat);
        dest.writeString(this.lng);
        dest.writeByte(this.isLovePlaceHot ? (byte) 1 : (byte) 0);
        dest.writeString(this.urlImagePlaceHot);
        dest.writeString(this.namePlaceHot);
    }

    public PlaceHot() {
    }

    protected PlaceHot(Parcel in) {
        this.idPlaceHot = in.readString();
        this.timeOpen = in.readString();
        this.desPlaceHot = in.readString();
        this.lat = in.readString();
        this.lng = in.readString();
        this.isLovePlaceHot = in.readByte() != 0;
        this.urlImagePlaceHot = in.readString();
        this.namePlaceHot = in.readString();
    }

    public static final Creator<PlaceHot> CREATOR = new Creator<PlaceHot>() {
        @Override
        public PlaceHot createFromParcel(Parcel source) {
            return new PlaceHot(source);
        }

        @Override
        public PlaceHot[] newArray(int size) {
            return new PlaceHot[size];
        }
    };
}
