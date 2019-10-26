package com.gojek.weather.di.module;

import android.content.Context;

import com.gojek.weather.GojekWeatherApplication;
import com.gojek.weather.helper.PrefManager;
import com.gojek.weather.service.repository.ApiService;
import com.gojek.weather.service.repository.ApixuService;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Singleton
    @Provides
    ApixuService provideApixuService() {
        return new Retrofit.Builder()
                .baseUrl(ApixuService.HTTPS_APIXU_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApixuService.class);

    }

    @Singleton
    @Provides
    ApiService provideApiService() {
        return new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);

    }

    @Singleton
    @Provides
    Context provideContext(GojekWeatherApplication application) {
        return application;

    }

    @Singleton
    @Provides
    PrefManager providePrefManager(Context context, Gson gson) {
        return new PrefManager(context, gson);

    }

    @Singleton
    @Provides
    Gson provideGson() {
        return new Gson();
    }
}
