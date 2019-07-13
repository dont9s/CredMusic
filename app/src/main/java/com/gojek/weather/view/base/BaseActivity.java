package com.gojek.weather.view.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<T extends ViewModel> extends DaggerAppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1200;
    private T viewModel;


    /*
     * @return view model instance
     * */
    public abstract T getViewModel();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = viewModel == null ? getViewModel() : viewModel;
    }
}
