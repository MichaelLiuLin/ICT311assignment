package com.bignerdranch.android.myevent.Helper;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by linliu on 12/10/17.
 */


//every time start the service, connecting to the google play service, once update the GPS data, stop the connection and send the data to UI.
public class GPSService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = "GPS";
    private String defaultUploadWebsite;
    private boolean currentlyProcessingLocation = false;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        //defaultUploadWebsite = getString(R.string.default_upload_website);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }


    // main method
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // if we are currently trying to get a location and the alarm manager has called this again,
        // no need to start processing a new location.
        if (!currentlyProcessingLocation) {
            currentlyProcessingLocation = true;

            startTracking();
        }
        // when memory is low, the service will be recreated.
        return START_NOT_STICKY;
    }

    private void startTracking() {
        Log.i(TAG, "startTracking");

        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this) //current activity will handle the connection
                    .addOnConnectionFailedListener(this)
                    .build();

            if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
                //connect to the google play service
                googleApiClient.connect();
            }
        } else {
            Log.i(TAG, "unable to connect to google play services.");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //after connect(), this method will be invoked.
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected");

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000); // milliseconds
        locationRequest.setFastestInterval(1000); // the fastest rate in milliseconds at which your app can handle location updates
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Log.e(TAG,"check permission");
            return;
        }

        //get last known location is for the first time
 //       Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        if(location ==null){
//            //no last location existing
//            //invoke on location change method
//            LocationServices.FusedLocationApi.requestLocationUpdates(
//                    googleApiClient, locationRequest, this);
//            Log.i(TAG,"last location is  null");
//
//        }else{
//            //location is not null meaning there is las location existing.
//            Log.i(TAG,"there is an existing last known locastion");
//            fn_update(location);
//            Log.w(TAG, "position: " + location.getLatitude() + ", " + location.getLongitude() + " accuracy: " + location.getAccuracy());
//        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
        Log.i(TAG,"last location is  null");

    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "GoogleApiClient connection has been suspend");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed");

        stopLocationUpdates();
        stopSelf();
    }

    @Override
    public void onLocationChanged(Location location) {
        //once changed location the GPS service will send broadcast to UI.
        fn_update(location);
        //fn_update2(location);
        Log.i(TAG,"location is changed.");
    }

    private void fn_update(Location location){
        Intent i = new Intent("1");
        i.putExtra("latitude",""+location.getLatitude());
        i.putExtra("longitude",""+location.getLongitude());
        //Log.e(TAG, "broadcast position to button: " + location.getLatitude() + ", " + location.getLongitude());
        sendBroadcast(i);
    }
//    private void fn_update2(Location location){
//        Intent i = new Intent("2");
//        i.putExtra("latitude",""+location.getLatitude());
//        i.putExtra("longitude",""+location.getLongitude());
//        Log.e(TAG, "broadcast position to map: " + location.getLatitude() + ", " + location.getLongitude());
//        sendBroadcast(i);
//    }

    private void stopLocationUpdates() {
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
            Log.i(TAG,"GPS is disconnected.");
        }
    }
}
