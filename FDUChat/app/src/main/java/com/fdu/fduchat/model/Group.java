package com.fdu.fduchat.model;

import java.util.ArrayList;

public class Group {

    private String group_name;
    private ArrayList<String> friends = new ArrayList<String>();

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public Group(String group_name) {
        this.group_name = group_name;
    }

    @Override
    public String toString() {
        return "Group{" +
                "group_name='" + group_name + '\'' +
                ", friends=" + friends +
                '}';
    }

    public Group(String group_name, ArrayList<String> friends) {
        this.group_name = group_name;
        this.friends = friends;
    }

    public Group() {

    }
}
