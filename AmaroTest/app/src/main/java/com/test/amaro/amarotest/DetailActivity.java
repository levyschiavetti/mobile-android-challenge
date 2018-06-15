package com.test.amaro.amarotest;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


/**
 *  Activity responsible for displaying details of a product
 */
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

        if (getIntent().getParcelableExtra("Product") != null) {
            ResponseList.Product product = getIntent().getParcelableExtra("Product");
            assignValuesToViews(product);
            generateAvailableSizes(product);
            setupOnSaleLayout(product);
        }
    }

    /**
     *  Bind views from Activity's layout
     */
    private void bindViews() {

        tvProductName = findViewById(R.id.act_detail_tv_name);
        tvProductPromoPrice = findViewById(R.id.act_detail_tv_promotional_price);
        tvProductRegularPrice = findViewById(R.id.act_detail_tv_regular_price);
        ivProduct = findViewById(R.id.act_detail_iv);
        containerSizes = findViewById(R.id.act_detail_container_sizes);
        tvProductSaleStatus = findViewById(R.id.act_detail_tv_sale);
    }


    /**
     * Set values to Displayed Views according to the product
     * parameter
     * @param product The product which views are going to get values from
     */
    private void assignValuesToViews(ResponseList.Product product) {

        tvProductName.setText(product.getName());
        tvProductPromoPrice.setText(product.getPriceActual());
        tvProductRegularPrice.setText(product.getPriceRegular());

        RequestOptions opt = new RequestOptions()
                .placeholder(R.drawable.vector_placeholder)
                .error(R.drawable.vector_placeholder);

        Glide.with(this)
                .load(product.getImageUrl())
                .apply(opt)
                .into(ivProduct);
    }


    /**
     *  Iterates through all the sizes in a product
     *  and create simple textviews to display each one of the
     *  available ones.
     *
     * @param product - Product to have generated sizes
     */
    private void generateAvailableSizes(ResponseList.Product product) {

        for (ResponseList.Size size : product.getSizeList()) {
            if (size.isAvailable()) {

                TextView textView = new TextView(this);
                textView.setText(size.getSize());

                LinearLayout.LayoutParams lp = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 10, 15, 0);
                textView.setLayoutParams(lp);
                containerSizes.addView(textView);
            }
        }
    }


    /**
     *  Changes layout to fit On Sale product.
     *  Toggle on visibility of promotional views according to product's
     *  onSale status and change regular price appearance
     *
     * @param product the product to be displayed
     */
    private void setupOnSaleLayout(ResponseList.Product product) {

        if (product.isOnSale()) {
            tvProductSaleStatus.setVisibility(View.VISIBLE);
            tvProductPromoPrice.setVisibility(View.VISIBLE);
            tvProductRegularPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
