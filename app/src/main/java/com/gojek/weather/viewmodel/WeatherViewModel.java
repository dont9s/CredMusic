package com.gojek.weather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.gojek.weather.helper.Constant;
import com.gojek.weather.service.model.Weather;
import com.gojek.weather.service.repository.WeatherRepository;

import javax.inject.Inject;

public class WeatherViewModel extends ViewModel {
    private static final String TAG = WeatherViewModel.class.getCanonicalName();

    private final LiveData<Weather> weatherObservable;


    @Inject
    public WeatherViewModel(@NonNull WeatherRepository weatherRepository) {

        weatherObservable = weatherRepository.getWeather(Constant.APIXU_API_KEY, "Gurgaon", 5);

    }

    public LiveData<Weather> getObservableWeather() {
        return weatherObservable;

    }
}
