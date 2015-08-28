package com.teamnexters.ehhhh.activity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.adapter.PubAdapter;
import com.teamnexters.ehhhh.common.PubInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by csk on 2015-07-23.
 * Edit by 슬기 2015-08-25.
 */
public class PubActivity extends AppCompatActivity {

    private static final String ARG_PARAM = "location";
    private ArrayList<String> mAddressList = new ArrayList<String>();
    private ArrayList<String> mPubNameList = new ArrayList<String>();

    private static final String TAG = "PubActivity";
    private static final String KEY_LAYOUT_MANAGER = "layoutmanager";

    protected LayoutManagerType mLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected PubAdapter mAdapter;

    private GoogleMap mMap;
    private Marker mSelectedMarker;
    private HashMap<Marker, Integer> mHashMap = new HashMap<Marker, Integer>();
    private int mMarkerPos;

    ArrayList<PubInfo> pubList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_pub);
        Log.e("TEST", "PubActivity");
        final String location = getIntent().getExtras().getString(ARG_PARAM);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        toolbar.setTitle(location);
        toolbar.setTitleTextColor(getResources().getColor(R.color.cwhite));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_white));
        setSupportActionBar(toolbar);

        // pub list
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        final TextView pubCountTextView = (TextView) findViewById(R.id.activity_pub_count);  //hy.jung

        if (savedInstanceState != null) {
            mLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }

        // Edit by 슬기 2015-08-25 : 서버데이터 로드 추가
        pubList = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("PubInfo");
        query.whereEqualTo("district", location);
        query.orderByAscending("name");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject object : list) {
                        PubInfo pub = new PubInfo();
                        pub.setObjectId(object.getObjectId());
                        pub.setName(object.getString("name"));
                        pub.setNameEng(object.getString("nameEng"));
                        pub.setPhone(object.getString("phone"));
                        pub.setDistrict(object.getString("district"));  //구,구분
                        pub.setAdress(object.getString("adress"));
                        pub.setTime(object.getString("time"));          //영업시간
                        pub.setInfo1(object.getString("info1"));
                        pub.setInfo2(object.getString("info2"));
                        pub.setEtc(object.getString("etc"));
                        // bookmark check
                        ParseUser parseUser = ParseUser.getCurrentUser();
                        String email = null;
                        if (parseUser != null) {
                            email = parseUser.getEmail();
                            ParseQuery<ParseObject> bookmarkQuery = ParseQuery.getQuery("Bookmark");
                            bookmarkQuery.whereEqualTo("email", email);
                            try {
                                for (ParseObject bookmark : bookmarkQuery.find()) {
                                    if (bookmark.get("pubId").equals(object.getObjectId())) {
                                        pub.setBookmarkYN(true);
                                        break;
                                    }
                                }
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                                Log.d("chasksk", "ParseQuery Error: " + e1.getMessage());
                            }
                        }
                        pubList.add(pub);
                    }
                } else {
                    Log.d("chasksk", "ParseQuery Error: " + e.getMessage());
                }

                setRecyclerViewLayoutManager(mLayoutManagerType);

                mAdapter = new PubAdapter(pubList);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                setAddress();
                setPubName();
                // maps
                setUpMapIfNeeded();

                pubCountTextView.setText(String.valueOf(mAdapter.getItemCount()) + "건");    //hy.jung
            }
        });
    }

    private void setAddress() {
        //hy.jung
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            mAddressList.add(i, mAdapter.getAdapterAddress(i));
        }
    }

    private void setPubName() {
        //hy.jung
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            mPubNameList.add(i, mAdapter.getAdapterPubName(i));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        //hy.jung
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        double latitude = 0;
        double longitude = 0;
        LatLngBounds.Builder bounds = new LatLngBounds.Builder();

        LatLng pointer = null;
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                for (int maker = 0; maker < mAdapter.getItemCount(); maker++) {
                    try {
                        addresses = geocoder.getFromLocationName(mAddressList.get(maker), 10);
                        if (addresses.size() > 0) {
                            latitude = addresses.get(0).getLatitude();
                            longitude = addresses.get(0).getLongitude();
                            pointer = new LatLng(latitude, longitude);
                            bounds.include(pointer).build();
                            setUpMap(pointer, mAddressList.get(maker), mPubNameList.get(maker), bounds, maker);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void setUpMap(LatLng loc, String addressName, String pubName, LatLngBounds.Builder bounds1, int pos) {
        //hy.jung
        final LatLngBounds bounds = bounds1.build();
        MarkerOptions markerOpt = new MarkerOptions();
        markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_dim));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mMarkerPos = mHashMap.get(marker);
                markerSelected();
                if (mSelectedMarker != null) {
                    mSelectedMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin_dim));
                }
                mSelectedMarker = marker;
                mSelectedMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin_enable));

                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (mSelectedMarker != null) {
                    mSelectedMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin_dim));
                }
                mSelectedMarker = null;
            }
        });

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if (mAdapter.getItemCount() > 1)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));  //hy.jung
                mMap.setOnCameraChangeListener(null);
            }
        });
        Marker tmp_marker = mMap.addMarker(markerOpt.position(loc).title(pubName));

        if (mAdapter.getItemCount() < 2) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
            tmp_marker.showInfoWindow();
            tmp_marker.setTitle(pubName);
        }
        //tmp_marker.showInfoWindow();
        mHashMap.put(tmp_marker, pos);
    }

    private void markerSelected() {
        //hy.jung
        if (mRecyclerView != null)
            mRecyclerView.smoothScrollToPosition(mMarkerPos);
    }

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
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
}