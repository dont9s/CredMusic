package com.gojek.weather.view.ui.weather;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class WeatherFragmentProvider {

    @ContributesAndroidInjector(modules = WeatherFragmentModule.class)
    abstract WeatherFragment provideWeatherFragment();
}
