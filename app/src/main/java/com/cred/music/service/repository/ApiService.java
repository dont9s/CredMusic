package com.cred.music.service.repository;

import com.cred.music.service.model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {


    String BASE_URL = "http://starlord.hackerearth.com/";

    @GET("studio")
    Call<List<Song>> getSongList();
}
