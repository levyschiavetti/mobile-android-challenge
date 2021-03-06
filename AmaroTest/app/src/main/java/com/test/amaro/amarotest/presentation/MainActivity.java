package com.test.amaro.amarotest.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.test.amaro.amarotest.common.Constants;
import com.test.amaro.amarotest.R;
import com.test.amaro.amarotest.domain.ProductListResponse;
import com.test.amaro.amarotest.common.Util;

import java.util.ArrayList;
import java.util.List;


/**
 *  Activity responsible for displaying product list from
 *  Amaro's product API.
 *  Also provides filtering and sorting features.
 */
public class MainActivity extends AppCompatActivity
                          implements MainContract.View {


    /**
     *  Presenter member of the MainContract
     */
    private MainContract.Presenter presenter = new MainPresenter(this);

    /**
     *  Product Adapter responsible for carrying list of product
     */
    private ProductAdapter adapter;

    /**
     *  RecyclerView that displays list of products
     */
    private RecyclerView rv;

    /**
     *  Activity's Toolbar
     */
    private Toolbar toolbar;

    /**
     *  TextView responsible for providing information
     *  regarding eventual errors.
     */
    private TextView tvError;

    /**
     *  Progresbar used to display loading status of Network
     *  call
     */
    private ProgressBar pbLoading;

    /**
     *  SwitchView which toogles on and off list state of on sale status
     *  exclusively
     *
     */
    private Switch switchFilter;

    /**
     *  Fab responsible for sorting products in ascending order (by Price)
     */
    private FloatingActionButton fab;

    /**
     *  Button responsible for performing call to API in case of error
     */
    private Button btnTryAgain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        setupListeners();
        setupToolbar();
        setupRecyclerView();
        defineStateToBePresented();
    }


    /**
     *  Configure Activity's Toolbar and its
     *  title's features
     */
    private void setupToolbar() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.act_main_title);
        }
    }

    /**
     *  Configure RecyclerView and initialze its Adapter
     */
    private void setupRecyclerView() {
        adapter = new ProductAdapter(MainActivity.this, new ArrayList<ProductListResponse.Product>(),
                this);

        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rv.setAdapter(adapter);
    }


    /**
     *  Bind Views from layout
     */
    private void bindViews() {
        btnTryAgain = findViewById(R.id.act_main_btn_try_again);
        rv = findViewById(R.id.act_main_rv);
        toolbar = findViewById(R.id.act_main_tb);
        pbLoading = findViewById(R.id.act_main_pb);
        tvError = findViewById(R.id.act_main_tv_error);
        switchFilter = findViewById(R.id.act_main_switch_filter);
        fab = findViewById(R.id.act_main_fab);
    }


    /**
     *  Triggers views that perform some kind of action to start listening
     *  for events
     */
    private void setupListeners() {

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defineStateToBePresented();
            }
        });

        switchFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    adapter.setList(presenter.getOnSaleProductList());
                } else {
                    adapter.setList(presenter.getCompleteProductList());
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<ProductListResponse.Product> list = adapter.getCurrentList();
                presenter.assignPriceValues(list);
                presenter.sortListByPrice(list);
                adapter.setList(list);
            }
        });
    }


    /**
     * @see MainContract for details
     *
     */
    @Override
    public void updateList(List<ProductListResponse.Product> list) {
        adapter.setList(list);
    }

    /**
     * @see MainContract for details
     *
     */
    @Override
    public void startDetailActivity(ProductListResponse.Product product, View iv, View tvName, View tvPrice) {

        Pair<View, String> pairImage = new Pair<>(iv, getString(R.string.transition_name_product_image));
        Pair<View, String> pairName = new Pair<>(tvName, getString(R.string.transition_name_product_name));
        Pair<View, String> pairPrice = new Pair<>(tvPrice, getString(R.string.transition_name_product_price));

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairImage, pairName, pairPrice);

        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra(Constants.EXTRA_PRODUCT, product);
        startActivity(i, options.toBundle());
    }

    /**
     * @see MainContract for details
     *
     */
    @Override
    public void toggleLoadingState() {
        tvError.setVisibility(View.INVISIBLE);
        rv.setVisibility(View.INVISIBLE);
        pbLoading.setVisibility(View.VISIBLE);
        fab.setVisibility(View.INVISIBLE);
        toolbar.setVisibility(View.INVISIBLE);
        btnTryAgain.setVisibility(View.INVISIBLE);
    }


    /**
     * @see MainContract for details
     *
     */
    @Override
    public void toggleErrorState() {
        tvError.setVisibility(View.VISIBLE);
        rv.setVisibility(View.INVISIBLE);
        pbLoading.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.INVISIBLE);
        toolbar.setVisibility(View.INVISIBLE);
        btnTryAgain.setVisibility(View.VISIBLE);
    }


    /**
     * @see MainContract for details
     *
     */
    @Override
    public void toggleNormalState() {
        tvError.setVisibility(View.INVISIBLE);
        rv.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        btnTryAgain.setVisibility(View.INVISIBLE);
    }


    /**
     *  Define which views user is going to see, according to their
     *  connection state at the moment. If user is connected, a call
     *  to the API is going to be performed and data is going to
     *  be presented.
     *  If user has no connection, error state is going to be
     *  presented.
     */
    public void defineStateToBePresented() {

        toggleLoadingState();

        if (Util.isOnline(this)) {
            presenter.performRequestToRetrieveProductList();
        } else {
            toggleErrorState();
        }
    }
}
