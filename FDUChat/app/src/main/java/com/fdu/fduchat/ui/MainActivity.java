package com.fdu.fduchat.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.fdu.fduchat.R;
import com.fdu.fduchat.message.BusProvider;
import com.fdu.fduchat.message.MyMessage;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initView();
//        tv = new TextView(this);
//        tv.setText("hello");
//        RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl1);
//        rl.addView(tv);
//
//        doWork = new DoWork();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                LiteHttp client = new ApacheHttpClient(new HttpConfig(MainActivity.this));
//                Response res = client.execute(new FileRequest("http://www.baidu.com"));
//                Log.d(Constant.LOG_TAG, res.toString());
//                try {
//                    for (Integer i = 0; i < 5; ++i) {
//                        Thread.sleep(2000);
//                        BusProvider.getBus().post(new MyMessage(i));
//                        BusProvider.getBus().post(new MyWork(i));
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }

    private FragmentTabHost mTabHost;

    private LayoutInflater layoutInflater;

    private Class fragmentClasses[] = {
            ChatFragment.class,
            FriendFragment.class,
            DiscoverFragment.class,
            MeFragment.class
    };

    private String fragmentNames[] = {"Chat", "Friend", "Discover", "Me"};

    private void initView(){
        layoutInflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        int count = fragmentClasses.length;

        for(int i = 0; i < count; i++){
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(fragmentNames[i]).setIndicator(fragmentNames[i]);
            mTabHost.addTab(tabSpec, fragmentClasses[i], null);
        }
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
