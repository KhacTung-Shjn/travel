package com.example.mytravel.models.city;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Explore implements Parcelable {
    @SerializedName("id_explore")
    @Expose
    private String id;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("hot_destinations")
    @Expose
    private List<String> hotDestinations;
    @SerializedName("is_love")
    @Expose
    private boolean isLove;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("name_explore")
    @Expose
    private String nameExplore;
    @SerializedName("rate_explore")
    @Expose
    private float rateExplore;
    @SerializedName("image_explore")
    @Expose
    private String urlImage;

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getHotDestinations() {
        return hotDestinations;
    }

    public void setHotDestinations(List<String> hotDestinations) {
        this.hotDestinations = hotDestinations;
    }

    public boolean isLove() {
        return isLove;
    }

    public void setLove(boolean love) {
        isLove = love;
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

    public String getNameExplore() {
        return nameExplore;
    }

    public void setNameExplore(String nameExplore) {
        this.nameExplore = nameExplore;
    }

    public float getRateExplore() {
        return rateExplore;
    }

    public void setRateExplore(float rateExplore) {
        this.rateExplore = rateExplore;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.desc);
        dest.writeStringList(this.hotDestinations);
        dest.writeByte(this.isLove ? (byte) 1 : (byte) 0);
        dest.writeString(this.lat);
        dest.writeString(this.lng);
        dest.writeString(this.nameExplore);
        dest.writeFloat(this.rateExplore);
        dest.writeString(this.urlImage);
    }

    public Explore() {
    }

    protected Explore(Parcel in) {
        this.id = in.readString();
        this.desc = in.readString();
        this.hotDestinations = in.createStringArrayList();
        this.isLove = in.readByte() != 0;
        this.lat = in.readString();
        this.lng = in.readString();
        this.nameExplore = in.readString();
        this.rateExplore = in.readFloat();
        this.urlImage = in.readString();
    }

    public static final Creator<Explore> CREATOR = new Creator<Explore>() {
        @Override
        public Explore createFromParcel(Parcel source) {
            return new Explore(source);
        }

        @Override
        public Explore[] newArray(int size) {
            return new Explore[size];
        }
    };
}
