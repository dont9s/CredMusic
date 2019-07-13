package com.gojek.weather.view.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.gojek.weather.R;
import com.gojek.weather.databinding.FragmentWeatherBinding;
import com.gojek.weather.helper.WeatherLoadStatus;
import com.gojek.weather.service.model.Weather;
import com.gojek.weather.view.base.BaseFragment;
import com.gojek.weather.viewmodel.WeatherViewModel;

import javax.inject.Inject;

public class WeatherFragment extends BaseFragment<WeatherViewModel> {

    @Inject
    ViewModelProvider.Factory factory;


    private View view;


    private WeatherViewModel viewModel;

    public WeatherFragment() {
    }


    @Override
    public WeatherViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(WeatherViewModel.class);
        return viewModel;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentWeatherBinding weatherBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false);


        weatherBinding.setViewModel(viewModel);
        weatherBinding.setLifecycleOwner(this);

        viewModel.setWeatherLoadStatusLiveData(WeatherLoadStatus.LOADING);


        viewModel.getObservableWeather().observe(getViewLifecycleOwner(), new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                if (weather != null && weather.getCurrent() != null) {

                    viewModel.setWeatherLoadStatusLiveData(WeatherLoadStatus.SUCCESS_LOADED);
                    Toast.makeText(getActivity(),
                            "Current Temperature is : " + weather.getCurrent().getTempC(),
                            Toast.LENGTH_LONG).show();
                } else {
                    viewModel.setWeatherLoadStatusLiveData(WeatherLoadStatus.FAILURE_LOAD);
                    Toast.makeText(getActivity(),
                            "Some Error Occured",
                            Toast.LENGTH_LONG).show();
                }

            }
        });


        return weatherBinding.getRoot();
    }


}
