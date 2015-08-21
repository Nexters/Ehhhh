package com.teamnexters.ehhhh.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.teamnexters.ehhhh.R;

/**
 * Created by 슬기 on 2015-08-21.
 * 설정화면
 */
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
        View rootView = inflater.inflate(R.layout.l_fragment_setting, container, false);
        rootView.setTag(TAG);

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

        TextView btn_logout = (TextView) rootView.findViewById(R.id.btn_logout);
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

}
