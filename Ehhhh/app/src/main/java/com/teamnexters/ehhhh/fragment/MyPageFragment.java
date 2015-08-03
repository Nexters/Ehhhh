package com.teamnexters.ehhhh.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teamnexters.ehhhh.R;

/**
 * Created by HyeonSi on 2015-07-22.
 */
public class MyPageFragment extends Fragment {

    private static final String TAG = "MyPageFragement";
    Context mContext;

    public static MyPageFragment newInstance() {
        MyPageFragment fragment = new MyPageFragment();
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
        View rootView = inflater.inflate(R.layout.l_fragment_mypage, container, false);
        rootView.setTag(TAG);

        TextView btn_login = (TextView) rootView.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "토스트", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}