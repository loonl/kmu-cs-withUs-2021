package com.with.us;

public class ListComments {
    private String content;
    private String userid;
    private String time;
    private int viewType;

    public ListComments(String content, String userid, String time, int viewType) {
        this.content = content;
        this.userid = userid;
        this.time = time;
        this.viewType = viewType;
    }

    public String getContent() {
        return content;
    }

    public String getUserId() {
        return userid;
    }

    public String getTime() {
        return time;
    }

    public int getViewType() {
        return viewType;
    }
}
