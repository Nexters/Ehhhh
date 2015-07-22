package com.teamnexters.ehhhh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamnexters.ehhhh.activity.SplashActivity;
import com.teamnexters.ehhhh.fragment.HomeFragment;
import com.teamnexters.ehhhh.fragment.LocationFragment;
import com.teamnexters.ehhhh.fragment.MyPageFragment;

import java.util.Locale;

/**
 * Created by HyeonSi on 2015-07-09.
 * Edit by csk 2015-07.21.
 */
public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
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
            final ActionBar actionBar = getSupportActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);

            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    actionBar.setSelectedNavigationItem(position);
                }
            });

            for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
                //actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
                actionBar.addTab(actionBar.newTab().setIcon(mSectionsPagerAdapter.Icon[i]).setTabListener(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return HomeFragment.newInstance();
                case 1:
                    return LocationFragment.newInstance();
                case 2:
                    return MyPageFragment.newInstance();
            }
            return MyPageFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 3;
        }

        final int[] Icon = new int[] {
                R.drawable.ic_person,
                R.drawable.ic_person,
                R.drawable.ic_person
        };
    }
}