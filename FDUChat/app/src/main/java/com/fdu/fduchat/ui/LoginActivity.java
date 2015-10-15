package com.fdu.fduchat.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.fdu.fduchat.R;
import com.fdu.fduchat.backend.Core;

/**
 * Created by HurricaneTong on 15/10/15.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()) {
           case R.id.loginButton:
               Intent intent = new Intent();
               intent.setClass(LoginActivity.this, MainActivity.class);
               LoginActivity.this.startActivity(intent);
               LoginActivity.this.finish();
               Core.initIMSDK(LoginActivity.this);
               break;
       }
    }
}
