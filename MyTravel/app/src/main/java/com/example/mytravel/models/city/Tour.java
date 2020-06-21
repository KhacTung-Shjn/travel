package com.example.mytravel.models.city;

import android.os.Parcel;
import android.os.Parcelable;

public class Tour implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Tour() {
    }

    protected Tour(Parcel in) {
    }

    public static final Creator<Tour> CREATOR = new Creator<Tour>() {
        @Override
        public Tour createFromParcel(Parcel source) {
            return new Tour(source);
        }

        @Override
        public Tour[] newArray(int size) {
            return new Tour[size];
        }
    };
}
