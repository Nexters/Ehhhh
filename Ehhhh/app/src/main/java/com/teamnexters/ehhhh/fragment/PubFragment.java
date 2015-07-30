package com.teamnexters.ehhhh.fragment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamnexters.ehhhh.R;

/**
 * Created by csk on 2015-07-23.
 */
public class PubFragment extends AppCompatActivity {
    private static final String ARG_PARAM = "location";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pub);

        String location = getIntent().getExtras().getString(ARG_PARAM);
        Toast.makeText(this, location, Toast.LENGTH_SHORT).show();


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        // maps
        setUpMapIfNeeded();

        // pub list
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
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

        mAdapter = new PubAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }


    private GoogleMap mMap;

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }


    private static final String TAG = "PubFragement";
    private static final String KEY_LAYOUT_MANAGER = "layoutmanager";

    protected LayoutManagerType mLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected PubAdapter mAdapter;

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    public class ItemData {
        private String pubName;
        private String address;
        private String phone;

        public ItemData(String pubName, String address, String phone) {
            this.pubName = pubName;
            this.address = address;
            this.phone = phone;
        }

        // getters & setters
        public String getPubName() {
            return pubName;
        }

        public void setPubName(String pubName) {
            this.pubName = pubName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
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
