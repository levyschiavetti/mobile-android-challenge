package com.test.amaro.amarotest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
                          implements MainContract.View {


    private MainContract.Presenter presenter = new MainPresenter(this);
    private RecyclerView rv;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        setupToolbar();
        setupRecyclerView();
    }

    private void bindViews() {
        rv = findViewById(R.id.act_main_rv);
        toolbar = findViewById(R.id.act_main_tb);
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
}
