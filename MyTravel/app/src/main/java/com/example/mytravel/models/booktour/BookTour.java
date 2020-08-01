package com.example.mytravel.models.booktour;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookTour implements Parcelable {

    @SerializedName("idBookTour")
    @Expose
    private String id;
    @SerializedName("checkIn")
    @Expose
    private String checkIn;
    @SerializedName("checkOut")
    @Expose
    private String checkOut;
    @SerializedName("numberAdults")
    @Expose
    private String numberAdults;
    @SerializedName("numberChildren")
    @Expose
    private String numberChildren;
    @SerializedName("nameUser")
    @Expose
    private String nameUser;
    @SerializedName("emailUser")
    @Expose
    private String emailUser;
    @SerializedName("phoneUser")
    @Expose
    private String phoneUser;
    @SerializedName("miniSecondCheckIn")
    @Expose
    private long miniSecondCheckIn;
    @SerializedName("miniSecondCheckOut")
    @Expose
    private long miniSecondCheckOut;
    @SerializedName("nameTour")
    @Expose
    private String nameTour;
    @SerializedName("priceTour")
    @Expose
    private String priceTour;
    @SerializedName("urlImageTour")
    @Expose
    private String urlImageTour;

    public BookTour(String id, String checkIn, String checkOut, String numberAdults, String numberChildren, String nameUser, String emailUser, String phoneUser, long miniSecondCheckIn, long miniSecondCheckOut) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberAdults = numberAdults;
        this.numberChildren = numberChildren;
        this.nameUser = nameUser;
        this.emailUser = emailUser;
        this.phoneUser = phoneUser;
        this.miniSecondCheckIn = miniSecondCheckIn;
        this.miniSecondCheckOut = miniSecondCheckOut;
    }

    public BookTour(String checkIn, String checkOut, String numberAdults, String numberChildren, String nameUser, String emailUser, String phoneUser, long miniSecondCheckIn, long miniSecondCheckOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberAdults = numberAdults;
        this.numberChildren = numberChildren;
        this.nameUser = nameUser;
        this.emailUser = emailUser;
        this.phoneUser = phoneUser;
        this.miniSecondCheckIn = miniSecondCheckIn;
        this.miniSecondCheckOut = miniSecondCheckOut;
    }

    public String getUrlImageTour() {
        return urlImageTour;
    }

    public void setUrlImageTour(String urlImageTour) {
        this.urlImageTour = urlImageTour;
    }

    public String getNameTour() {
        return nameTour;
    }

    public void setNameTour(String nameTour) {
        this.nameTour = nameTour;
    }

    public String getPriceTour() {
        return priceTour;
    }

    public void setPriceTour(String priceTour) {
        this.priceTour = priceTour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getNumberAdults() {
        return numberAdults;
    }

    public void setNumberAdults(String numberAdults) {
        this.numberAdults = numberAdults;
    }

    public String getNumberChildren() {
        return numberChildren;
    }

    public void setNumberChildren(String numberChildren) {
        this.numberChildren = numberChildren;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public long getMiniSecondCheckIn() {
        return miniSecondCheckIn;
    }

    public void setMiniSecondCheckIn(long miniSecondCheckIn) {
        this.miniSecondCheckIn = miniSecondCheckIn;
    }

    public long getMiniSecondCheckOut() {
        return miniSecondCheckOut;
    }

    public void setMiniSecondCheckOut(long miniSecondCheckOut) {
        this.miniSecondCheckOut = miniSecondCheckOut;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.checkIn);
        dest.writeString(this.checkOut);
        dest.writeString(this.numberAdults);
        dest.writeString(this.numberChildren);
        dest.writeString(this.nameUser);
        dest.writeString(this.emailUser);
        dest.writeString(this.phoneUser);
        dest.writeLong(this.miniSecondCheckIn);
        dest.writeLong(this.miniSecondCheckOut);
        dest.writeString(this.nameTour);
        dest.writeString(this.priceTour);
        dest.writeString(this.urlImageTour);
    }

    protected BookTour(Parcel in) {
        this.id = in.readString();
        this.checkIn = in.readString();
        this.checkOut = in.readString();
        this.numberAdults = in.readString();
        this.numberChildren = in.readString();
        this.nameUser = in.readString();
        this.emailUser = in.readString();
        this.phoneUser = in.readString();
        this.miniSecondCheckIn = in.readLong();
        this.miniSecondCheckOut = in.readLong();
        this.nameTour = in.readString();
        this.priceTour = in.readString();
        this.urlImageTour = in.readString();
    }

    public static final Creator<BookTour> CREATOR = new Creator<BookTour>() {
        @Override
        public BookTour createFromParcel(Parcel source) {
            return new BookTour(source);
        }

        @Override
        public BookTour[] newArray(int size) {
            return new BookTour[size];
        }
    };
}
