package com.cred.music;

import com.cred.music.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class CredMusicApplication extends DaggerApplication {

    private static CredMusicApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public static synchronized CredMusicApplication getInstance() {
        return instance;

    }


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
