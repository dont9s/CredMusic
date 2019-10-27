package com.cred.music.view.ui.musicplayer;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class PlayerListFragmentProvider {

    @ContributesAndroidInjector(modules = PlayerListFragmentModule.class)
    abstract PlayerListFragment providePlayerListFragment();
}
