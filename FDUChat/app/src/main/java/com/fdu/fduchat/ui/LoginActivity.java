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
import android.widget.Toast;

import com.fdu.fduchat.R;
import com.fdu.fduchat.backend.Core;
import com.fdu.fduchat.backend.CoreProvider;
import com.fdu.fduchat.message.BusProvider;
import com.fdu.fduchat.message.CreateUserResult;
import com.fdu.fduchat.message.LoginResult;
import com.fdu.fduchat.model.User;
import com.fdu.fduchat.utils.Constant;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginButton;
    private Button signupButton;
    private EditText usernameText;
    private EditText passwordText;

    private void initView() {

        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        signupButton = (Button)findViewById(R.id.signupButton);
        signupButton.setOnClickListener(this);

        usernameText = (EditText)findViewById(R.id.usernameText);
        passwordText = (EditText)findViewById(R.id.passwordText);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        BusProvider.getBus().register(this);
        CoreProvider.getCoreInstance().init(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
//        BusProvider.getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        BusProvider.getBus().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getBus().unregister(this);
    }

    @Override
    public void onClick(View view) {
        User u = new User();
        u.setUsername(usernameText.getText().toString());
        u.setPassword(passwordText.getText().toString());
        switch (view.getId()) {
            case R.id.loginButton:
                CoreProvider.getCoreInstance().getCustomData().put(Constant.CUSTOM_DATA_KEY_USER, u);
                CoreProvider.getCoreInstance().login(u);
                break;
            case R.id.signupButton:
                CoreProvider.getCoreInstance().getCustomData().put(Constant.CUSTOM_DATA_KEY_USER, u);
                CoreProvider.getCoreInstance().createUser(u);
                break;
        }
    }

    private void navToMainActivity() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(intent);
        LoginActivity.this.finish();
    }

    @Subscribe
    public void createUserHandler(CreateUserResult result) {
        Toast.makeText(LoginActivity.this, result.getContent(), Toast.LENGTH_SHORT).show();
        if (result.isSuccessful()) {
            navToMainActivity();
        }
    }

    @Subscribe
    public void loginHandler(LoginResult result) {
        Toast.makeText(LoginActivity.this, result.getContent(), Toast.LENGTH_SHORT).show();
//        if (result.isSuccessful()) {
            navToMainActivity();
//        }
    }

}
