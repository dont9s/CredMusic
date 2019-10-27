package com.cred.music;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.cred.music.helper.DateUtil;
import com.cred.music.helper.PrefManager;
import com.cred.music.service.model.weather.Weather;
import com.cred.music.view.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {

    @Mock
    private LiveData<Weather> weather;

    private LiveData<WeatherLoadStatus> weatherLoadStatus;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private PrefManager prefManager;

    @Mock
    LifecycleOwner lifecycleOwner;

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    MainActivity activity;


    @Mock
    Observer<WeatherLoadStatus> observer;


    public AppTest() {


    }


    @Before
    public void setUp() {
        weatherLoadStatus = new MediatorLiveData<>();
    }


    @Test
    public void checkWeatherLoadStatusFail() {

        observer = new Observer<WeatherLoadStatus>() {
            @Override
            public void onChanged(WeatherLoadStatus weatherLoadStatus) {

            }
        };

        weatherLoadStatus.observeForever(observer);

        ((MediatorLiveData<WeatherLoadStatus>) weatherLoadStatus).postValue(WeatherLoadStatus.FAILURE_LOAD);

        assertNotEquals(weatherLoadStatus.getValue(), WeatherLoadStatus.SUCCESS_LOADED);


    }

    @Test
    public void checkWeatherLoadStatusSuccess() {

        observer = new Observer<WeatherLoadStatus>() {
            @Override
            public void onChanged(WeatherLoadStatus weatherLoadStatus) {

            }
        };

        weatherLoadStatus.observeForever(observer);

        ((MediatorLiveData<WeatherLoadStatus>) weatherLoadStatus).postValue(WeatherLoadStatus.SUCCESS_LOADED);

        assertEquals(weatherLoadStatus.getValue(), WeatherLoadStatus.SUCCESS_LOADED);


    }

    @Test
    public void testDateUtil() {

        String date = "14-07-2019";

        assertEquals("Tuesday", DateUtil.getDayOfWeek(date));
    }

    @Test
    public void testDateUtilFail() {

        String date = "14-07-2019";

        assertNotEquals("Wednesday", DateUtil.getDayOfWeek(date));
    }


}
