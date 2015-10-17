package com.fdu.fduchat.model;

import com.litesuits.http.request.param.HttpParamModel;

public class User implements HttpParamModel {

    private String username;
    private String password;
    private String alias;
    private String portrait;
    private Integer contact_id;
    private String location;
    private Integer gender;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Integer getContact_id() {
        return contact_id;
    }

    public void setContact_id(Integer contact_id) {
        this.contact_id = contact_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public User() {

    }

    public User(String username, String password, String alias, String portrait, Integer contact_id, String location, Integer gender) {
        this.username = username;
        this.password = password;
        this.alias = alias;
        this.portrait = portrait;
        this.contact_id = contact_id;
        this.location = location;
        this.gender = gender;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", alias='" + alias + '\'' +
                ", portrait='" + portrait + '\'' +
                ", contact_id=" + contact_id +
                ", location='" + location + '\'' +
                ", gender=" + gender +
                '}';
    }
}
