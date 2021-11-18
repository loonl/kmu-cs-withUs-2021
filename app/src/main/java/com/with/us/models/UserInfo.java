package com.with.us.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class UserInfo implements Parcelable {
//    public String email;
    public String displayName;
    public int birthDate;
    public String gender;
    public String region;
    public String interest;

    public UserInfo(String displayName, int birthDate, String gender, String region, String interest) {
//        this.email = email;
        this.displayName = displayName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.region = region;
        this.interest = interest;
    }

    protected UserInfo(Parcel in) {
//        email = in.readString();
        displayName = in.readString();
        birthDate = in.readInt();
        gender = in.readString();
        region = in.readString();
        interest = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
