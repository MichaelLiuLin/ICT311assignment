package com.bignerdranch.android.myevent.ControllerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.bignerdranch.android.myevent.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by linliu on 18/10/17.
 */


public class ThirdActivity_FirstFragment extends SupportMapFragment{

    private GoogleMap mMap;
    private String mLatitude;
    private String mLongitude;
    private BroadcastReceiver mBroadcastReceiver;

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("1"));
    }

    @Override
    public void onPause() {
        super.onPause();
        //getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    private void fn_recevice() {
        Intent intent=getActivity().getIntent();
        mLatitude= intent.getStringExtra("latitude");
        mLongitude=intent.getStringExtra("longitude");
    }
    private void fn_receive2() {
    mBroadcastReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            mLatitude=intent.getStringExtra("latitude");
            mLongitude=intent.getStringExtra("longitude");

        }
    };
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        fn_recevice();
        //fn_receive2();


        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                Log.e("GPS","receiving "+mLatitude+mLongitude);
                updateUI();
            }
        });
    }


    private void updateUI() {
        if(mLatitude==null){
            Log.e("GPS","latitude is null");
        }else {
            Log.e("GPS","test map marker");
            LatLng sunshine = new LatLng(Double.parseDouble(mLatitude), Double.parseDouble(mLongitude));
            mMap.addMarker(new MarkerOptions().position(sunshine).title("Marker in the Sunshine Coast"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sunshine));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sunshine, 15));
            //Update the UI Map View using the overlay Polyline to draw route path

        }
    }
}
