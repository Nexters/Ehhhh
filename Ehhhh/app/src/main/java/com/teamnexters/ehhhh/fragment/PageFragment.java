package com.teamnexters.ehhhh.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseUser;
import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.Util.AppPreference;

/**
 * Created by HyeonSi on 2015-07-22.
 */
public class PageFragment extends Fragment {

    private static final String TAG = "PageFragement";
    Context mContext;

    public static PageFragment newInstance() {
        PageFragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.l_fragment_page, container, false);
        rootView.setTag(TAG);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            MyPageFragment fragment = new MyPageFragment();
            transaction.replace(R.id.content_fragment, fragment);

            transaction.commit();
        } else {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            LoginFragment fragment = new LoginFragment();
            transaction.replace(R.id.content_fragment, fragment);

            transaction.commit();
        }


        return rootView;
    }

    public void changeSignup() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        SignupFragment fragment = new SignupFragment();
        transaction.replace(R.id.content_fragment, fragment);

        transaction.commit();
    }
}