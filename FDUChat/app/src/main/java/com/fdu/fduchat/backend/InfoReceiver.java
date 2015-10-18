package com.fdu.fduchat.backend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fdu.fduchat.message.BusProvider;
import com.fdu.fduchat.model.ExMessage;
import com.fdu.fduchat.model.Message;
import com.fdu.fduchat.model.MessageContent;
import com.fdu.fduchat.utils.Constant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class InfoReceiver extends BroadcastReceiver {

    public class InfoExtras {
        private Integer type;
        private String sender;
        private String timestamp;
        private String receiver;

        public InfoExtras() {

        }

        public InfoExtras(Integer type, String sender, String timestamp, String receiver) {
            this.type = type;
            this.sender = sender;
            this.timestamp = timestamp;
            this.receiver = receiver;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        @Override
        public String toString() {
            return "InfoExtras{" +
                    "type=" + type +
                    ", sender='" + sender + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", receiver='" + receiver + '\'' +
                    '}';
        }
    }

    public final static Integer MESSAGE_INFO = 1;
    public final static Integer MOMENT_INFO = 2;

    private Core core;
    public InfoReceiver() {
        super();
        core = CoreProvider.getCoreInstance();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Constant.MESSAGE_RECEIVED.equals(intent.getAction())) {
            String messageString = intent.getStringExtra(JPushInterface.EXTRA_MESSAGE);
            String extrasString = intent.getStringExtra(JPushInterface.EXTRA_EXTRA);
            Gson gson = new Gson();
            InfoExtras infoExtras = gson.fromJson(extrasString, InfoExtras.class);
//            Log.d(Constant.LOG_TAG, infoExtras.toString());
            if (infoExtras.getType() == InfoReceiver.MESSAGE_INFO) {
                Message m = new Message(infoExtras);
                MessageContent mc = gson.fromJson(messageString, MessageContent.class);
                m.setContent(mc);
                ExMessage em = new ExMessage(m, false);
                String friendName = em.getSender();
                ArrayList<ExMessage> chatHistory;
                Map<String, ArrayList<ExMessage> > chatHistories =
                        (Map<String, ArrayList<ExMessage> >)core.getCustomData().get(Constant.CUSTOM_DATA_KEY_CHATTINGS);
                if (chatHistories.containsKey(friendName)) {
                    chatHistory = (ArrayList<ExMessage>)chatHistories.get(friendName);
                } else {
                    chatHistory = new ArrayList<ExMessage>();
                    chatHistories.put(friendName, chatHistory);
                }
                chatHistory.add(em);
                BusProvider.getBus().post(m);
            } else if (infoExtras.getType() == InfoReceiver.MOMENT_INFO) {

            } else {
                Log.d(Constant.LOG_TAG, "Unknown info!");
            }
        }
    }

}
