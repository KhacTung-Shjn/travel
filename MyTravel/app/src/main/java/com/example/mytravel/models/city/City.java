package com.example.mytravel.models.city;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City implements Parcelable {

    @SerializedName("id_city")
    @Expose
    private String id_city;
    @SerializedName("name_city")
    @Expose
    private String name_city;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("rate_city")
    @Expose
    private float rate_city;
    @SerializedName("image_city")
    @Expose
    private String image_city;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("tour")
    @Expose
    private Tour tour;
    @SerializedName("place")
    @Expose
    private Place place;
    @SerializedName("explore")
    @Expose
    private Explore explore;


    public City(String id_city, String name_city, String lat, String lng, float rate_city, String image_city, Tour tour, Place place, Explore explore) {
        this.id_city = id_city;
        this.name_city = name_city;
        this.lat = lat;
        this.lng = lng;
        this.rate_city = rate_city;
        this.image_city = image_city;
        this.tour = tour;
        this.place = place;
        this.explore = explore;
    }

    public City() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIdCity() {
        return id_city;
    }

    public void setIdCity(String id_city) {
        this.id_city = id_city;
    }

    public String getNameCity() {
        return name_city;
    }

    public void setNameCity(String name_city) {
        this.name_city = name_city;
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

    public float getRateCity() {
        return rate_city;
    }

    public void setRateCity(float rate_city) {
        this.rate_city = rate_city;
    }

    public String getImageCity() {
        return image_city;
    }

    public void setImageCity(String image_city) {
        this.image_city = image_city;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Explore getExplore() {
        return explore;
    }

    public void setExplore(Explore explore) {
        this.explore = explore;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_city);
        dest.writeString(this.name_city);
        dest.writeString(this.lat);
        dest.writeString(this.lng);
        dest.writeFloat(this.rate_city);
        dest.writeString(this.image_city);
        dest.writeString(this.desc);
        dest.writeParcelable(this.tour, flags);
        dest.writeParcelable(this.place, flags);
        dest.writeParcelable(this.explore, flags);
    }

    protected City(Parcel in) {
        this.id_city = in.readString();
        this.name_city = in.readString();
        this.lat = in.readString();
        this.lng = in.readString();
        this.rate_city = in.readFloat();
        this.image_city = in.readString();
        this.desc = in.readString();
        this.tour = in.readParcelable(Tour.class.getClassLoader());
        this.place = in.readParcelable(Place.class.getClassLoader());
        this.explore = in.readParcelable(Explore.class.getClassLoader());
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
