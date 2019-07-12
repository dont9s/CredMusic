package com.gojek.weather.di.module;

import com.gojek.weather.service.repository.ApixuService;

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

}
