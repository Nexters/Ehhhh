package com.teamnexters.ehhhh.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.teamnexters.ehhhh.R;

/**
 * Created by HyeonSi on 2015-07-09.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }
}