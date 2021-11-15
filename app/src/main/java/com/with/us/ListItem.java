package com.with.us;

public class ListItem {
    private String name;
    private String description;
    private String score;
    private String number;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getScore() {
        return score;
    }

    public String getNumber() {
        return number;
    }

    ListItem(String name, String description, String score, String number) {
        this.name = name;
        this.description = description;
        this.score = score;
        this.number = number;
    }
}
