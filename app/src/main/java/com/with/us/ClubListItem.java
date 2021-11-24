package com.with.us;

import android.net.Uri;

public class ClubListItem {
    private String title;
    private String content;
    private int likes;
    private int comments;
    private String postImage;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public String getPostImage() {
        return postImage;
    }

    public ClubListItem(String title, String content, int likes, int comments, String postImage) {
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.comments = comments;
        this.postImage = postImage.toString();
    }
}
