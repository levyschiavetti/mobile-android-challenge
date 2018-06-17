package com.test.amaro.amarotest.presentation;

import android.support.annotation.NonNull;

import com.test.amaro.amarotest.common.Constants;
import com.test.amaro.amarotest.domain.ProductService;
import com.test.amaro.amarotest.domain.ProductListResponse;

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
        Callback<ProductListResponse> {

    /**
     *  View member of MainContract
     */
    private MainContract.View view;

    /**
     *  Retrofit object responsible for performing network calls
     */
    private Retrofit retrofit;

    /**
     *  The complete list of products received from API
     */
    private List<ProductListResponse.Product> completeProductList = new ArrayList<>();

    /**
     *  The filtered list of products by 'on sale' status (true)
     */
    private List<ProductListResponse.Product> onSaleProductList = new ArrayList<>();



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
                .baseUrl(Constants.SERVER_URL)
                .build();
    }

    /**
     * @see MainContract
     */
    @Override
    public void performRequestToRetrieveProductList() {

        ProductService service = retrofit.create(ProductService.class);

        Call<ProductListResponse> call = service.getAmaroProductList();

        call.enqueue(this);
    }


    /**
     * @see MainContract
     */
    @Override
    public void onResponse(@NonNull Call<ProductListResponse> call, @NonNull Response<ProductListResponse> response) {

        if (response.isSuccessful() &&
            response.body() != null) {

            ProductListResponse productListResponse = response.body();

            if (productListResponse != null) {
                completeProductList = productListResponse.getProducts();
                onSaleProductList = buildOnSaleList(completeProductList);
                view.updateList(completeProductList);
                view.toggleNormalState();
            }
        } else {
            view.toggleErrorState();
        }
    }


    /**
     * @see MainContract
     */
    @Override
    public void onFailure(@NonNull Call<ProductListResponse> call, Throwable t) {
        view.toggleErrorState();
    }

    /**
     * @see MainContract
     */
    @Override
    public List<ProductListResponse.Product> getOnSaleProductList() {
        return onSaleProductList;
    }

    /**
     * @see MainContract
     */
    @Override
    public List<ProductListResponse.Product> getCompleteProductList() {
        return completeProductList;
    }

    /**
     *  Filter a list according to OnSale product status
     * @param givenList The list to be filtered
     * @return The filtered list
     */
    public List<ProductListResponse.Product> buildOnSaleList(List<ProductListResponse.Product> givenList) {

        List<ProductListResponse.Product> list = new ArrayList<>();

        for (ProductListResponse.Product product : givenList) {
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
    public List<ProductListResponse.Product> assignPriceValues(List<ProductListResponse.Product> list) {

        for (ProductListResponse.Product product : list) {

            String priceString;

            if (product.isOnSale()) {
                priceString = product.getPricePromotional();
            } else {
                priceString = product.getPriceRegular();
            }

            priceString = priceString.replace("R$", "")
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
    public List<ProductListResponse.Product> sortListByPrice(List<ProductListResponse.Product> list) {

        Collections.sort(list, new Comparator<ProductListResponse.Product>() {
            @Override
            public int compare(ProductListResponse.Product product1, ProductListResponse.Product product2) {
                return product1.getPrice().compareTo(product2.getPrice());
            }
        });

        return list;
    }
}
