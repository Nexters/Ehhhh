package com.teamnexters.ehhhh.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.common.PubInfo;

import java.util.ArrayList;

/**
 * Created by HyeonSi on 2015-07-09.
 */
public class SplashActivity extends Activity {

    public static ArrayList<PubInfo> pubList;
    ImageView splash_img;
    AnimationDrawable splash_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_splash);

        splash_img = (ImageView) findViewById(R.id.splash_img);
        splash_img.setBackgroundResource(R.drawable.splash_animation);
        splash_anim = (AnimationDrawable) splash_img.getBackground();

        splash_anim.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                splash_anim.stop();
                finish();
            }
        }, 2970);
        isNetworkCheck();
    }

    private void isNetworkCheck() {
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifi.isConnected() || mobile.isConnected()) {

        } else {
            Toast.makeText(this, "네트워크가 연결되지 않았습니다. 연결 상태를 확인해주세요.", Toast.LENGTH_LONG).show();
        }
    }
}