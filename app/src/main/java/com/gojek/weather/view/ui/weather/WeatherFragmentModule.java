package com.gojek.weather.view.ui.weather;

import androidx.lifecycle.ViewModelProvider;

import com.gojek.weather.service.repository.ApixuService;
import com.gojek.weather.service.repository.WeatherRepository;
import com.gojek.weather.viewmodel.WeatherViewModel;
import com.gojek.weather.viewmodel.WeatherViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherFragmentModule {

    @Provides
    WeatherViewModel provideWeatherViewModel(WeatherRepository repository) {
        return new WeatherViewModel(repository);

    }


    @Provides
    WeatherRepository provideWeatherRepository(ApixuService service) {
        return new WeatherRepository(service);
    }


    @Provides
    ViewModelProvider.Factory provideViewModelProvide(WeatherViewModel viewModel) {
        return new WeatherViewModelFactory<>(viewModel);

    }
}
