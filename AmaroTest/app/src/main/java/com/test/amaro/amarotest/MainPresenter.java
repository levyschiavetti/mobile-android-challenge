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


public class MainPresenter implements MainContract.Presenter,
                                      Callback<ResponseList> {


    private MainContract.View view;
    private Retrofit retrofit;
    private List<ResponseList.Product> completeProductList = new ArrayList<>();
    private List<ResponseList.Product> onSaleProductList = new ArrayList<>();


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

    @Override
    public void performRequestToRetrieveProductList() {

        IRetrofit service = retrofit.create(IRetrofit.class);

        Call<ResponseList> call = service.getAmaroProductList();

        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseList> call, Response<ResponseList> response) {

        if (response.body() != null) {

            ResponseList responseList = response.body();

            if (responseList != null) {
                completeProductList = responseList.getProducts();
                onSaleProductList = buildOnSaleList(completeProductList);
                view.updateList(completeProductList);
                view.toggleNormalState();
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseList> call, Throwable t) {
        view.toggleErrorState();
    }


    public List<ResponseList.Product> buildOnSaleList(List<ResponseList.Product> givenList) {

        List<ResponseList.Product> list = new ArrayList<>();

        for (ResponseList.Product product : givenList) {
            if (product.isOnSale()) {
                list.add(product);
            }
        }
        return list;
    }

    @Override
    public List<ResponseList.Product> getOnSaleProductList() {
        return null;
    }

    @Override
    public List<ResponseList.Product> getCompleteProductList() {
        return null;
    }

    @Override
    public List<ResponseList.Product> assignPriceValues(List<ResponseList.Product> list) {
        return null;
    }

    @Override
    public List<ResponseList.Product> sortListByPrice(List<ResponseList.Product> list) {
        return null;
    }
}
