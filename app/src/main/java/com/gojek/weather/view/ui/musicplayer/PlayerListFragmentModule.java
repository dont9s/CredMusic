package com.gojek.weather.view.ui.musicplayer;

import androidx.lifecycle.ViewModelProvider;

import com.gojek.weather.service.repository.ApiService;
import com.gojek.weather.service.repository.SongRepository;
import com.gojek.weather.viewmodel.PlayerListViewModel;
import com.gojek.weather.viewmodel.PlayerViewModelFactory;

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
