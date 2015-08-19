package com.teamnexters.ehhhh.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
                    Toast.makeText(mContext, "성공!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
