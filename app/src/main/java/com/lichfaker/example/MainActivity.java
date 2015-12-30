package com.lichfaker.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lichfaker.example.util.JsonUtil;
import com.lichfaker.example.util.OkHttpUtil;
import com.lichfaker.lib.converter.FastJsonConverterFactory;
import com.lichfaker.retrofit2.example.R;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private SimpleData request;

    private Retrofit retrofit1;
    private Retrofit retrofit2;

    private ApiService service1;
    private ApiService service2;

    private Call call1;
    private Call call2;

    private static final String BASE_URL = "http://192.168.0.101:8080/";
    private static final String CACHE_DIR = "/sdcard";

    private final String jsonStr = "{\"createTime\":1451401876366,\"email\":\"lichfaker@gmail.com\",\"flag\":true,\"groupId\":11,\"id\":1,\"image\":\"./image/default.png\",\"name\":\"lichfaker\",\"nickName\":\"LichFaker\",\"phone\":\"1234\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initView();
    }

    private void initData() {
        request = new SimpleData();
        request.setId(1);
        request.setGroupId(11);
        request.setCreateTime(System.currentTimeMillis());
        request.setEmail("lichfaker@gmail.com");
        request.setFlag(true);
        request.setImage("./image/default.png");
        request.setName("lichfaker");
        request.setNickName("LichFaker");
        request.setPhone("1234");
        Log.d("LichFaker", JSONObject.toJSONString(request));
        retrofit1 = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(FastJsonConverterFactory.create())
                .client(OkHttpUtil.getInstance(getApplicationContext(), CACHE_DIR))
                .build();

        service1 = retrofit1.create(ApiService.class);

        call1 = service1.test(request);

        retrofit2 = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpUtil.getInstance(getApplicationContext(), CACHE_DIR))
                .build();

        service2 = retrofit2.create(ApiService.class);

        call2 = service2.test(request);
    }

    private void initView() {
        findViewById(R.id.mainFastjsonBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long time = System.currentTimeMillis();
                call1.clone().enqueue(new retrofit.Callback<SimpleData>() {
                    @Override
                    public void onResponse(retrofit.Response<SimpleData> response, Retrofit retrofit) {
                        Log.e("LichFaker", "retrofit1:" + (System.currentTimeMillis() - time));
                        if (response.isSuccess()) {
                            Log.d("LichFaker", JSONObject.toJSONString(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("LichFaker", t.getMessage());
                    }

                });
            }
        });

        findViewById(R.id.mainGsonBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long time = System.currentTimeMillis();
                call2.clone().enqueue(new retrofit.Callback<SimpleData>() {
                    @Override
                    public void onResponse(retrofit.Response<SimpleData> response, Retrofit retrofit) {
                        Log.e("LichFaker", "retrofit2:" + (System.currentTimeMillis() - time));
                        if (response.isSuccess()) {
                            Log.d("LichFaker", JSONObject.toJSONString(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("LichFaker", t.getMessage());
                    }

                });
            }
        });

        findViewById(R.id.mainFastTestBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long total1 = 0;
                long total2 = 0;
                long time1 = System.currentTimeMillis();
                for (int i = 0; i < 50; i++) {
                    total1 += fastStr2Obj();
                    total2 += fastObj2Str();
                }
                long offTime = System.currentTimeMillis() - time1;
                Log.e("LichFaker Test", "fastStr2Obj:" + total1);
                Log.e("LichFaker Test", "fastObj2Str:" + total2);
                Log.e("LichFaker Test", "real total time:" + offTime);
            }
        });

        findViewById(R.id.mainGsonTestBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long total1 = 0;
                long total2 = 0;
                long time1 = System.currentTimeMillis();
                for (int i = 0; i < 50; i++) {
                    total1 += gsonStr2Obj();
                    total2 += gsonObj2Str();
                }
                long offTime = System.currentTimeMillis() - time1;
                Log.e("LichFaker Test", "gsonStr2Obj:" + total1);
                Log.e("LichFaker Test", "gsonObj2Str:" + total2);
                Log.e("LichFaker Test", "real total time:" + offTime);
            }
        });

        findViewById(R.id.mainRxBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RxRetrofitActivity.class);
                startActivity(intent);
            }
        });
    }

    private long fastStr2Obj() {
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            JSON.parseObject(jsonStr, SimpleData.class);
        }
        return System.currentTimeMillis() - time1;
    }

    private long fastObj2Str() {
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            JSONObject.toJSONString(request);
        }
        return System.currentTimeMillis() - time1;
    }

    private long gsonStr2Obj() {
        Gson gson = JsonUtil.create().getGson();
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            gson.fromJson(jsonStr, SimpleData.class);
        }
        return System.currentTimeMillis() - time1;
    }

    private long gsonObj2Str() {
        Gson gson = JsonUtil.create().getGson();
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            gson.toJson(request);
        }
        return System.currentTimeMillis() - time1;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (call1 != null) {
            call1.cancel();
        }
        if (call2 != null) {
            call2.cancel();
        }
    }
}
