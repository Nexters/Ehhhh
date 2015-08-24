/*
package com.teamnexters.ehhhh.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.teamnexters.ehhhh.R;

*/
/**
 * Created by 현식 on 2015-08-22.
 *//*

public class VersionActivity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_version);

        mContext = this;
        PackageInfo packageInfo = null;

        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String version = packageInfo.versionName;

        TextView version_txt = (TextView) findViewById(R.id.version_txt);
        TextView btn_go_star = (TextView) findViewById(R.id.btn_go_star);

        version_txt.setText(version);

        btn_go_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PackageManager pm = getPackageManager();
                TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                String appPackageName = getPackageName();
                if ("com.android.vending".equals(pm.getInstallerPackageName(appPackageName))) {
                    //Play Store
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                } else {
                    // Play store
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.activity_close_in, R.anim.activity_close_out);
    }

}
*/
