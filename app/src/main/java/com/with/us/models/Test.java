package com.with.us.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Test implements Parcelable {
    public String title;
    public String content;

    public Test(String title, String content) {
        this.title = title;
        this.content = content;
    }

    protected Test(Parcel in) {
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
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
