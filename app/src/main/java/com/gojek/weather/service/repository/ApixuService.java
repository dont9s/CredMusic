package com.gojek.weather.service.repository;

import com.gojek.weather.service.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApixuService {

    /*?key=8ccb207f1a684769983154436190807&q=gurgaon&days=5*/
    String HTTPS_APIXU_API_URL = "https://api.apixu.com/v1/forecast.json";

    @GET()
    Call<Weather> getWeather(@Query("key") String key,
                             @Query("q") String q,
                             @Query("days") int days);

}
