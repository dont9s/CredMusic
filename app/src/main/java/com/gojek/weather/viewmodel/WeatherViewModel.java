package com.gojek.weather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gojek.weather.helper.Constant;
import com.gojek.weather.helper.PrefManager;
import com.gojek.weather.helper.WeatherLoadStatus;
import com.gojek.weather.service.model.Weather;
import com.gojek.weather.service.repository.WeatherRepository;

import javax.inject.Inject;

public class WeatherViewModel extends ViewModel {
    private static final String TAG = WeatherViewModel.class.getCanonicalName();

    private final LiveData<Weather> weatherObservable;

    private MutableLiveData<WeatherLoadStatus> weatherLoadStatusLiveData;


    @Inject
    public WeatherViewModel(@NonNull WeatherRepository weatherRepository, PrefManager prefManager) {

        String q = "";
        if (prefManager.getFloat(Constant.LATITUDE, 0) == 0 || prefManager.getFloat(Constant.LONGITUDE, 0) == 0) {
            q = "gurgaon";
        } else
            q = prefManager.getFloat(Constant.LATITUDE, 0) + "," + prefManager.getFloat(Constant.LONGITUDE, 0);


        weatherObservable = weatherRepository.getWeather(Constant.APIXU_API_KEY,
                q,
                5);

    }

    public LiveData<Weather> getObservableWeather() {
        return weatherObservable;

    }

    public void setWeatherLoadStatusLiveData(WeatherLoadStatus weatherLoadStatus) {
        if (weatherLoadStatusLiveData == null)
            weatherLoadStatusLiveData = new MutableLiveData<>();


        weatherLoadStatusLiveData.setValue(weatherLoadStatus);

    }

    public LiveData<WeatherLoadStatus> getWeatherLoadStatusLiveData() {
        return weatherLoadStatusLiveData;
    }
}
