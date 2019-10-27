package com.cred.music.di.builder;

import com.cred.music.view.ui.MainActivity;
import com.cred.music.view.ui.MainActivityModule;
import com.cred.music.view.ui.musicplayer.PlayerListFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {MainActivityModule.class,
            PlayerListFragmentProvider.class})
    abstract MainActivity contributeMainActivity();


}
