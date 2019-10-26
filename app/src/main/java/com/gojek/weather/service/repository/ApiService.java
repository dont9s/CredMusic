package com.gojek.weather.service.repository;

import com.gojek.weather.service.model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {


    String BASE_URL = "http://starlord.hackerearth.com/";

    @GET("studio")
    Call<List<Song>> getSongList();
}
