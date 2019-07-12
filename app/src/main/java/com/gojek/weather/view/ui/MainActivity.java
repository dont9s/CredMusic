package com.gojek.weather.view.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.gojek.weather.R;
import com.gojek.weather.databinding.ActivityMainBinding;
import com.gojek.weather.view.base.BaseActivity;
import com.gojek.weather.view.ui.weather.WeatherFragment;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainActivityViewModel> {

    private ActivityMainBinding binding;

    @Inject
    MainActivityViewModel viewModel;


    @Override
    public MainActivityViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();

        loadFragment(new WeatherFragment());
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
