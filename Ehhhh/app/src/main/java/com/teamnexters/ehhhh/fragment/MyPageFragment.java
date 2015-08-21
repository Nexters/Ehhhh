package com.teamnexters.ehhhh.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseUser;
import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.util.AppPreference;

/**
 * Created by 현식 on 2015-08-20.
 */
public class MyPageFragment extends Fragment {

    private static final String TAG = "MyPageFragement";
    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.l_fragment_page, container, false);
        rootView.setTag(TAG);

        return rootView;
    }



    public void getUserData() {
        ParseUser parseUser;
        parseUser = ParseUser.getCurrentUser();

        AppPreference.saveName(mContext, parseUser.getUsername());
        AppPreference.saveMail(mContext, parseUser.getEmail());
    }
}
