package com.cred.music.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cred.music.helper.SongLoadStatus;
import com.cred.music.service.model.Song;
import com.cred.music.service.repository.SongRepository;

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
