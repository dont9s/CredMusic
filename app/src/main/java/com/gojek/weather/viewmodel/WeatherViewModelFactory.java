package com.gojek.weather.viewmodel;


import android.support.v4.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gojek.weather.di.ViewModelSubcomponent;

import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;

public class WeatherViewModelFactory implements ViewModelProvider.Factory {

    private final ArrayMap<Class, Callable<? extends ViewModel>> creators;


    @Inject
    public WeatherViewModelFactory(final ViewModelSubcomponent viewModelSubcomponent) {
        creators = new ArrayMap<>();


        // View models cannot be injected directly because they won't be bound to the owner's view model scope.
        creators.put(WeatherViewModel.class, new Callable<ViewModel>() {
            @Override
            public ViewModel call() throws Exception {
                return viewModelSubcomponent.projectViewModel();

            }
        });


    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Callable<? extends ViewModel> creator = creators.get(modelClass);
        if (creator == null) {
            for (Map.Entry<Class, Callable<? extends ViewModel>> entry : creators.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("Unknown model class " + modelClass);
        }
        try {
            return (T) creator.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
