package com.fdu.fduchat.utils;

public class Constant {
    public static final String LOG_TAG = "FDUCHAT_LOG";

    public static final String MESSAGE_RECEIVED = "cn.jpush.android.intent.MESSAGE_RECEIVED";

    public static final String SERVER_IP = "http://45.32.251.133";
    public static final String SERVER_PORT = "80";
    public static final String SERVER_ADDRESS = SERVER_IP + ":" + SERVER_PORT;
    public static final String SERVER_CREATE_USER_ADDRESS = SERVER_ADDRESS + "/users";
    public static final String SERVER_LOGIN_ADDRESS = SERVER_ADDRESS + "/login";
    public static final String SERVER_GET_CONTACTS = SERVER_ADDRESS + "/users";
    public static final String SERVER_SEND_MESSAGE_ADRESS = SERVER_ADDRESS + "/message/send";

    public static final String CUSTOM_DATA_KEY_USER = "user";
    public static final String CUSTOM_DATA_KEY_CONTACTS = "contacts";
    public static final String CUSTOM_DATA_KEY_CHATTINGS = "chattings";

    public static final String INTENT_EXTRA_KEY_FRIEND = "friend";

}
