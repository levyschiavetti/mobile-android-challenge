package com.test.amaro.amarotest;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity
                          implements MainContract.View {


    private MainContract.Presenter presenter = new MainPresenter(this);
    private RecyclerView rv;
    private Toolbar toolbar;
    private TextView tvError;
    private ProgressBar pbLoading;
    private Switch switchFilter;
    private ImageView ivSort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        setupListeners();
        setupToolbar();
        setupRecyclerView();
        presenter.performRequestToRetrieveProductList();
        toggleLoadingState();
    }

    private void bindViews() {
        rv = findViewById(R.id.act_main_rv);
        toolbar = findViewById(R.id.act_main_tb);
        pbLoading = findViewById(R.id.act_main_pb);
        tvError = findViewById(R.id.act_main_tv_error);
        switchFilter = findViewById(R.id.act_main_switch_filter);
        ivSort = findViewById(R.id.act_main_iv_sort);
    }


    private void setupToolbar() {
        toolbar.setTitle("Amaros Title");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.filter:
                break;

            case R.id.sort:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupListeners() {

        switchFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        ivSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public void toggleLoadingState() {
        tvError.setVisibility(View.INVISIBLE);
        rv.setVisibility(View.INVISIBLE);
        pbLoading.setVisibility(View.VISIBLE);
    }


    @Override
    public void toggleErrorState() {
        tvError.setVisibility(View.VISIBLE);
        rv.setVisibility(View.INVISIBLE);
        pbLoading.setVisibility(View.INVISIBLE);
    }


    @Override
    public void toggleNormalState() {
        tvError.setVisibility(View.INVISIBLE);
        rv.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startDetailActivity(ResponseList.Product product, View ivProduct, View tvName, View tvPrice) {

        Pair<View, String> pairImage = new Pair<>(ivProduct, "product_image");
        Pair<View, String> pairName = new Pair<>(tvName, "product_name");
        Pair<View, String> pairPrice = new Pair<>(tvPrice, "product_price");

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairImage, pairName, pairPrice);

        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("Product", product);
        startActivity(i, options.toBundle());
    }

    @Override
    public void updateList(List<ResponseList.Product> list) {

    }
}
