package com.fdu.fduchat.backend;

import android.util.Log;

import com.fdu.fduchat.message.BusProvider;
import com.fdu.fduchat.message.MyWork;
import com.fdu.fduchat.utils.Constant;
import com.squareup.otto.Subscribe;

/**
 * Created by HurricaneTong on 15/10/13.
 */
public class DoWork {

    public DoWork() {
        BusProvider.getBus().register(this);
    }

    @Subscribe
    public void doNewWork(MyWork w) {
        Log.d(Constant.LOG_TAG, w.i.toString());
    }
}
