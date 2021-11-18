package com.with.us;

import java.util.ArrayList;

public class ListCategory {
    private ArrayList<String> child;
    private String groupName;

    ListCategory(String name) {
        groupName = name;
        child = new ArrayList<String>();
    }

    public ArrayList<String> getChild() {
        return child;
    }

    public String getGroupName() {
        return groupName;
    }

}
