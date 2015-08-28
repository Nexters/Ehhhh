package com.teamnexters.ehhhh.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.teamnexters.ehhhh.R;

public class PubReportActivity extends AppCompatActivity {

    Context mContext;
    EditText report_name, report_eng_name, report_location, report_call, report_time, report_info;
    TextView btn_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_report);
        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("제보하기");
        toolbar.setTitleTextColor(getResources().getColor(R.color.cwhite));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_white));
        setSupportActionBar(toolbar);

        report_name = (EditText) findViewById(R.id.report_name);
        report_eng_name = (EditText) findViewById(R.id.report_eng_name);
        report_location = (EditText) findViewById(R.id.report_location);
        report_call = (EditText) findViewById(R.id.report_call);
        report_time = (EditText) findViewById(R.id.report_time);
        report_info = (EditText) findViewById(R.id.report_info);

        btn_report = (TextView) findViewById(R.id.btn_report);

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!report_name.getText().toString().equals("")
                        && !report_eng_name.getText().toString().equals("")
                        && !report_location.getText().toString().equals("")) {
                    reportPub();
                } else {
                    Toast.makeText(mContext, "펍 이름과 영어이름, 주소는 필수 항목입니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void reportPub() {
        ParseObject reportObject = new ParseObject("Report");
        reportObject.put("name", report_name.getText().toString());
        reportObject.put("eng_name", report_eng_name.getText().toString());
        reportObject.put("location", report_location.getText().toString());
        reportObject.put("call", report_call.getText().toString());
        reportObject.put("time", report_time.getText().toString());
        reportObject.put("info", report_info.getText().toString());
        reportObject.saveInBackground();

        Toast.makeText(mContext, "펍 제보가 완료되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}