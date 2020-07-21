package com.example.mytravel.models.imagehot;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageHot implements Parcelable {
    @SerializedName("idImagehot")
    @Expose
    private String idImageHot;
    @SerializedName("idExplore")
    @Expose
    private String idExplore;
    @SerializedName("urlImageHot")
    @Expose
    private String urlImageHot;

    public String getIdImageHot() {
        return idImageHot;
    }

    public void setIdImageHot(String idImageHot) {
        this.idImageHot = idImageHot;
    }

    public String getIdExplore() {
        return idExplore;
    }

    public void setIdExplore(String idExplore) {
        this.idExplore = idExplore;
    }

    public String getUrlImageHot() {
        return urlImageHot;
    }

    public void setUrlImageHot(String urlImageHot) {
        this.urlImageHot = urlImageHot;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idImageHot);
        dest.writeString(this.idExplore);
        dest.writeString(this.urlImageHot);
    }

    public ImageHot() {
    }

    protected ImageHot(Parcel in) {
        this.idImageHot = in.readString();
        this.idExplore = in.readString();
        this.urlImageHot = in.readString();
    }

    public static final Parcelable.Creator<ImageHot> CREATOR = new Parcelable.Creator<ImageHot>() {
        @Override
        public ImageHot createFromParcel(Parcel source) {
            return new ImageHot(source);
        }

        @Override
        public ImageHot[] newArray(int size) {
            return new ImageHot[size];
        }
    };
}
