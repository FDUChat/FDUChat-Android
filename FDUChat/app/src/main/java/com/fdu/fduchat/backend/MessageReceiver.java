package com.fdu.fduchat.backend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fdu.fduchat.utils.Constant;

import cn.jpush.android.api.JPushInterface;

public class MessageReceiver extends BroadcastReceiver {

    public MessageReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(Constant.LOG_TAG, "receive");
        Log.d(Constant.LOG_TAG, intent.getAction());
        if (Constant.MESSAGE_RECEIVED.equals(intent.getAction())) {
            String messge = intent.getStringExtra(JPushInterface.EXTRA_TITLE);
            String extras = intent.getStringExtra(JPushInterface.EXTRA_MESSAGE);
            Log.d(Constant.LOG_TAG, "new message: ");
            Log.d(Constant.LOG_TAG, messge);
            Log.d(Constant.LOG_TAG, extras);
        }
    }

}
