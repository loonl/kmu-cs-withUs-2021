package com.with.us.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import com.google.firebase.Timestamp;

public class PostDetail implements Parcelable {
    public String uid;
    public String author;
    public String category;
    public String displayName;
    public String title;
    public String content;
    public int comments;
    public int likes;
    public String postImage;
    public String createdAt;

    public PostDetail(String uid, String author, String category, String displayName, String title, String content, int comments,
            int likes, String postImage, String createdAt) {
        this.uid = uid;
        this.author = author;
        this.category = category;
        this.displayName = displayName;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.likes = likes;
        this.postImage = postImage;
        this.createdAt = createdAt;
    }

    protected PostDetail(Parcel in) {
        uid = in.readString();
        author = in.readString();
        category = in.readString();
        displayName = in.readString();
        title = in.readString();
        content = in.readString();
        comments = in.readInt();
        likes = in.readInt();
        postImage = in.readString();
        createdAt = in.readString();
    }

    public static final Creator<PostDetail> CREATOR = new Creator<PostDetail>() {
        @Override
        public PostDetail createFromParcel(Parcel in) {
            return new PostDetail(in);
        }

        @Override
        public PostDetail[] newArray(int size) {
            return new PostDetail[size];
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
