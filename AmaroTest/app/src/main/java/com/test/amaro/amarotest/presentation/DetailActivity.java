package com.test.amaro.amarotest.presentation;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.test.amaro.amarotest.common.Constants;
import com.test.amaro.amarotest.R;
import com.test.amaro.amarotest.domain.ProductListResponse;


/**
 *  Activity responsible for displaying details of a product.
 */
public class DetailActivity extends AppCompatActivity {

    /**
     *  TextView that presents Product's name
     */
    private TextView tvProductName;

    /**
     *  TextView that presents Product's Promotional Price
     */
    private TextView tvProductPromoPrice;

    /**
     *  TextView that presents Product's Regular Price
     */
    private TextView tvProductRegularPrice;

    /**
     *  TextView that presents Product's Sale Status
     */
    private TextView tvProductSaleStatus;

    /**
     *  ImageView that presents Product's illustration
     */
    private ImageView ivProduct;

    /**
     *  Viewgroup container that presents Product's available sizes
     */
    private LinearLayout containerSizes;


    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bindViews();

        if (getIntent().getParcelableExtra(Constants.EXTRA_PRODUCT) != null) {
            ProductListResponse.Product product = getIntent().getParcelableExtra(Constants.EXTRA_PRODUCT);
            assignValuesToViews(product);
            generateAvailableSizes(product);
            setupOnSaleLayout(product);
            setupToolbar(product.getName());
        }
    }

    /**
     *  Configure Activity's Toolbar and its
     *  title's features according to the product name
     */
    private void setupToolbar(String productName) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(productName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
     * Set values to Displayed Views according to the product in
     * parameter
     * @param product The product which views are going to get values from
     */
    private void assignValuesToViews(ProductListResponse.Product product) {

        tvProductName.setText(product.getName());
        tvProductPromoPrice.setText(product.getPricePromotional());
        tvProductRegularPrice.setText(product.getPriceRegular());

        RequestOptions opt = new RequestOptions()
                .placeholder(R.mipmap.icon_launcher)
                .error(R.mipmap.icon_launcher);

        Glide.with(this)
                .load(product.getImageUrl())
                .apply(opt)
                .into(ivProduct);
    }


    /**
     *  Iterates through all the sizes in a product
     *  and create simple TextViews to display each one of the
     *  available ones.
     *
     * @param product - The Product to have generated sizes
     */
    private void generateAvailableSizes(ProductListResponse.Product product) {

        for (ProductListResponse.Size size : product.getSizeList()) {
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
    private void setupOnSaleLayout(ProductListResponse.Product product) {

        if (product.isOnSale()) {
            tvProductSaleStatus.setVisibility(View.VISIBLE);
            tvProductPromoPrice.setVisibility(View.VISIBLE);
            tvProductRegularPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
