package com.test.amaro.amarotest;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


/**
 *  Activity responsible for displaying product list from
 *  Amaro's product API.
 *  Also provides filtering and sorting features.
 */
public class MainActivity extends AppCompatActivity
        implements MainContract.View {


    private MainContract.Presenter presenter = new MainPresenter(this);
    private ProductAdapter adapter;
    private RecyclerView rv;
    private Toolbar toolbar;
    private TextView tvError;
    private ProgressBar pbLoading;
    private Switch switchFilter;
    private FloatingActionButton fab;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        setupListeners();
        setupToolbar();
        setupRecyclerView();

        if (Util.isOnline(this)) {
            presenter.performRequestToRetrieveProductList();
            toggleLoadingState();
        } else {
            toggleErrorState();
        }
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
     *  Configure RecyclerView and initialzeits Adapter
     */
    private void setupRecyclerView() {
        adapter = new ProductAdapter(MainActivity.this, new ArrayList<ResponseList.Product>(),
                this);

        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rv.setAdapter(adapter);
    }


    /**
     *  Bind Views from layout
     */
    private void bindViews() {
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

                List<ResponseList.Product> list = adapter.getCurrentList();
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
    public void updateList(List<ResponseList.Product> list) {
        adapter.setList(list);
    }

    /**
     * @see MainContract for details
     *
     */
    @Override
    public void startDetailActivity(ResponseList.Product product, View iv, View tvName, View tvPrice) {

        Pair<View, String> pairImage = new Pair<>(iv, "product_image");
        Pair<View, String> pairName = new Pair<>(tvName, "product_name");
        Pair<View, String> pairPrice = new Pair<>(tvPrice, "product_price");

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairImage, pairName, pairPrice);

        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("Product", product);
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
    }
}
