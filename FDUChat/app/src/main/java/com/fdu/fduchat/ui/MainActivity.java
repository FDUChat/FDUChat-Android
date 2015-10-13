package com.fdu.fduchat.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdu.fduchat.R;
import com.fdu.fduchat.message.BusProvider;
import com.fdu.fduchat.message.MyMessage;
import com.fdu.fduchat.utils.Constant;
import com.litesuits.http.HttpConfig;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.impl.apache.ApacheHttpClient;
import com.litesuits.http.request.FileRequest;
import com.litesuits.http.response.Response;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tv = new TextView(this);
        tv.setText("hello");
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl1);
        rl.addView(tv);

        new Thread(new Runnable() {
            @Override
            public void run() {
                LiteHttp client = new ApacheHttpClient(new HttpConfig(MainActivity.this));
                Response res = client.execute(new FileRequest("http://www.baidu.com"));
                Log.d(Constant.LOG_TAG, res.toString());
                try {
                    for (Integer i = 0; i < 5; ++i) {
                        Thread.sleep(2000);
                        BusProvider.getBus().post(new MyMessage(i));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

    @Subscribe
    public void updateTextView(MyMessage m) {
        tv.setText(m.i.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getBus().unregister(this);
    }
}
