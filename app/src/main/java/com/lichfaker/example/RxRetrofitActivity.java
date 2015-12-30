package com.lichfaker.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.lichfaker.example.util.RetrofitUtil;
import com.lichfaker.retrofit2.example.R;

public class RxRetrofitActivity extends AppCompatActivity {

    private ApiService service;

    private static final String BASE_URL = "http://192.168.0.101:8080/";
    private static final String CACHE_DIR = "/sdcard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_retrofit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        service = RetrofitUtil.createApi(getApplicationContext(), BASE_URL, CACHE_DIR, ApiService.class);

    }

}
