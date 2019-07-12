package com.gojek.weather;

import com.gojek.weather.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class GojekWeatherApplication extends DaggerApplication {

    private static GojekWeatherApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public static synchronized GojekWeatherApplication getInstance() {
        return instance;

    }


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
