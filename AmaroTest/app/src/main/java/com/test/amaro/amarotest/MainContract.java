package com.test.amaro.amarotest;

import java.util.List;

/**
 *  Contract between MainActivity and MainPresenter
 */
public interface MainContract {

    interface View {
        void updateList(List<ResponseList.Product> list);
        void toggleLoadingState();
        void toggleErrorState();
        void toggleNormalState();
        void startDetailActivity(ResponseList.Product product, android.view.View ivProduct,
                                 android.view.View tvName, android.view.View tvPrice);
    }

    interface Presenter {
        void performRequestToRetrieveProductList();
        List<ResponseList.Product> getOnSaleProductList();
        List<ResponseList.Product> getCompleteProductList();
        List<ResponseList.Product> assignPriceValues(List<ResponseList.Product> list);
        List<ResponseList.Product> sortListByPrice(List<ResponseList.Product> list);
    }
}
