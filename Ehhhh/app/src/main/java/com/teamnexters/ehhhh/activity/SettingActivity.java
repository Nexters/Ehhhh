package com.teamnexters.ehhhh.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;
import com.teamnexters.ehhhh.R;

/**
 * Created by HyeonSi on 2015-08-24.
 */
public class SettingActivity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_setting);
        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        toolbar.setTitle("설정");
        toolbar.setTitleTextColor(getResources().getColor(R.color.cwhite));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_white));
        setSupportActionBar(toolbar);

        PackageInfo packageInfo = null;

        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String version = packageInfo.versionName;

        TextView btn_logout = (TextView) findViewById(R.id.btn_logout);
        TextView version_txt = (TextView) findViewById(R.id.version_txt);
        TextView btn_report = (TextView) findViewById(R.id.btn_report);

        version_txt.setText(version);

        //로그아웃 버튼
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Toast.makeText(mContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        //제보하기버튼
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, PubReportActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
