package com.gojek.weather;

import android.app.Application;
import android.content.Context;

import androidx.test.runner.AndroidJUnitRunner;

import com.github.tmurakami.dexopener.DexOpener;

public class AndroidTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        DexOpener.install(this);
        return super.newApplication(cl,
                WeatherTestApplication.class.getName(),
                context);
    }
}
