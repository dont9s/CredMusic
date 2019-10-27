package com.cred.music.view.ui.musicplayer;

import androidx.lifecycle.ViewModelProvider;

import com.cred.music.service.repository.ApiService;
import com.cred.music.service.repository.SongRepository;
import com.cred.music.viewmodel.PlayerListViewModel;
import com.cred.music.viewmodel.PlayerViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class PlayerListFragmentModule {

    @Provides
    PlayerListViewModel providePlayerListViewModel(SongRepository repository) {
        return new PlayerListViewModel(repository);

    }


    @Provides
    SongRepository provideSongRepository(ApiService service) {
        return new SongRepository(service);
    }


    @Provides
    ViewModelProvider.Factory provideViewModelProvide(PlayerListViewModel viewModel) {
        return new PlayerViewModelFactory<>(viewModel);

    }

}
