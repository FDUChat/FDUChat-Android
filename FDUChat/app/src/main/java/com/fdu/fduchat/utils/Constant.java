package com.fdu.fduchat.utils;

public class Constant {
    public static final String LOG_TAG = "FDUCHAT_LOG";

    public static final String MESSAGE_RECEIVED = "cn.jpush.android.intent.MESSAGE_RECEIVED";

    public static final String SERVER_IP = "http://192.168.1.18";
    public static final String SERVER_PORT = "4567";
    public static final String SERVER_ADDRESS = SERVER_IP + ":" + SERVER_PORT;
    public static final String SERVER_CREATE_USER_ADDRESS = SERVER_ADDRESS + "/users";
    public static final String SERVER_LOGIN_ADDRESS = SERVER_ADDRESS + "/login";
    public static final String SERVER_GET_CONTACTS = SERVER_ADDRESS + "/users";

    public static final String CUSTOM_DATA_KEY_USER = "user";
    public static final String CUSTOM_DATA_KEY_CONTACTS = "contacts";

}
