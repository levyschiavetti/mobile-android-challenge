package com.test.amaro.amarotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView tvProductName;
    private TextView tvProductPromoPrice;
    private TextView tvProductRegularPrice;
    private TextView tvProductSaleStatus;
    private ImageView ivProduct;
    private LinearLayout containerSizes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bindViews();
    }


    private void bindViews() {

        tvProductName = findViewById(R.id.act_detail_tv_name);
        tvProductPromoPrice = findViewById(R.id.act_detail_tv_promotional_price);
        tvProductRegularPrice = findViewById(R.id.act_detail_tv_regular_price);
        ivProduct = findViewById(R.id.act_detail_iv);
        containerSizes = findViewById(R.id.act_detail_container_sizes);
        tvProductSaleStatus = findViewById(R.id.act_detail_tv_sale);
    }
}
