package com.gojek.weather.di.component;

import android.app.Application;

import com.gojek.weather.GojekWeatherApplication;
import com.gojek.weather.di.builder.ActivityBuilder;
import com.gojek.weather.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AppModule.class,
        ActivityBuilder.class,
        AndroidSupportInjectionModule.class
})
public interface AppComponent extends AndroidInjector<GojekWeatherApplication> {

    @Component.Builder
    abstract class  Builder extends AndroidInjector.Builder<GojekWeatherApplication>{}

//    void inject(GojekWeatherApplication application);

}
