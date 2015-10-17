package com.fdu.fduchat.model;

import com.litesuits.http.request.param.HttpParamModel;

public class User implements HttpParamModel {
    public String username;
    public String password;
    public String alias;
    public String portrait;
    public Integer contact_id;
    public String location;
    public Integer gender;
}
