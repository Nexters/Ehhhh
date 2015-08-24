package com.teamnexters.ehhhh.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.teamnexters.ehhhh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csk on 2015-07-23.
 */
public class SearchActivity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        toolbar.setTitle("검색");
        toolbar.setTitleTextColor(getResources().getColor(R.color.cwhite));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_white));
        setSupportActionBar(toolbar);

        final EditText keyword_txt = (EditText) findViewById(R.id.edit_search);
        TextView btn_search = (TextView) findViewById(R.id.btn_search);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searching(keyword_txt.getText().toString());
                Log.e("@@@", "검색!");
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

    public void searching(String keyword) {

        ParseQuery<ParseObject> pubNameSearch = ParseQuery.getQuery("PubInfo");
        pubNameSearch.whereContains("name", keyword);

        ParseQuery<ParseObject> pubDistrictSearch = ParseQuery.getQuery("PubInfo");
        pubDistrictSearch.whereContains("district", keyword);

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(pubNameSearch);
        queries.add(pubDistrictSearch);

        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        mainQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (list.isEmpty()) {
                    //TODO 검색결과가 없으면 이곳으로 들어감. 검색결과 없을때 어떻게 나올건지 구성해야함
                    Log.e("@@@@", "결과 없음");
                } else {
                    int i;
                    for (i = 0; i < list.size(); i++) {
                        ParseObject object = list.get(i);
                        //TODO 여기서 Arraylist를 쓰던지 뭐를 쓰던지 마음대로 정보 받아서 요리하면됨!
                        /**
                         * ex. String name = object.getString("name");
                         *     String district = object.getString("district");
                         *
                         *     이러고 뭐 바로 adapter에 add 하는 방식으로 해도 좋을듯
                         */
                        Log.e("@@@@", "검색결과 " + i + 1 + "번째 이름 : " + object.getString("name"));
                        Log.e("@@@@", "검색결과 " + i + 1 + "번째 지역 : " + object.getString("district"));
                    }
                }
            }
        });


        /*pubSearch.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (list.isEmpty()) {
                    //TODO 검색결과가 없으면 이곳으로 들어감. 검색결과 없을때 어떻게 나올건지 구성해야함
                    Log.e("@@@@", "결과 없음");
                } else {
                    int i;
                    for (i = 0; i < list.size(); i++) {
                        ParseObject object = list.get(i);
                        //TODO 여기서 Arraylist를 쓰던지 뭐를 쓰던지 마음대로 정보 받아서 요리하면됨!
                        *//**
         * ex. String name = object.getString("name");
         *     String district = object.getString("district");
         *
         *     이러고 뭐 바로 adapter에 add 하는 방식으로 해도 좋을듯
         *//*
                        Log.e("@@@@", "검색결과 " + i + 1 + "번째 이름 : " + object.getString("name"));
                        Log.e("@@@@", "검색결과 " + i + 1 + "번째 지역 : " + object.getString("district"));
                    }
                }
            }
        });*/
    }
}