package com.cred.music.di.module;

import android.content.Context;

import com.cred.music.CredMusicApplication;
import com.cred.music.helper.PrefManager;
import com.cred.music.service.repository.ApiService;
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
    ApiService provideApiService() {
        return new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);

    }

    @Singleton
    @Provides
    Context provideContext(CredMusicApplication application) {
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
