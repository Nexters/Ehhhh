package com.teamnexters.ehhhh.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

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
    }
}