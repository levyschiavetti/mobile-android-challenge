package com.test.amaro.amarotest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainPresenter implements MainContract.Presenter {


    private MainContract.View view;
    private Retrofit retrofit;


    public MainPresenter(MainContract.View view) {
        this.view = view;

        setupRetrofit();
    }

    private void setupRetrofit() {

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(IRetrofit.BASE_URL)
                .build();
    }
}
