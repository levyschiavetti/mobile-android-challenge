package com.test.amaro.amarotest;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import android.support.test.rule.ActivityTestRule;

import com.test.amaro.amarotest.common.Util;
import com.test.amaro.amarotest.presentation.MainActivity;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.OkHttpClient;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.assertion.ViewAssertions.matches;



@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> actTestRule = new ActivityTestRule<>(MainActivity.class);

    private MockWebServer serverMock;

    @Before
    public void setup() throws IOException {
        serverMock = new MockWebServer();
        serverMock.start();
    }

    @Test
    public void when_noConnection_displayErrorState() {

        if (!Util.isOnline(actTestRule.getActivity())) {
            onView(withId(R.id.act_main_tv_error)).check(matches(isDisplayed()));
            onView(withId(R.id.act_main_fab)).check(matches(not(isDisplayed())));
            onView(withId(R.id.act_main_rv)).check(matches(not(isDisplayed())));
            onView(withId(R.id.act_main_pb)).check(matches(not(isDisplayed())));
            onView(withId(R.id.act_main_tb)).check(matches(not(isDisplayed())));
        }
    }



    @Test
    public void testRequestAPI() throws IOException, Exception {

        serverMock.enqueue(new MockResponse()
                                .setBody("Test"));

        HttpUrl url = serverMock.url("amaro/api");

        String responseBody = performRequest(new OkHttpClient(), url);

        System.out.println("Test Response is " + responseBody);
    }

    public String performRequest(OkHttpClient client, HttpUrl serverUrl) throws Exception {

        RequestBody body =
                RequestBody.create(MediaType.parse("text/plain"), "Este é o corpo de request");

        okhttp3.Request request = new okhttp3.Request.Builder()
                .post(body)
                .url(serverUrl)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}




