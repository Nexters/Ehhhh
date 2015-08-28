package com.teamnexters.ehhhh.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
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
import com.teamnexters.ehhhh.common.GNetworkInfo;

/**
 * Created by 현식 on 2015-08-20.
 * 로그인화면
 */
public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragement";
    private Context mContext;
    private EditText edit_name, edit_password;
    private View progressBar;

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
        TextView btn_signup = (TextView) rootView.findViewById(R.id.btn_signup);
        edit_name = (EditText) rootView.findViewById(R.id.login_name);
        edit_password = (EditText) rootView.findViewById(R.id.login_pass);
        progressBar = rootView.findViewById(R.id.progressBar);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_name.getText().toString().equals("")) {
                    Toast.makeText(mContext, "사용자 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (edit_password.getText().toString().equals("")) {
                    Toast.makeText(mContext, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                if (!edit_name.getText().toString().equals("") && !edit_password.getText().toString().equals("")) {
                    if (GNetworkInfo.IsWifiAvailable(getActivity())) {
                        doLogin();
                    } else {
                        Toast toast = Toast.makeText(getActivity(), "네트워크 연결을 확인해 주세요.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // edit by 슬기 2015-08-20
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                SignupFragment fragment = new SignupFragment();
                transaction.replace(R.id.content_fragment, fragment);
                transaction.commit();
            }
        });

        return rootView;
    }


    private void doLogin() {
        progressBar.setVisibility(View.VISIBLE);

        ParseUser.logInInBackground(edit_name.getText().toString(), edit_password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                progressBar.setVisibility(View.GONE);

                if (parseUser != null) {
                    //슬기
                    Toast.makeText(mContext, "Welcome!!", Toast.LENGTH_SHORT).show();

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    MyPageFragment fragment = new MyPageFragment();
                    transaction.replace(R.id.content_fragment, fragment);
                    transaction.commit();
                } else {
                    Toast.makeText(mContext, "로그인 실패", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}