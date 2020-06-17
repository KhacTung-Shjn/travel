package com.example.mytravel.models.user;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class UserInformation implements Parcelable {
    private String providerId;
    private String uid;
    private String name;
    private String email;
    private Uri photoUrl;

    public UserInformation(String providerId, String uid, String name, String email, Uri photoUrl) {
        this.providerId = providerId;
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    public UserInformation() {
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.providerId);
        dest.writeString(this.uid);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeParcelable(this.photoUrl, flags);
    }

    protected UserInformation(Parcel in) {
        this.providerId = in.readString();
        this.uid = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.photoUrl = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<UserInformation> CREATOR = new Creator<UserInformation>() {
        @Override
        public UserInformation createFromParcel(Parcel source) {
            return new UserInformation(source);
        }

        @Override
        public UserInformation[] newArray(int size) {
            return new UserInformation[size];
        }
    };
}
