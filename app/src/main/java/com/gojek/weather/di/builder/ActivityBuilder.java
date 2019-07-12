package com.gojek.weather.di.builder;

import com.gojek.weather.view.ui.MainActivityModule;
import com.gojek.weather.view.ui.MainActivity;
import com.gojek.weather.view.ui.weather.WeatherFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {MainActivityModule.class,
            WeatherFragmentProvider.class})
    abstract MainActivity contributeMainActivity();


}
