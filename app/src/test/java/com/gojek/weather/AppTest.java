package com.gojek.weather;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.gojek.weather.helper.Constant;
import com.gojek.weather.helper.DateUtil;
import com.gojek.weather.helper.PermissionManager;
import com.gojek.weather.helper.PrefManager;
import com.gojek.weather.helper.WeatherLoadStatus;
import com.gojek.weather.service.model.Weather;
import com.gojek.weather.service.repository.ApixuService;
import com.gojek.weather.service.repository.WeatherRepository;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {

    @Mock
    private LiveData<Weather> weather;

    private WeatherLoadStatus weatherLoadStatus;
    @Mock
    private PrefManager prefManager;


    @Mock
    private WeatherRepository weatherRepository;


    public AppTest() {


    }

    @Test
    public void getWeatherTest() {

        String q = "Gurgaon";

        when(prefManager.getFloat(Constant.LATITUDE, 0)).thenReturn((float) 28.26);
        when(prefManager.getFloat(Constant.LONGITUDE, 0)).thenReturn((float) 77.03);

        if (!(prefManager.getFloat(Constant.LATITUDE, 0) == 0 || prefManager.getFloat(Constant.LONGITUDE, 0) == 0))
            q = prefManager.getFloat(Constant.LATITUDE, 0) + "," + prefManager.getFloat(Constant.LONGITUDE, 0);


        weather = weatherRepository.getWeather(Constant.APIXU_API_KEY,
                q,
                5);

        weather.observe(null, new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                assertNull(weather.getCurrent().getTempC());
            }
        });


    }

    @Test
    public void testDateUtil() {

        String date = "14-07-2019";

//        System.out.println(DateUtil.getDayOfWeek(date));
    }



}
