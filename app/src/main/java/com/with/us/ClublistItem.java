package com.with.us;

public class ClubListItem {
    private String title;
    private String content;
    private int likes;
    private int comments;

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

    public ClubListItem(String title, String content, int likes, int comments) {
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.comments = comments;
    }
}
