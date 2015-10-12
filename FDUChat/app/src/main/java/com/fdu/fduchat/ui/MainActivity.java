package com.fdu.fduchat.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdu.fduchat.R;
import com.litesuits.http.HttpConfig;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.impl.apache.ApacheHttpClient;
import com.litesuits.http.request.FileRequest;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.response.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        TextView tv = new TextView(this);
//        tv.setText("hello");
//        RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl1);
//        rl.addView(tv);
        new Thread(new Runnable() {
            @Override
            public void run() {
                LiteHttp client = new ApacheHttpClient(new HttpConfig(MainActivity.this));
                Response res = client.execute(new FileRequest("http://www.baidu.com"));
                System.out.println(res.toString());
            }
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
