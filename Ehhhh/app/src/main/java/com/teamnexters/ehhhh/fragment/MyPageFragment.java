package com.teamnexters.ehhhh.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.Util.AppPreference;

import java.util.Arrays;
import java.util.List;

/**
 * Created by HyeonSi on 2015-07-22.
 */
public class MyPageFragment extends Fragment {

    private static final String TAG = "MyPageFragement";
    Context mContext;
    ProgressDialog dialog;
    TextView name, mail;

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
        View rootView = inflater.inflate(R.layout.l_fragment_login, container, false);

        rootView.setTag(TAG);

        TextView btn_login = (TextView) rootView.findViewById(R.id.btn_login);
        TextView btn_logout = (TextView) rootView.findViewById(R.id.btn_logout);
        name = (TextView) rootView.findViewById(R.id.name_txt);
        mail = (TextView) rootView.findViewById(R.id.mail_txt);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = ProgressDialog.show(mContext, "", "Logging in....", true);

                List<String> permissions = Arrays.asList("public_profile", "email");

                ParseFacebookUtils.logInWithReadPermissionsInBackground(getActivity(), permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        dialog.dismiss();
                        if (parseUser == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        } else if (parseUser.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Facebook!");
                            getUserData();
                        } else {
                            Log.d("MyApp", "User logged in through Facebook!");
                            getUserData();
                        }
                    }
                });

            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Toast.makeText(mContext, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public void getUserData() {
        ParseUser parseUser;
        parseUser = ParseUser.getCurrentUser();

        AppPreference.saveName(mContext, parseUser.getUsername());
        AppPreference.saveMail(mContext, parseUser.getEmail());
    }

    @Override
    public void onResume() {
        super.onResume();

        name.setText(AppPreference.loadName(mContext));
        mail.setText(AppPreference.loadMail(mContext));
    }
}