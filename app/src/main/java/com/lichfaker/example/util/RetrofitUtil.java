package com.lichfaker.example.util;

import android.content.Context;

import com.lichfaker.lib.converter.FastJsonConverterFactory;

import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

//import com.lichfaker.retrofit2.converter.FastJsonConverterFactory;

/**
 * Retrofit 单例工具类
 */
public class RetrofitUtil {

    private static Retrofit singleton;

    /**
     * @param context
     * @param baseUrl   HOST地址， 以/结尾
     * @param cacheDir  缓存目录
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T createApi(Context context, String baseUrl, String cacheDir,  Class<T> clazz) {
        if (singleton == null) {
            synchronized (RetrofitUtil.class) {
                if (singleton == null) {
                    singleton = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(FastJsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(OkHttpUtil.getInstance(context, cacheDir))
                            .build();
                }
            }
        }
        return singleton.create(clazz);
    }
}
