package com.test.amaro.amarotest;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
import android.support.test.rule.ActivityTestRule;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.assertion.ViewAssertions.matches;



@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> actTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shouldDisplayToolbar() {
        onView(withId(R.id.act_main_tb)).check(matches(isDisplayed()));
        onView(withId(R.id.act_main_fab)).check(matches(isDisplayed()));
        onView(withId(R.id.act_main_switch_filter)).check(matches(isDisplayed()));
    }

}




