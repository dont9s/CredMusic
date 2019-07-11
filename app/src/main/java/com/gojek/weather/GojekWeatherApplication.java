package com.gojek.weather;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class GojekWeatherApplication extends DaggerApplication {

    private static GojekWeatherApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static synchronized GojekWeatherApplication getInstance() {
        return instance;

    }


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return null;
    }
}
