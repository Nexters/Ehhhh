package com.teamnexters.ehhhh.activity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.adapter.PubAdapter;
import com.teamnexters.ehhhh.common.ItemData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by csk on 2015-07-23.
 */
public class PubActivity extends AppCompatActivity {

    private static final String ARG_PARAM = "location";
    private ArrayList<String> mAddressList = new ArrayList<String>();
    private ArrayList<String> mPubNameList = new ArrayList<String>();

    private static final String TAG = "PubFragement";
    private static final String KEY_LAYOUT_MANAGER = "layoutmanager";

    protected LayoutManagerType mLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected PubAdapter mAdapter;

    private GoogleMap mMap;
    private Marker mSelectedMarker;
    private HashMap<Marker, Integer> mHashMap = new HashMap<Marker, Integer>();
    private int mMarkerPos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_pub);

        String location = getIntent().getExtras().getString(ARG_PARAM);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        toolbar.setTitle(location);
        toolbar.setTitleTextColor(getResources().getColor(R.color.cwhite));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_white));
        setSupportActionBar(toolbar);

        // pub list
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }

        ItemData mDataset[] = {new ItemData("네이버후드", "서울특별시 서대문구 창천동 54-24", "02)342-3291"),
                new ItemData("메이드 인 퐁당", "서울특별시 용산구 녹사평대로 222-1", "02)321-0041"),
                new ItemData("크라켄 1호점", "서울특별시 서초구 반포동 512-1", "02)231-1321"),
                new ItemData("옐로우펍", "서울특별시 마포구 독막로9길 41", "02)4345-2342"),
                new ItemData("AweSome", "서울특별시 마포구 독막로3길 20", "02)234-1121"),};

        setRecyclerViewLayoutManager(mLayoutManagerType);

        mAdapter = new PubAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        setAddress();
        setPubName();
        // maps
        setUpMapIfNeeded();
    }

    private void setAddress() {
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            mAddressList.add(i, mAdapter.getAdapterAddress(i));
        }
    }

    private void setPubName() {
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
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
                mMap.setOnCameraChangeListener(null);
            }
        });
        //        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 10));
        Marker tmp_marker = mMap.addMarker(markerOpt.position(loc).title(pubName));
        mHashMap.put(tmp_marker, pos);
    }

    private void markerSelected() {
        Log.e("TEST", "mMarkerPos : " + mMarkerPos + " mRecyclerView.isSelected() : " + mRecyclerView.isSelected());
        mRecyclerView.smoothScrollToPosition(mMarkerPos);

//        mRecyclerView.setSelected(true);
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