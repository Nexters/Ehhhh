package com.teamnexters.ehhhh.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.adapter.LocationAdapter;
import com.teamnexters.ehhhh.common.PubInfo;
import com.teamnexters.ehhhh.common.Recommend;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends Fragment {

    private static final String TAG = "HomeFragement";
    private static final String KEY_LAYOUT_MANAGER = "layoutmanager";

    protected LayoutManagerType mLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LocationAdapter mAdapter;

    ArrayList<PubInfo> pubList;

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    public static Fragment newInstance() {
        LocationFragment locationFragment = new LocationFragment();
        Bundle bundle = new Bundle();
        locationFragment.setArguments(bundle);

        return locationFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.l_fragment_recyclerview, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }

        // Edit by 슬기 2015-08-25 : 서버데이터 로드 추가
        pubList = new ArrayList<>();

        final PubInfo[] comparePub = {null};

        ParseQuery<ParseObject> query = ParseQuery.getQuery("PubInfo");
        query.orderByAscending("district");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject object : list) {

                        if (comparePub[0] == null) {
                            comparePub[0] = new PubInfo();
                            comparePub[0].setDistrict(object.getString("district"));  //구,구분
                            comparePub[0].setCount(1);
                        } else if (comparePub[0].getDistrict().equals(object.getString("district"))) {
                            int count = comparePub[0].getCount();
                            comparePub[0].setCount(count + 1);
                        } else {
                            pubList.add(comparePub[0]);
                            comparePub[0] = new PubInfo();
                            comparePub[0].setDistrict(object.getString("district"));  //구,구분
                            comparePub[0].setCount(1);
                        }
                    }
                    pubList.add(comparePub[0]);
                } else {
                    Log.d("chasksk", "ParseQuery Error: " + e.getMessage());
                }

                setRecyclerViewLayoutManager(mLayoutManagerType);

                mAdapter = new LocationAdapter(pubList);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            }
        });




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


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }
}