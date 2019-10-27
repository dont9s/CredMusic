package com.cred.music.view.ui;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    MainActivityViewModel provideMainActivityViewModel() {
        return new MainActivityViewModel();


    }
}
