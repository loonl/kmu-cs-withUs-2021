package com.with.us.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Comment implements Parcelable {
    public String author;
    public String category;
    public String displayName;
    public String content;
    public boolean isComment;
    public int likes;
    public int bundleId;
    public String postUid;
    public String profileImage;
    public boolean notification;

    public Comment(String author, String category, String displayName, String content, boolean isComment, int likes,
            int bundleId, String postUid, String profileImage, boolean notification) {
        this.author = author;
        this.category = category;
        this.displayName = displayName;
        this.content = content;
        this.isComment = isComment;
        this.likes = likes;
        this.bundleId = bundleId;
        this.postUid = postUid;
        this.profileImage = profileImage;
        this.notification = notification;
    }

    protected Comment(Parcel in) {
        author = in.readString();
        category = in.readString();
        displayName = in.readString();
        content = in.readString();
        isComment = in.readBoolean();
        likes = in.readInt();
        bundleId = in.readInt();
        postUid = in.readString();
        profileImage = in.readString();
        notification = in.readBoolean();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
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
