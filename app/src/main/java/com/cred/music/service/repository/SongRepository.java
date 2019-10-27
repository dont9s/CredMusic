package com.cred.music.service.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cred.music.service.model.Song;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class SongRepository {

    private ApiService service;


    @Inject
    public SongRepository(ApiService service) {
        this.service = service;
    }

    public LiveData<List<Song>> getSongList() {

        final MutableLiveData<List<Song>> data = new MutableLiveData<>();

        service.getSongList().enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
