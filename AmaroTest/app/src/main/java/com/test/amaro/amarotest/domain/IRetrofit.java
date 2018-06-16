package com.test.amaro.amarotest.domain;

import com.test.amaro.amarotest.common.Constants;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 *  Interface responsible for performing request to
 *  Amaro's API regarding products
 */
public interface IRetrofit {

    String BASE_URL = Constants.SERVER_URL;

    /**
     * Retrieve all time best-seller product list
     * @return product list
     */
    @GET("59b6a65a0f0000e90471257d")
    public Call<ResponseList> getAmaroProductList();

}
