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

/**
 *  Presenter of MainActivity, responsible for working
 *  on the logic of MainActivity's purpose
 */
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

    /**
     *  Initialize Retrofit for performing call to
     *  the product API
     */
    private void setupRetrofit() {

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(IRetrofit.BASE_URL)
                .build();
    }

    /**
     * @see MainContract
     */
    @Override
    public void performRequestToRetrieveProductList() {

        IRetrofit service = retrofit.create(IRetrofit.class);

        Call<ResponseList> call = service.getAmaroProductList();

        call.enqueue(this);
    }


    /**
     * @see MainContract
     */
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


    /**
     * @see MainContract
     */
    @Override
    public void onFailure(Call<ResponseList> call, Throwable t) {
        view.toggleErrorState();
    }

    /**
     * @see MainContract
     */
    @Override
    public List<ResponseList.Product> getOnSaleProductList() {
        return onSaleProductList;
    }

    /**
     * @see MainContract
     */
    @Override
    public List<ResponseList.Product> getCompleteProductList() {
        return completeProductList;
    }

    /**
     *  Filter a list according to OnSale product status
     * @param givenList The list to be filtered
     * @return The filtered list
     */
    public List<ResponseList.Product> buildOnSaleList(List<ResponseList.Product> givenList) {

        List<ResponseList.Product> list = new ArrayList<>();

        for (ResponseList.Product product : givenList) {
            if (product.isOnSale()) {
                list.add(product);
            }
        }
        return list;
    }

    /**
     * @see MainContract
     */
    @Override
    public List<ResponseList.Product> assignPriceValues(List<ResponseList.Product> list) {

        for (ResponseList.Product product : list) {

            String priceString = product.getPriceRegular()
                    .replace("R$", "")
                    .replace(",",".")
                    .trim();

            Double price = Double.parseDouble(priceString);

            product.setPrice(price);
        }

        return list;
    }


    /**
     * @see MainContract
     */
    @Override
    public List<ResponseList.Product> sortListByPrice(List<ResponseList.Product> list) {

        Collections.sort(list, new Comparator<ResponseList.Product>() {
            @Override
            public int compare(ResponseList.Product product1, ResponseList.Product product2) {
                return product1.getPrice().compareTo(product2.getPrice());
            }
        });

        return list;
    }
}
