package com.teamnexters.ehhhh.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.adapter.SearchAdapter;
import com.teamnexters.ehhhh.common.GNetworkInfo;
import com.teamnexters.ehhhh.common.PubInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csk on 2015-07-23.
 */
public class SearchActivity extends AppCompatActivity {

    private Context mContext;

    private LayoutManagerType mLayoutManagerType;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchAdapter mAdapter;
    private View progressBar;

    private ArrayList<PubInfo> pubList;

    private static final String KEY_LAYOUT_MANAGER = "layoutmanager";

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_search);
        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("검색");
        toolbar.setTitleTextColor(getResources().getColor(R.color.cwhite));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_white));
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        progressBar = findViewById(R.id.progressBar);

        pubList = new ArrayList<>();

        final EditText keyword_txt = (EditText) findViewById(R.id.edit_search);
        final ImageView search_close = (ImageView) findViewById(R.id.search_close);

        setRecyclerViewLayoutManager(mLayoutManagerType);

        keyword_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 == 0) {
                    search_close.setVisibility(View.INVISIBLE);
                } else {
                    search_close.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        keyword_txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch (i) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        if (GNetworkInfo.IsWifiAvailable(mContext)) {
                            searching(keyword_txt.getText().toString());
                        } else {
                            Toast toast = Toast.makeText(mContext, "네트워크 연결을 확인해 주세요.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        return true;
                }
                return false;
            }
        });

        search_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword_txt.setText("");
            }
        });
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(this);
                mLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
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
        pubList.clear();

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
                    for (ParseObject pubInfo : list) {
                        PubInfo pub = new PubInfo();
                        pub.setObjectId(pubInfo.getObjectId());
                        pub.setName(pubInfo.getString("name"));
                        pub.setNameEng(pubInfo.getString("nameEng"));
                        pub.setPhone(pubInfo.getString("phone"));
                        pub.setDistrict(pubInfo.getString("district"));  //구,구분
                        pub.setAdress(pubInfo.getString("adress"));
                        pub.setTime(pubInfo.getString("time"));          //영업시간
                        pub.setInfo1(pubInfo.getString("info1"));
                        pub.setInfo2(pubInfo.getString("info2"));
                        pub.setEtc(pubInfo.getString("etc"));
                        // bookmark check
                        ParseUser parseUser = ParseUser.getCurrentUser();
                        String email = null;
                        if (parseUser != null) {
                            email = parseUser.getEmail();
                            ParseQuery<ParseObject> bookmarkQuery = ParseQuery.getQuery("Bookmark");
                            bookmarkQuery.whereEqualTo("email", email);
                            try {
                                for (ParseObject bookmark : bookmarkQuery.find()) {
                                    if (bookmark.get("pubId").equals(pubInfo.getObjectId())) {
                                        pub.setBookmarkYN(true);
                                        break;
                                    }
                                }
                            } catch (ParseException e1) {
                                pub.setBookmarkYN(false);
                                e1.printStackTrace();
                                Log.d("ehhhh", "ParseQuery Error: " + e1.getMessage());
                            }
                        }

                        pubList.add(pub);
                    }
                }

                setRecyclerViewLayoutManager(mLayoutManagerType);

                int totalCnt = pubList.size();
                ((TextView) findViewById(R.id.searchResult)).setText("검색 결과 " + totalCnt + "건");
//            if(totalCnt == 0) {
//                rootView.findViewById(R.id.defaultView).setVisibility(View.VISIBLE);
//            }
                if (totalCnt < 5) {
                    findViewById(R.id.layout_all).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.layout_all).setVisibility(View.VISIBLE);
                }

                mAdapter = new SearchAdapter(pubList);
                mRecyclerView.setAdapter(mAdapter);
            }
        });


        findViewById(R.id.layout_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 모두 보기
                v.setVisibility(View.GONE);

                mAdapter.setViewType(true);
                mAdapter.refreshView();
            }
        });
    }

}