package com.fdu.fduchat.backend;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.fdu.fduchat.model.User;
import com.fdu.fduchat.ui.LoginActivity;
import com.fdu.fduchat.ui.MainActivity;
import com.fdu.fduchat.utils.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class Core {

    private MessageReceiver messageReceiver;

    public void init(Context context) {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(context);
        registerMessageReceiver(context);
        JPushInterface.setAliasAndTags(context, "slardar1", null, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.d(Constant.LOG_TAG, String.valueOf(i));
            }
        });
    }

    public void registerMessageReceiver(Context context) {
        messageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Constant.MESSAGE_RECEIVED);
        context.registerReceiver(messageReceiver, filter);
    }

}
