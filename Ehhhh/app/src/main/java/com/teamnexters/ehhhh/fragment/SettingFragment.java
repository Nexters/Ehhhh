/*
package com.teamnexters.ehhhh.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;
import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.activity.PubReport;
import com.teamnexters.ehhhh.activity.VersionActivity;

*/
/**
 * Created by 슬기 on 2015-08-21.
 * 설정화면
 *//*

public class SettingFragment extends Fragment {

    private static final String TAG = "SettingFragement";
    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.l_activity_setting, container, false);
        rootView.setTag(TAG);

        TextView btn_logout = (TextView) rootView.findViewById(R.id.btn_logout);
        TextView setting_version = (TextView) rootView.findViewById(R.id.setting_version);
        TextView setting_about = (TextView) rootView.findViewById(R.id.setting_about);
        TextView setting_report = (TextView) rootView.findViewById(R.id.setting_report);


        // 뒤로가기 버튼
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    MyPageFragment fragment = new MyPageFragment();
                    transaction.replace(R.id.content_fragment, fragment);
                    transaction.commit();
                    return true;
                } else {
                    return false;
                }
            }
        });


        //제보하기버튼
        setting_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, PubReportActivity.class));
            }
        });

        //버전정보 버튼
        setting_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, VersionActivity.class));
            }
        });

        //About us 버튼
        setting_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //로그아웃 버튼
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Toast.makeText(mContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                LoginFragment fragment = new LoginFragment();
                transaction.replace(R.id.content_fragment, fragment);
                transaction.commit();
            }
        });

        return rootView;
    }
}*/
