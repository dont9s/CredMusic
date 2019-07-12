package com.gojek.weather.helper;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class CurrentLocationListener extends LiveData<Location> implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static CurrentLocationListener instance;

    private GoogleApiClient googleApiClient;


    public static CurrentLocationListener getInstance(Context appContext) {
        if (instance == null) {
            instance = new CurrentLocationListener(appContext);
        }
        return instance;
    }


    private CurrentLocationListener(Context appContext) {
        buildGoogleApiClient(appContext);
    }


    private synchronized void buildGoogleApiClient(Context appContext) {
        googleApiClient = new GoogleApiClient.Builder(appContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    protected void onActive() {
        super.onActive();
        googleApiClient.connect();
    }


    @Override
    protected void onInactive() {

        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,
                    this);
            googleApiClient.disconnect();

        }


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
