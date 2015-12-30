package com.lichfaker.example.util;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.concurrent.TimeUnit;



/**
 * OkHttp 单例工具类
 */
public class OkHttpUtil {

    private static OkHttpClient singleton;

    private static long RESPONSE_CACHE_SIZE = 10*1024*1024; // 默认缓存20M
    private static long HTTP_CONNECT_TIMEOUT = 20*1000;
    private static long HTTP_READ_TIMEOUT = 20*1000;

    public static OkHttpClient getInstance(Context context, String cacheDirPath) {
        if (singleton == null) {
            synchronized (OkHttpUtil.class) {
                if (singleton == null) {
                    File cacheDir = new File(context.getCacheDir(), cacheDirPath);
                    singleton = new OkHttpClient();
                    singleton.setCache(new Cache(cacheDir, RESPONSE_CACHE_SIZE));
                    singleton.setConnectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
                    singleton.setReadTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);
                }
            }
        }
        return singleton;
    }
}
