package com.example.mytravel.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInformation implements Parcelable {

    //social
    @SerializedName("providerId")
    @Expose
    private String providerId;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;

    //firebase
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("birth")
    @Expose
    private String birth;
    @SerializedName("gender")
    @Expose
    private int gender;
    @SerializedName("phone")
    @Expose
    private String phone;


    public UserInformation() {
    }

    public UserInformation(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getPhotoUrl() {
        return avatarUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.avatarUrl = photoUrl;
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
        dest.writeString(this.avatarUrl);
        dest.writeString(this.address);
        dest.writeString(this.avatar);
        dest.writeString(this.birth);
        dest.writeInt(this.gender);
        dest.writeString(this.phone);
    }

    protected UserInformation(Parcel in) {
        this.providerId = in.readString();
        this.uid = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.avatarUrl = in.readString();
        this.address = in.readString();
        this.avatar = in.readString();
        this.birth = in.readString();
        this.gender = in.readInt();
        this.phone = in.readString();
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
