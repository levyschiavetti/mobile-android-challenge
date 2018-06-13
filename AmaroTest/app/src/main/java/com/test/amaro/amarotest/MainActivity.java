package com.test.amaro.amarotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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
    }

    private void bindViews() {
        rv = findViewById(R.id.act_main_rv);
        toolbar = findViewById(R.id.act_main_tb);
    }
}
