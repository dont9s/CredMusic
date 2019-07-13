package com.gojek.weather.viewmodel;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
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

    private LiveData<Weather> weatherObservable;

    private MutableLiveData<WeatherLoadStatus> weatherLoadStatusLiveData;

    private PrefManager prefManager;
    private WeatherRepository weatherRepository;


    @Inject
    public WeatherViewModel(@NonNull WeatherRepository weatherRepository, PrefManager prefManager) {

        this.prefManager = prefManager;
        this.weatherRepository = weatherRepository;

        getWeather();

    }

    public void getWeather() {
        String q = "Gurgaon";

        if (!(prefManager.getFloat(Constant.LATITUDE, 0) == 0 || prefManager.getFloat(Constant.LONGITUDE, 0) == 0))
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
