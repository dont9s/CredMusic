package com.gojek.weather.service.repository;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gojek.weather.service.model.Weather;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class WeatherRepository {
    private ApixuService service;


    @Inject
    public WeatherRepository(ApixuService service) {
        this.service = service;
    }

    public LiveData<Weather> getWeather(String key, String q, int days) {

        final MutableLiveData<Weather> data = new MutableLiveData<>();

        service.getWeather(key, q, days).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
