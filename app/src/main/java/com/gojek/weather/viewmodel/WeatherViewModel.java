package com.gojek.weather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gojek.weather.service.model.Weather;
import com.gojek.weather.service.repository.WeatherRepository;

import javax.inject.Inject;

public class WeatherViewModel extends AndroidViewModel {
    private static final String TAG = WeatherViewModel.class.getCanonicalName();

    private final LiveData<Weather> weatherObservable;


    @Inject
    public WeatherViewModel(@NonNull WeatherRepository weatherRepository, @NonNull Application application) {
        super(application);

//        weatherObservable = weatherRepository.getWeather();
        weatherObservable = null;

    }

    public LiveData<Weather> getObservableWeather() {
        return weatherObservable;

    }
}
