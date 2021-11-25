package com.with.us;

public class ClubListItem {
    private String uid;
    private String title;
    private String content;
    private int likes;
    private int comments;
    private String postImage;

    public String getUid() {
        return uid;
    }

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

    public ClubListItem(String uid, String title, String content, int likes, int comments, String postImage) {
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.comments = comments;
        this.postImage = postImage;
    }
}
