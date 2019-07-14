package com.gojek.weather.view.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gojek.weather.R;
import com.gojek.weather.adapter.ForecastAdapter;
import com.gojek.weather.databinding.FragmentWeatherBinding;
import com.gojek.weather.helper.WeatherLoadStatus;
import com.gojek.weather.service.model.Forecastday;
import com.gojek.weather.service.model.Weather;
import com.gojek.weather.view.base.BaseFragment;
import com.gojek.weather.viewmodel.WeatherViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WeatherFragment extends BaseFragment<WeatherViewModel> {

    @Inject
    @VisibleForTesting
    ViewModelProvider.Factory factory;


    private View view;

    private RecyclerView rvForecat;
    private ForecastAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Forecastday> forecastdays;
    private TextView tvCurrentCity, tvCurrentTemp;

    private FragmentWeatherBinding weatherBinding;

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
        weatherBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false);


        weatherBinding.setViewModel(viewModel);
        weatherBinding.setFragment(this);
        weatherBinding.setLifecycleOwner(this);

        viewModel.setWeatherLoadStatusLiveData(WeatherLoadStatus.LOADING);

        initView();


        getObservableAndSet();

        getObservaleSetVisibilities(weatherBinding);


        return weatherBinding.getRoot();
    }

    private void initView() {

        rvForecat = weatherBinding.getRoot().findViewById(R.id.rv_forecast);
        tvCurrentCity = weatherBinding.getRoot().findViewById(R.id.tv_current_city);
        tvCurrentTemp = weatherBinding.getRoot().findViewById(R.id.tv_current_temp);

        layoutManager = new LinearLayoutManager(getActivity());
        forecastdays = new ArrayList<>();
        adapter = new ForecastAdapter(forecastdays);
        rvForecat.setLayoutManager(layoutManager);
        rvForecat.setAdapter(adapter);

    }

    private void getObservaleSetVisibilities(final FragmentWeatherBinding weatherBinding) {
        viewModel.getWeatherLoadStatusLiveData().observe(getViewLifecycleOwner(), new Observer<WeatherLoadStatus>() {
            @Override
            public void onChanged(WeatherLoadStatus weatherLoadStatus) {


                weatherBinding.includeLoading.setVisibility(View.GONE);
                weatherBinding.includeWeather.setVisibility(View.GONE);
                weatherBinding.includeError.getRoot().setVisibility(View.GONE);

                switch (weatherLoadStatus) {
                    case LOADING:
                        weatherBinding.includeLoading.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS_LOADED:

                        weatherBinding.includeWeather.setVisibility(View.VISIBLE);
                        break;

                    case FAILURE_LOAD:
                        weatherBinding.includeError.getRoot().setVisibility(View.VISIBLE);

                        break;
                    default:

                }

            }
        });
    }

    private void getObservableAndSet() {
        viewModel.getObservableWeather().observe(getViewLifecycleOwner(), new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                if (weather != null && weather.getCurrent() != null) {

                    viewModel.setWeatherLoadStatusLiveData(WeatherLoadStatus.SUCCESS_LOADED);

                    animateRecyclerView();

                    tvCurrentCity.setText(weather.getLocation().getName());
                    tvCurrentTemp.setText(getString(R.string.string_temp_degree,
                            String.valueOf(weather.getCurrent().getTempC())));

                    forecastdays.addAll(weather.getForecast().getForecastday());
                    adapter.notifyDataSetChanged();

                } else {
                    viewModel.setWeatherLoadStatusLiveData(WeatherLoadStatus.FAILURE_LOAD);

                }

            }
        });
    }

    private void animateRecyclerView() {
        Animation animation;
        animation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.bottom_to_original);
        rvForecat.setAnimation(animation);
    }


    public void onRetryClick(View view) {
        viewModel.setWeatherLoadStatusLiveData(WeatherLoadStatus.LOADING);

        viewModel.getWeather();
        getObservableAndSet();


    }


}
