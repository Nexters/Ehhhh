package com.teamnexters.ehhhh.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.Util.AppPreference;

/**
 * Created by HyeonSi on 2015-07-22.
 */
public class MyPageFragment extends Fragment {

    private static final String TAG = "MyPageFragement";
    Context mContext;
    ProgressDialog dialog;
    EditText edit_name, edit_password;

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
        edit_name = (EditText) rootView.findViewById(R.id.login_name);
        edit_password = (EditText) rootView.findViewById(R.id.login_pass);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_name.getText().toString().equals("")) {
                    Toast.makeText(mContext, "사용자 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (edit_password.getText().toString().equals("")) {
                    Toast.makeText(mContext, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                if (!edit_name.getText().toString().equals("") && !edit_password.getText().toString().equals("")) {
                    doLogin();
                }
            }
        });

        return rootView;
    }

    //아직안씀
    public void getUserData() {
        ParseUser parseUser;
        parseUser = ParseUser.getCurrentUser();

        AppPreference.saveName(mContext, parseUser.getUsername());
        AppPreference.saveMail(mContext, parseUser.getEmail());
    }

    //아직안씀
    private void doSignUp() {
        ParseUser user = new ParseUser();
        user.setUsername("SIC");
        user.setPassword("qqqq");
        user.setEmail("user@email.com");

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(mContext, "성공!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void doLogin() {

        ParseUser.logInInBackground(edit_name.getText().toString(), edit_password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {

                } else {
                    Toast.makeText(mContext, "로그인 실패", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}