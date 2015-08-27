package com.teamnexters.ehhhh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.fragment.HomeFragment;
import com.teamnexters.ehhhh.fragment.LocationFragment;
import com.teamnexters.ehhhh.fragment.PageFragment;

/**
 * Created by HyeonSi on 2015-08-06.
 */
public class MainAdapter extends FragmentStatePagerAdapter {

    public MainAdapter(FragmentManager fm) {
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
                return PageFragment.newInstance();
            default:
                return HomeFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public final int[] Icon = new int[]{
            R.drawable.btn_tap_home,
            R.drawable.btn_tap_pin,
            R.drawable.btn_tap_person
    };
}