package com.test.amaro.amarotest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;

import com.test.amaro.amarotest.presentation.DetailActivity;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    @Rule
    public ActivityTestRule<DetailActivity> actTestRule = new ActivityTestRule<>(DetailActivity.class);


    @Test
    public void shouldDisplayViews() {
        onView(withId(R.id.act_detail_tv_sizes)).check(matches(isDisplayed()));
        onView(withId(R.id.act_detail_iv)).check(matches(isDisplayed()));
        onView(withId(R.id.act_detail_tv_name)).check(matches(isDisplayed()));
        onView(withId(R.id.act_detail_tv_regular_price)).check(matches(isDisplayed()));
    }
}
