package com.fdu.fduchat.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fdu.fduchat.R;
import com.fdu.fduchat.backend.Core;
import com.fdu.fduchat.model.User;
import com.fdu.fduchat.utils.Constant;

/**
 * Created by HurricaneTong on 15/10/15.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginButton;
    private Button loginButton2;
    private Button signupButton;
    private EditText usernameText;
    private User u1;
    private User u2;
    private EditText messageText;
    private Button sendButton;
    private User currentUser;
    private User anotherUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton2 = (Button)findViewById(R.id.loginButton2);
        signupButton = (Button)findViewById(R.id.signupButton);
        usernameText = (EditText)findViewById(R.id.usernameText);
        messageText = (EditText)findViewById(R.id.messageText);
        sendButton = (Button)findViewById(R.id.sendButton);

        sendButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        loginButton2.setOnClickListener(this);

        u1 = new User();
        u1.username = "005";
        u1.voipAccount = "8002083400000006";
        u1.voidPwd = "kKAzTBbW";
        u1.subToken = "580799ef488221c6996514aa0724cc9a";
        u1.subAccountSid = "1f6eecdd740311e5bb61ac853d9d52fd";

        u2 = new User();
        u2.username = "006";
        u2.voipAccount = "8002083400000007";
        u2.voidPwd = "CghtmfZ3";
        u2.subToken = "bd9dc191a18a1e0604011450b4d60577";
        u2.subAccountSid = "6f942e2a740311e5bb61ac853d9d52fd";



//        currentUser = u2;
//        anotherUser = u1;
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()) {
           case R.id.loginButton:
               Intent intent = new Intent();
               intent.setClass(LoginActivity.this, MainActivity.class);
               LoginActivity.this.startActivity(intent);
               LoginActivity.this.finish();
               currentUser = u1;
               anotherUser = u2;
               break;
           case R.id.loginButton2:
               currentUser = u2;
               anotherUser = u1;
               break;
           case R.id.signupButton:
               User u = new User();
               u.username = usernameText.getText().toString();
               break;
           case R.id.sendButton:
               String msgText = messageText.getText().toString();
               Log.d(Constant.LOG_TAG, "msg content: " + msgText);
               break;
       }
    }
}
