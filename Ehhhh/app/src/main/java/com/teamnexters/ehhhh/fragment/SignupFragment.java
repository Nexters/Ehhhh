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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.teamnexters.ehhhh.R;

/**
 * Created by 현식 on 2015-08-20.
 * 회원가입 화면
 */
public class SignupFragment extends Fragment {

    private static final String TAG = "SignUpFragement";
    Context mContext;
    EditText name, mail, pass, pass_again;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.l_fragment_signup, container, false);
        rootView.setTag(TAG);

        name = (EditText) rootView.findViewById(R.id.signup_name);
        mail = (EditText) rootView.findViewById(R.id.signup_mail);
        pass = (EditText) rootView.findViewById(R.id.signup_pass);
        pass_again = (EditText) rootView.findViewById(R.id.signup_pass_again);


        //슬기
        // 뒤로가기 버튼
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    LoginFragment fragment = new LoginFragment();
                    transaction.replace(R.id.content_fragment, fragment);
                    transaction.commit();
                    return true;
                } else {
                    return false;
                }
            }
        });

        TextView btn_signup = (TextView) rootView.findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입하기전에 칸 안차면 하나하나 순서대로 토스트 띄워주기 LoginFragment참고하기
                if (!name.getText().toString().equals("")
                        && !mail.getText().toString().equals("")
                        && !pass.getText().toString().equals("")
                        && !pass_again.getText().toString().equals("")
                        && pass.getText().toString().equals(pass_again.getText().toString())) {
                    doSignUp();
                }
            }
        });

        return rootView;
    }


    private void doSignUp() {
        ParseUser user = new ParseUser();
        user.setUsername(name.getText().toString());
        user.setPassword(pass.getText().toString());
        user.setEmail(mail.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(mContext, "가입되었습니다.", Toast.LENGTH_SHORT).show();
                    // edit by 슬기 2015-08-20 : 로그인 화면으로 이동
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    LoginFragment fragment = new LoginFragment();
                    transaction.replace(R.id.content_fragment, fragment);
                    transaction.commit();
                } else {
                    Toast.makeText(mContext, "가입에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
