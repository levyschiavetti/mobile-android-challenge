package com.test.amaro.amarotest.presentation;

import com.test.amaro.amarotest.domain.ResponseList;

import java.util.List;

/**
 *  Contract between MainActivity and MainPresenter
 */
public interface MainContract {

    /**
     *  Responsabilities of View
     */
    interface View {
        /**
         * Responsible for updating current displayed list
         * in RecyclerView
         * @param list
         */
        void updateList(List<ResponseList.Product> list);

        /**
         *  Change group of view's visibilities to match
         *  Loading State layout
         */
        void toggleLoadingState();

        /**
         *  Change group of view's visibilities to match
         *  Error State layout
         */
        void toggleErrorState();

        /**
         *  Change group of view's visibilities to match
         *  normal State layout
         */
        void toggleNormalState();

        /**
         * Start detail activity with given product details
         * @param product The product to be displayed in details
         */
        void startDetailActivity(ResponseList.Product product, android.view.View ivProduct,
                                 android.view.View tvName, android.view.View tvPrice);
    }

    /**
     *  Responsabilities of Presenter
     */
    interface Presenter {

        /**
         *  Call REST API in order to get product list
         */
        void performRequestToRetrieveProductList();

        /**
         *  Get product list with onSale status
         * @return onSale list of products
         */
        List<ResponseList.Product> getOnSaleProductList();

        /**
         * Get complete list of products
         * @return The complete list of products
         */
        List<ResponseList.Product> getCompleteProductList();

        /**
         * Iterates among provided product list in order to assign price
         * values in Double format
         * @param list The list to assign price values
         * @return The same list containing all products with prices also
         * in Double (beside Strings)
         */
        List<ResponseList.Product> assignPriceValues(List<ResponseList.Product> list);

        /**
         * Sort list by ascending order according to 'Price' property
         * @param list The list to be sorted
         * @return The sorted list
         */
        List<ResponseList.Product> sortListByPrice(List<ResponseList.Product> list);
    }
}
