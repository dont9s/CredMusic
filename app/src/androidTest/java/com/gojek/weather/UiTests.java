package com.gojek.weather;


import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.gojek.weather.view.ui.MainActivity;
import com.gojek.weather.view.ui.weather.WeatherFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UiTests {


    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setUp() {
        activityRule.getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, new WeatherFragment())
                .commit();
    }

    @Test
    public void checkRetryButtonClick() {

        onView(withId(R.id.bt_retry))
                .perform(click())
                .check(isVisibleView());


    }

    public ViewAssertion isVisibleView() {
        return getViewAssertion(ViewMatchers.Visibility.VISIBLE);
    }


    private ViewAssertion getViewAssertion(ViewMatchers.Visibility visibility) {
        return ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(visibility));
    }

}
