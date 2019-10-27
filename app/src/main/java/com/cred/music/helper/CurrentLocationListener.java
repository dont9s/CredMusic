package com.cred.music.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.cred.music.view.ui.MainActivity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class CurrentLocationListener extends LiveData<Location> {

    private static CurrentLocationListener instance;


    private final static long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private final static long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    public final static int REQUEST_CHECK_SETTINGS = 2000;

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;

    private LocationCallback locationCallback;

    private Context appContext;
    /**
     * Represents a geographical location.
     */
    private Location mLastLocation;


    public static CurrentLocationListener getInstance(Context appContext) {
        if (instance == null) {
            instance = new CurrentLocationListener(appContext);
        }
        return instance;
    }


    private CurrentLocationListener(Context appContext) {
        this.appContext = appContext;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(appContext);
        settingsClient = LocationServices.getSettingsClient(appContext);
    }


    /*
  create callback for receiving locations events
   */
    private void createLocationCallback() {

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    setValue(null);
                    return;
                }
               /* for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
//                    mLastLocation = location;
                }*/
                mLastLocation = locationResult.getLastLocation();

                if (mLastLocation != null) {
                    stopLocationUpdates();
                    setValue(mLastLocation);
                }
            }
        };
    }


    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    /*
  for checking if device has needed location settings
  */
    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();
    }


    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        mFusedLocationClient.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED: {
                                try {
                                    ResolvableApiException rae = ((ResolvableApiException) e);
                                    rae.startResolutionForResult(((MainActivity) appContext), REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.e("sendIntentException", sie != null ? sie.getLocalizedMessage() : "error");
                                }
                            }
                            break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE: {
                            }
                            break;

                            default:
                                break;
                        }
                    }
                });
    }

    private void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        mFusedLocationClient.removeLocationUpdates(locationCallback)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }


    @SuppressLint("MissingPermission")
    public Location getLocation() {
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();
        startLocationUpdates();


        return mLastLocation;
    }

    @Override
    protected void onActive() {
        super.onActive();
        getLocation();

    }


    @Override
    protected void onInactive() {
        stopLocationUpdates();
    }
}
