package com.gojek.weather.view.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.gojek.weather.R;
import com.gojek.weather.databinding.ActivityMainBinding;
import com.gojek.weather.helper.Constant;
import com.gojek.weather.helper.CurrentLocationListener;
import com.gojek.weather.helper.PermissionManager;
import com.gojek.weather.helper.PermissionStatus;
import com.gojek.weather.helper.PrefManager;
import com.gojek.weather.view.base.BaseActivity;
import com.gojek.weather.view.ui.weather.WeatherFragment;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainActivityViewModel> {

    private ActivityMainBinding binding;

    @Inject
    MainActivityViewModel viewModel;

    @Inject
    PrefManager prefManager;


    @Override
    public MainActivityViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();


        viewModel.setPersmissionStatus(PermissionManager.getPermissionStatus(this,
                Manifest.permission.ACCESS_FINE_LOCATION));


        viewModel.getPermissionStatus().observe(this, new Observer<PermissionStatus>() {
            @Override
            public void onChanged(PermissionStatus permissionStatus) {
                if (permissionStatus != null) {
                    switch (permissionStatus) {
                        case CAN_ASK_PERMISSION:

                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    BaseActivity.MY_PERMISSIONS_REQUEST_LOCATION);

                            break;
                        case PERMISSION_GRANTED:
                            fetchLocation();
                            break;
                        case PERMISSION_DENIED:
                            //open settings
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.parse("package:" + getApplicationContext().getPackageName());
                            intent.setData(uri);
                            startActivity(intent);

                            break;
                        default:
                            fetchLocation();
                            break;
                    }
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                viewModel.setPersmissionStatus(PermissionManager.getPermissionStatus(this,
                        Manifest.permission.ACCESS_FINE_LOCATION));
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    private void fetchLocation() {
        CurrentLocationListener.getInstance(MainActivity.this).observe(MainActivity.this,
                new Observer<Location>() {
                    @Override
                    public void onChanged(Location location) {
                        Toast.makeText(MainActivity.this, "Longitude : " + location.getLongitude() +
                                " Latitude : " + location.getLatitude(), Toast.LENGTH_LONG).show();


                        prefManager.saveFloat(Constant.LATITUDE, ((long) location.getLatitude()));
                        prefManager.saveFloat(Constant.LONGITUDE, ((long) location.getLongitude()));


                        loadFragment(new WeatherFragment());
                    }
                });
    }

    public void loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();

        }

    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    }
}
