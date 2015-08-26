package com.teamnexters.ehhhh.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.parse.ParseUser;
import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.adapter.HomeAdapter;
import com.teamnexters.ehhhh.common.PubInfo;
import com.teamnexters.ehhhh.common.Recommend;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragement";
    private static final String KEY_LAYOUT_MANAGER = "layoutmanager";

    protected LayoutManagerType mLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected HomeAdapter mAdapter;

    ArrayList<Recommend> recommendList;//슬기 2015-08-25


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.l_fragment_recyclerview, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }



        // Edit by 슬기 2015-08-25 : 서버데이터 로드 추가
        recommendList = new ArrayList<>();
        final Recommend[] recommend = {null};

//        ParseUser parseUser = ParseUser.getCurrentUser();
//        String email = null;
//        if(parseUser != null)
//            email = parseUser.getEmail();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Recommend");
        query.findInBackground(new FindCallback<ParseObject>() {
           @Override
           public void done(List<ParseObject> list, ParseException e) {
               if (e == null) {

                   for (ParseObject object : list) {
                       String pubId = object.getString("pubId");

                       recommend[0] = new Recommend();
                       recommend[0].setObjectId(object.getObjectId());
                       recommend[0].setSubject(object.getString("subject"));
                       recommend[0].setPubId(pubId);
                       ParseQuery<ParseObject> subQuery = ParseQuery.getQuery("PubInfo");
                       subQuery.whereEqualTo("objectId", pubId);
                       try {
                           for (ParseObject subObject : subQuery.find()) {
                               recommend[0].pub.setObjectId(subObject.getString("name"));
                               recommend[0].pub.setName(subObject.getString("name"));
                               recommend[0].pub.setNameEng(subObject.getString("nameEng"));
                               recommend[0].pub.setPhone(subObject.getString("phone"));
                               recommend[0].pub.setDistrict(subObject.getString("district"));  //구,구분
                               recommend[0].pub.setAdress(subObject.getString("adress"));
                               recommend[0].pub.setTime(subObject.getString("time"));          //영업시간
                               recommend[0].pub.setInfo1(subObject.getString("info1"));
                               recommend[0].pub.setInfo2(subObject.getString("info2"));
                               recommend[0].pub.setEtc(subObject.getString("etc"));

                               // bookmark check
                               ParseUser parseUser = ParseUser.getCurrentUser();
                               String email = null;
                               if(parseUser != null) {
                                   email = parseUser.getEmail();
                                   ParseQuery<ParseObject> bookmarkQuery = ParseQuery.getQuery("Bookmark");
                                   bookmarkQuery.whereEqualTo("email", email);
                                   try {
                                       for(ParseObject bookmark : bookmarkQuery.find()) {
                                           if(bookmark.get("pubId").equals(pubId)) {
                                               recommend[0].pub.setBookmarkYN(true);
                                               break;
                                           }
                                       }
                                   } catch (ParseException e1) {
                                       //e1.printStackTrace();
                                       Log.d("chasksk", "ParseQuery Error: " + e1.getMessage());
                                   }
                               }
                           }

                           recommendList.add(recommend[0]);
                       } catch (ParseException e1) {
                           //e1.printStackTrace();
                           Log.d("chasksk", "ParseQuery Error: " + e1.getMessage());
                       }
                   }

               } else {
                   Log.d("chasksk", "ParseQuery Error: " + e.getMessage());
               }
               setRecyclerViewLayoutManager(mLayoutManagerType);

               mAdapter = new HomeAdapter(recommendList);
               mRecyclerView.setAdapter(mAdapter);
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