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

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.teamnexters.ehhhh.R;

import java.util.List;

/**
 * Created by csk on 2015-08-11. 펍 상세화면
 */
public class PubDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_pub_detail);

        final String pubId = getIntent().getExtras().getString("pubId");
        final String name = getIntent().getExtras().getString("name");
        String nameEng = getIntent().getExtras().getString("nameEng");
        final String phone = getIntent().getExtras().getString("phone");
        final String district = getIntent().getExtras().getString("district");//구,구분
        final String adress = getIntent().getExtras().getString("adress");
        String time = getIntent().getExtras().getString("time");//영업시간
        String info1 = getIntent().getExtras().getString("info1");
        String info2 = getIntent().getExtras().getString("info2");
        String etc = getIntent().getExtras().getString("etc");
        boolean bookmark = getIntent().getExtras().getBoolean("bookmark");


        ((TextView) findViewById(R.id.pub_title)).setText(name);
        ((TextView) findViewById(R.id.pub_title_eng)).setText(nameEng);
        ((TextView) findViewById(R.id.pub_info_addr)).setText(adress);
        ((TextView) findViewById(R.id.pub_info_time)).setText(time);

        if (info1 == null || info1.equals(""))
            (findViewById(R.id.pub_summary)).setVisibility(View.GONE);
        else
            ((TextView) findViewById(R.id.pub_summary)).setText(info1);

        if (info2 == null || info2.equals(""))
            (findViewById(R.id.pub_subject)).setVisibility(View.GONE);
        else
            ((TextView) findViewById(R.id.pub_subject)).setText(info2);

        if (etc == null || etc.equals(""))
            (findViewById(R.id.pub_info_etc)).setVisibility(View.GONE);
        else
            ((TextView) findViewById(R.id.pub_info_etc)).setText(etc);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // button
        findViewById(R.id.floatingbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(getApplicationContext(), PubSubMapActivity.class);
                mapIntent.putExtra("pub_address", adress);
                mapIntent.putExtra("pub_name", name);
                startActivity(mapIntent);
            }
        });

        findViewById(R.id.callbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });

        findViewById(R.id.bookmarkbutton).setTag(bookmark);
        findViewById(R.id.bookmarkbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bookmarkYN = v.getTag().equals(true);
                ParseUser parseUser = ParseUser.getCurrentUser();
                if (parseUser == null) {
                    Toast.makeText(getApplicationContext(), "로그인 후 사용하실 수 있습니다.", Toast.LENGTH_SHORT).show();

//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    MyPageFragment fragment = new MyPageFragment();
//                    transaction.replace(R.id.content_fragment, fragment);
//                    transaction.commit();
                } else {
                    String email = parseUser.getEmail();


                    if (v.getTag().equals(true)) {
//                        Toast.makeText(getApplicationContext(), "즐겨찾기 해제되었습니다.", Toast.LENGTH_SHORT).show();
                        //object.deleteEventually();
                        ParseQuery<ParseObject> bookmarkquery = ParseQuery.getQuery("Bookmark");
                        bookmarkquery.whereEqualTo("email", email);
                        bookmarkquery.whereEqualTo("pubId", pubId);
                        bookmarkquery.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if (list.size() != 0) {
                                    list.get(0).deleteInBackground(new DeleteCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                Toast.makeText(getApplicationContext(), "즐겨찾기 해제되었습니다.", Toast.LENGTH_SHORT).show();
                                                ;
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "즐겨찾기에 등록되었습니다.", Toast.LENGTH_SHORT).show();
                        ParseObject object = new ParseObject("Bookmark");
                        object.put("email", email);
                        object.put("pubId", pubId);
                        object.saveInBackground();
                    }
                    v.setTag(!bookmarkYN);
                }
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
                msg.putExtra(Intent.EXTRA_SUBJECT, "[" + appName + "]" + " 오늘은 \"" + name + "\"로 ~!!"); // 앱 이름
                //msg.putExtra(Intent.EXTRA_TITLE, "[펍 이름]"); // 펍 이름
                msg.putExtra(Intent.EXTRA_TEXT, "연락처 : " + phone + "\n주소 : " + adress + "\nhttp://www.naver.com"); // 펍 정보(연락처, 주소, 링크)
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
