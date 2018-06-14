package com.test.amaro.amarotest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IRetrofit {

    public static final String BASE_URL = "http://www.mocky.io/v2/";

    @GET("59b6a65a0f0000e90471257d")
    public Call<ResponseList> getAmaroProductList();

}
