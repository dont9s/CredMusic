package com.gojek.weather.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gojek.weather.helper.SongLoadStatus;
import com.gojek.weather.service.model.Song;
import com.gojek.weather.service.repository.SongRepository;

import java.util.List;

import javax.inject.Inject;

public class PlayerListViewModel extends ViewModel {
    private static final String TAG = PlayerListViewModel.class.getCanonicalName();

    private LiveData<List<Song>> songsObservable;

    private MutableLiveData<SongLoadStatus> songLoadStatusLiveData;

    private SongRepository songRepository;


    @Inject
    public PlayerListViewModel(@NonNull SongRepository songRepository) {


        this.songRepository = songRepository;

        getSongs();

    }

    public void getSongs() {


        songsObservable = songRepository.getSongList();
    }

    public LiveData<List<Song>> getObservableSong() {
        return songsObservable;

    }

    public void setSongLoadStatusLiveData(SongLoadStatus songLoadStatus) {
        if (songLoadStatusLiveData == null)
            songLoadStatusLiveData = new MutableLiveData<>();


        songLoadStatusLiveData.setValue(songLoadStatus);


    }

    public LiveData<SongLoadStatus> getSongLoadStatusLiveData() {
        return songLoadStatusLiveData;
    }

}
