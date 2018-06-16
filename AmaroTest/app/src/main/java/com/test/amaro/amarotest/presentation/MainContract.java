package com.test.amaro.amarotest.presentation;

import com.test.amaro.amarotest.domain.ProductListResponse;

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
        void updateList(List<ProductListResponse.Product> list);

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
         * @param ivProduct Product ImageView element for animation
         * @param tvName Product TextView name element for animation
         * @param tvPrice Product TextView price element for animation
         */
        void startDetailActivity(ProductListResponse.Product product, android.view.View ivProduct,
                                 android.view.View tvName, android.view.View tvPrice);
    }

    /**
     *  Responsibilities of Presenter
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
        List<ProductListResponse.Product> getOnSaleProductList();

        /**
         * Get complete list of products
         * @return The complete list of products
         */
        List<ProductListResponse.Product> getCompleteProductList();

        /**
         * Iterates among provided product list in order to assign price
         * values in Double format
         * @param list The list to assign price values
         * @return The same list containing all products with prices also
         * in Double (beside Strings)
         */
        List<ProductListResponse.Product> assignPriceValues(List<ProductListResponse.Product> list);

        /**
         * Sort list by ascending order according to 'Price' property
         * @param list The list to be sorted
         * @return The sorted list
         */
        List<ProductListResponse.Product> sortListByPrice(List<ProductListResponse.Product> list);
    }
}
