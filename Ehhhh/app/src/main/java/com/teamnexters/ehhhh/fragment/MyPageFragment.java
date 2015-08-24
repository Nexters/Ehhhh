package com.teamnexters.ehhhh.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;
import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.activity.SettingActivity;
import com.teamnexters.ehhhh.adapter.BookmarkAdapter;
import com.teamnexters.ehhhh.common.ItemData;
import com.teamnexters.ehhhh.util.AppPreference;

/**
 * Created by 현식 on 2015-08-20.
 * edit by 슬기
 */
public class MyPageFragment extends Fragment {

    private static final String TAG = "MyPageFragement";
    Context mContext;

    LayoutManagerType mLayoutManagerType;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    BookmarkAdapter mAdapter;

    private static final String KEY_LAYOUT_MANAGER = "layoutmanager";

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.l_fragment_mypage, container, false);
        rootView.setTag(TAG);

        final ParseUser parseUser;
        parseUser = ParseUser.getCurrentUser();

        AppPreference.saveName(mContext, parseUser.getUsername());
        AppPreference.saveMail(mContext, parseUser.getEmail());

        // 즐겨찾기 리스트
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }

        ItemData mDataset[] = {new ItemData("네이버후드", "서울특별시 서대문구 창천동 54-24", "02)342-3291"),
                new ItemData("메이드 인 퐁당", "서울특별시 용산구 녹사평대로 76-12", "02)321-0041"),
                new ItemData("크라켄 1호점", "서울특별시 서초구 반포동 512-1", "02)231-1321"),
                new ItemData("옐로우펍", "서울특별시 마포구 독막로9길 41", "02)4345-2342"),
                new ItemData("AweSome", "서울특별시 마포구 독막로3길 20", "02)234-1121"),};

        setRecyclerViewLayoutManager(mLayoutManagerType);

        mAdapter = new BookmarkAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);


        // 클릭 이벤트
        Button btn_setting = (Button) rootView.findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 설정화면 이동
                /*FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                SettingFragment fragment = new SettingFragment();
                transaction.replace(R.id.content_fragment, fragment);
                transaction.commit();*/
                startActivity(new Intent(mContext, SettingActivity.class));
            }
        });

        rootView.findViewById(R.id.layout_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 즐겨찾기 한 펍 모두 보기
                v.setVisibility(View.GONE);

                //mRecyclerView.setScrollingTouchSlop();
                mAdapter.setViewType(true);
                //mRecyclerView.notify();
                mRecyclerView.refreshDrawableState();
            }
        });

        // 사용자 정보 출력
        ((TextView) rootView.findViewById(R.id.user_name)).setText(parseUser.getUsername());
        ((TextView) rootView.findViewById(R.id.user_mail)).setText(parseUser.getEmail());
        ((TextView) rootView.findViewById(R.id.bookmark_cnt)).setText(mDataset.length + "");

        return rootView;
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