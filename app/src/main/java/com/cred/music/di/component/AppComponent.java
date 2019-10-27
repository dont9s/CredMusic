package com.cred.music.di.component;

import com.cred.music.CredMusicApplication;
import com.cred.music.di.builder.ActivityBuilder;
import com.cred.music.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AppModule.class,
        ActivityBuilder.class,
        AndroidSupportInjectionModule.class
})
public interface AppComponent extends AndroidInjector<CredMusicApplication> {

    @Component.Builder
    abstract class  Builder extends AndroidInjector.Builder<CredMusicApplication>{}


}
