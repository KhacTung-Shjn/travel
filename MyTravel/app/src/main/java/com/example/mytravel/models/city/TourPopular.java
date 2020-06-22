package com.example.mytravel.models.city;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TourPopular implements Parcelable {

    @SerializedName("id_tour")
    @Expose
    private String id;
    @SerializedName("introduction")
    @Expose
    private String introduction;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("is_love")
    @Expose
    private boolean isLove;
    @SerializedName("money")
    @Expose
    private int money;
    @SerializedName("nameTour")
    @Expose
    private String nameTour;
    @SerializedName("rate_tour")
    @Expose
    private int rate;
    @SerializedName("schedule")
    @Expose
    private String schedule;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("traffic")
    @Expose
    private String traffic;
    @SerializedName("image_tour")
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isLove() {
        return isLove;
    }

    public void setLove(boolean love) {
        isLove = love;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getNameTour() {
        return nameTour;
    }

    public void setNameTour(String nameTour) {
        this.nameTour = nameTour;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.introduction);
        dest.writeString(this.endDate);
        dest.writeByte(this.isLove ? (byte) 1 : (byte) 0);
        dest.writeInt(this.money);
        dest.writeString(this.nameTour);
        dest.writeInt(this.rate);
        dest.writeString(this.schedule);
        dest.writeString(this.startDate);
        dest.writeString(this.time);
        dest.writeString(this.traffic);
        dest.writeString(this.urlImage);
    }

    public TourPopular() {
    }

    protected TourPopular(Parcel in) {
        this.id = in.readString();
        this.introduction = in.readString();
        this.endDate = in.readString();
        this.isLove = in.readByte() != 0;
        this.money = in.readInt();
        this.nameTour = in.readString();
        this.rate = in.readInt();
        this.schedule = in.readString();
        this.startDate = in.readString();
        this.time = in.readString();
        this.traffic = in.readString();
        this.urlImage = in.readString();
    }

    public static final Creator<TourPopular> CREATOR = new Creator<TourPopular>() {
        @Override
        public TourPopular createFromParcel(Parcel source) {
            return new TourPopular(source);
        }

        @Override
        public TourPopular[] newArray(int size) {
            return new TourPopular[size];
        }
    };
}
