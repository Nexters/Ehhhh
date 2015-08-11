package com.teamnexters.ehhhh.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.teamnexters.ehhhh.R;

import java.util.List;

/**
 * Created by csk on 2015-08-11. 펍 상세화면
 */
public class PubDetailActivity extends AppCompatActivity {
    private static final String ARG_PARAM1 = "pub_id";
    private static final String ARG_PARAM2 = "pub_id";//"pub_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_detail);

        String id = getIntent().getExtras().getString(ARG_PARAM1);
        String title = getIntent().getExtras().getString(ARG_PARAM2);
        ((TextView) findViewById(R.id.pub_title)).setText(title);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        // button
        findViewById(R.id.floatingbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PubActivity.class);
                intent.putExtra("location", "용산구");//intent.putExtra("location", ???);
                startActivity(intent);
            }
        });

        findViewById(R.id.callbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-1111-2222"));
                startActivity(intent);
            }
        });

        findViewById(R.id.bookmarkbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "즐겨찾기에 등록되었습니다.", Toast.LENGTH_SHORT).show();
                // or 즐겨찾기에서 해제되었습니다.
            }
        });

        findViewById(R.id.sharebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> info = am.getRunningTasks(1);
                ComponentName topActivity = info.get(0).topActivity;
                String packageName = topActivity.getPackageName();
                String appName = null;
                try {
                    appName = (String) getPackageManager().getApplicationLabel(getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                Intent msg = new Intent(Intent.ACTION_SEND);
                msg.addCategory(Intent.CATEGORY_DEFAULT);
                msg.putExtra(Intent.EXTRA_SUBJECT, "[" + appName + "]" + " 오늘은 \"ABC Pub\"로 ~!!"); // 앱 이름
                //msg.putExtra(Intent.EXTRA_TITLE, "[펍 이름]"); // 펍 이름
                msg.putExtra(Intent.EXTRA_TEXT, "연락처 : 010-1234-5678\n주소 : 서울시 용산구 용산1동\nhttp://www.naver.com"); // 펍 정보(연락처, 주소, 링크)
                msg.setType("text/plain");

                startActivity(Intent.createChooser(msg, "공유"));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
