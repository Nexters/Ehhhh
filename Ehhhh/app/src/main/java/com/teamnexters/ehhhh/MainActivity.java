package com.teamnexters.ehhhh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.parse.ParseFacebookUtils;
import com.teamnexters.ehhhh.activity.SearchActivity;
import com.teamnexters.ehhhh.activity.SplashActivity;
import com.teamnexters.ehhhh.adapter.MainAdapter;

/**
 * Created by HyeonSi on 2015-07-09.
 * Edit by HyeienSi 2015-08-06.
 */
public class MainActivity extends AppCompatActivity {

    MainAdapter mAdapter;
    ViewPager mViewPager;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        // 스플래쉬
        startActivity(new Intent(mContext, SplashActivity.class));

        try {
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

            mAdapter = new MainAdapter(getSupportFragmentManager());

            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mAdapter);
            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            for (int i = 0; i < mAdapter.getCount(); i++) {
                tabLayout.addTab(tabLayout.newTab().setIcon(mAdapter.Icon[i]));
            }
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mViewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

            findViewById(R.id.floatingbutton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, SearchActivity.class));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }
}