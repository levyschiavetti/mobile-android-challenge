package com.test.amaro.amarotest;

import java.util.List;

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

    }
}
