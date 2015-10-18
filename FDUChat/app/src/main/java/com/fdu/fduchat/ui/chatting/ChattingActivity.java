package com.fdu.fduchat.ui.chatting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdu.fduchat.R;
import com.fdu.fduchat.backend.Core;
import com.fdu.fduchat.backend.CoreProvider;
import com.fdu.fduchat.message.BusProvider;
import com.fdu.fduchat.message.SendMessageResult;
import com.fdu.fduchat.model.ExMessage;
import com.fdu.fduchat.model.Message;
import com.fdu.fduchat.model.MessageContent;
import com.fdu.fduchat.model.User;
import com.fdu.fduchat.utils.Constant;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class ChattingActivity extends AppCompatActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChattingAdapter adapter;
    private ArrayList<ExMessage> chatHistory;
    private User user;
    private Core core;
    private String friendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        BusProvider.getBus().register(this);
        core = CoreProvider.getCoreInstance();
        user = (User)core.getCustomData().get(Constant.CUSTOM_DATA_KEY_USER);
        friendName = (String)getIntent().getExtras().get(Constant.INTENT_EXTRA_KEY_FRIEND);
        Map<String, ArrayList<ExMessage> > chatHistories =
                (Map<String, ArrayList<ExMessage> >)core.getCustomData().get(Constant.CUSTOM_DATA_KEY_CHATTINGS);
        if (chatHistories.containsKey(friendName)) {
            chatHistory = (ArrayList<ExMessage>)chatHistories.get(friendName);
        } else {
            chatHistory = new ArrayList<ExMessage>();
            chatHistories.put(friendName, chatHistory);
        }
        initControls();
        loadHistory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getBus().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void receiveMessageHandler(Message message) {
        Log.d(Constant.LOG_TAG, message.toString());
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void sendMessageHandler(SendMessageResult result) {
        Log.d(Constant.LOG_TAG, result.toString());
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ExMessage chatMessage = new ExMessage();
                chatMessage.setIsMe(true);
                chatMessage.setSender(user.getUsername());
                chatMessage.setReceiver(friendName);
                chatMessage.setTimestamp(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setContent(new MessageContent(MessageContent.TEXT_MESSAGE, messageText));
                core.sendMessage(chatMessage);
                messageET.setText("");
                displayMessage(chatMessage);
            }
        });


    }

    public void displayMessage(ExMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadHistory(){

        adapter = new ChattingAdapter(ChattingActivity.this, chatHistory);
        messagesContainer.setAdapter(adapter);

//        for(int i=0; i<chatHistory.size(); i++) {
//            ExMessage message = chatHistory.get(i);
//            displayMessage(message);
//        }

    }

}

