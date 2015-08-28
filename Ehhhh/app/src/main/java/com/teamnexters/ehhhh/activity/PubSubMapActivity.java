package com.teamnexters.ehhhh.activity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamnexters.ehhhh.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by hy on 2015-08-17.
 */
public class PubSubMapActivity extends FragmentActivity {

    private String intent_address = "pub_address";
    private String intent_name = "pub_name";
    private GoogleMap mSubMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_sub_map);

        String pubAddress = getIntent().getExtras().getString(intent_address);
        String pubName = getIntent().getExtras().getString(intent_name);
        Log.e("TEST", "pubAddress : " + pubAddress);
        Log.e("TEST", "pubName : " + pubName);
        setMap(pubAddress, pubName);
    }

    private void setMap(String address, String name) {
        LatLng loc = null;

        double lat = 0;
        double lng = 0;

        Toast.makeText(this, name.toString(), Toast.LENGTH_SHORT).show();

        MarkerOptions markerOpt = new MarkerOptions();
        markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_enable));

        mSubMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.sub_map)).getMap();

        Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address> bAddress = null;

        if (mSubMap != null) {
            try {
                bAddress = geocoder.getFromLocationName(address, 1);

                if (bAddress.size() > 0) {
                    lat = bAddress.get(0).getLatitude();
                    lng = bAddress.get(0).getLongitude();
                    loc = new LatLng(lat, lng);

                    mSubMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16));
                    mSubMap.addMarker(markerOpt.position(loc).title(name)).showInfoWindow();
//                    mSubMap.addMarker(new MarkerOptions().position(loc));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}