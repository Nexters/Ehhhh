package com.teamnexters.ehhhh.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.activity.SettingActivity;
import com.teamnexters.ehhhh.adapter.BookmarkAdapter;
import com.teamnexters.ehhhh.common.PubInfo;
import com.teamnexters.ehhhh.util.AppPreference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 현식 on 2015-08-20.
 * edit by 슬기 2015-08-25.
 */
public class MyPageFragment extends Fragment {

    private static final String TAG = "MyPageFragement";
    Context mContext;

    LayoutManagerType mLayoutManagerType;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    BookmarkAdapter mAdapter;

    ArrayList<PubInfo> pubList;

    private static final String KEY_LAYOUT_MANAGER = "layoutmanager";

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    // add by 슬기 2015-08-25
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            PageFragment fragment = new PageFragment();
            transaction.replace(R.id.content_fragment, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.l_fragment_mypage, container, false);
        rootView.setTag(TAG);


        if (savedInstanceState != null) {
            mLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();


        ParseUser parseUser = ParseUser.getCurrentUser();

        AppPreference.saveName(mContext, parseUser.getUsername());
        AppPreference.saveMail(mContext, parseUser.getEmail());



        // 사용자 정보 출력
        ((TextView) rootView.findViewById(R.id.user_name)).setText(parseUser.getUsername());
        ((TextView) rootView.findViewById(R.id.user_mail)).setText(parseUser.getEmail());

        // Edit by 슬기 2015-08-25 : 서버데이터 로드 추가
        getBookmarkInfo(parseUser.getEmail(), rootView);

        // 클릭 이벤트
        Button btn_setting = (Button) rootView.findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 설정화면 이동
                startActivityForResult(new Intent(mContext, SettingActivity.class), 100);
            }
        });

    }



    private void getBookmarkInfo(String email, final View rootView) {
        // 즐겨찾기 리스트
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;


        pubList = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Bookmark");
        query.whereEqualTo("email", email);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    //for 문으로 하나하나 가져오기
                    for (ParseObject object : list) {

                        ParseQuery<ParseObject> query = ParseQuery.getQuery("PubInfo");
                        query.whereEqualTo("objectId", object.getString("pubId"));

                        try {
                            for (ParseObject pubInfo : query.find()) {
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
                                pub.setBookmarkYN(true);

                                pubList.add(pub);
                            }
                        } catch (ParseException e1) {
                            //e1.printStackTrace();
                            Log.d("ehhhh", "ParseQuery Error: " + e1.getMessage());
                        }
                    }
                } else {
                    Log.d("ehhhh", "ParseQuery Error: " + e.getMessage());
                }

                setRecyclerViewLayoutManager(mLayoutManagerType);

                int totalCnt = pubList.size();
                ((TextView) rootView.findViewById(R.id.bookmark_cnt)).setText(totalCnt + "");
                if (totalCnt < 3)
                    rootView.findViewById(R.id.layout_all).setVisibility(View.GONE);


                mAdapter = new BookmarkAdapter(pubList);
                mRecyclerView.setAdapter(mAdapter);

            }

        });


        rootView.findViewById(R.id.layout_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 즐겨찾기 한 펍 모두 보기
                v.setVisibility(View.GONE);

                mAdapter.setViewType(true);
                mRecyclerView.refreshDrawableState();
            }
        });

        rootView.invalidate();
    }


    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }
}