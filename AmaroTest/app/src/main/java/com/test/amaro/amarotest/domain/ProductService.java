package com.test.amaro.amarotest.domain;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 *  Interface responsible for performing requests to products
 *  section of Amaro's API
 *
 */
public interface ProductService {

    /**
     * Retrieve all time best-seller product list
     * @return product list
     */
    @GET("59b6a65a0f0000e90471257d")
    Call<ProductListResponse> getAmaroProductList();

}
