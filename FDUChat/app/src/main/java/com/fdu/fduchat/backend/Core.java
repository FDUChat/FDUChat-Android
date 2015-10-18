package com.fdu.fduchat.backend;

import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import com.fdu.fduchat.message.BusProvider;
import com.fdu.fduchat.message.CreateUserResult;
import com.fdu.fduchat.message.GetContactsResult;
import com.fdu.fduchat.message.LoginResult;
import com.fdu.fduchat.message.PutContactsResult;
import com.fdu.fduchat.message.SendMessageResult;
import com.fdu.fduchat.model.Contacts;
import com.fdu.fduchat.model.ExMessage;
import com.fdu.fduchat.model.Message;
import com.fdu.fduchat.model.User;
import com.fdu.fduchat.utils.Constant;
import com.litesuits.http.HttpConfig;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.impl.apache.ApacheHttpClient;
import com.litesuits.http.request.JsonRequest;
import com.litesuits.http.request.content.JsonBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class Core {

    private InfoReceiver infoReceiver;
    private LiteHttp client;
    private Map<String, Object> customData = new HashMap<String, Object>();
    private static Context context;
    public static Context getApplicationContext() {
        return Core.context;
    }

    public Map<String, Object> getCustomData() {
        return customData;
    }

    public Core() {
        customData.put(Constant.CUSTOM_DATA_KEY_CONTACTS, new Contacts());
        customData.put(Constant.CUSTOM_DATA_KEY_CHATTINGS, new HashMap<String, ArrayList<ExMessage> >());
    }

    public void init(Context context) {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(context);
        registerMessageReceiver(context);
        client = new ApacheHttpClient(new HttpConfig(context));
        this.context = context;
    }

    private void registerMessageReceiver(Context context) {
        infoReceiver = new InfoReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Constant.MESSAGE_RECEIVED);
        context.registerReceiver(infoReceiver, filter);
    }


    class CreateUser implements Runnable {
        private User u;
        public CreateUser(User u) {
            this.u = u;
        }
        @Override
        public void run() {
            JsonRequest<CreateUserResult> req = new JsonRequest(
                    Constant.SERVER_CREATE_USER_ADDRESS + "/" + u.getUsername(), CreateUserResult.class);
            req.setHttpBody(new JsonBody(u));
            final CreateUserResult r = client.post(req);
            JPushInterface.setAliasAndTags(context, u.getUsername(), null, new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    if (i == 0) {
                        BusProvider.getBus().post(r);
                    } else {
                        Log.d(Constant.LOG_TAG, "Set alias error!");
                    }
                }
            });
        }
    }
    public void createUser(User u) {
        new Thread(new CreateUser(u)).start();
    }

    class Login implements Runnable {
        private User u;
        public Login(User u) {
            this.u = u;
        }
        @Override
        public void run() {
            JsonRequest<LoginResult> req = new JsonRequest(
                    Constant.SERVER_LOGIN_ADDRESS, LoginResult.class
            );
            req.setHttpBody(new JsonBody(u));
            final LoginResult r = client.post(req);
            JPushInterface.setAliasAndTags(context, u.getUsername(), null, new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    if (i == 0) {
                        BusProvider.getBus().post(r);
                    } else {
                        Log.d(Constant.LOG_TAG, "Set alias error!");
                    }
                }
            });
        }
    }
    public void login(User u) {
        new Thread(new Login(u)).start();
    }

    class GetContacts implements Runnable {

        private User u;
        public GetContacts(User u) {
            this.u = u;
        }

        @Override
        public void run() {
            JsonRequest<GetContactsResult> req = new JsonRequest(
                    Constant.SERVER_GET_CONTACTS + "/" + u.getUsername() + "/contacts",
                    GetContactsResult.class
            );
            GetContactsResult r = client.get(req);
            BusProvider.getBus().post(r);
        }
    }
    public void getContacts(User u) {
        new Thread(new GetContacts(u)).start();
    }

    class PutContacts implements Runnable {

        private Contacts c;
        private User u;

        public PutContacts(User u, Contacts c) {
            this.u = u;
            this.c = c;
        }

        @Override
        public void run() {
            JsonRequest<PutContactsResult> req = new JsonRequest(
                    Constant.SERVER_GET_CONTACTS + "/" + u.getUsername() + "/contacts",
                    PutContactsResult.class
            );
            req.setHttpBody(new JsonBody(c));

            req.setMethod(HttpMethods.Put);
            PutContactsResult r = client.put(req);
            BusProvider.getBus().post(r);
        }
    }
    public void putContacts(User u, Contacts c) {
        new Thread(new PutContacts(u, c)).start();
    }

    class SendMessage implements Runnable {

        private Message m;
        public SendMessage(Message m) {
            this.m = m;
        }

        @Override
        public void run() {
            JsonRequest<SendMessageResult> req = new JsonRequest<SendMessageResult>(
                    Constant.SERVER_SEND_MESSAGE_ADRESS,
                    SendMessageResult.class
            );
            req.setHttpBody(new JsonBody(m));
            SendMessageResult r = client.post(req);
            if (r == null) {
                Log.d(Constant.LOG_TAG, "Send message error!");
                return;
            }
            BusProvider.getBus().post(r);
        }
    }
    public void sendMessage(Message m) {
        new Thread(new SendMessage(m)).start();
    }

}
