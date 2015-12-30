package com.lichfaker.example.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * json 数据解析工具单例类
 * 基于Gson 2.5 实现
 * @author LichFaker
 */
public class JsonUtil {

    private final Gson gson;

    private static JsonUtil mInstance;

    public static JsonUtil create() {
        if (mInstance == null) {
            mInstance = new JsonUtil();
        }
        return mInstance;
    }

    private JsonUtil() {
        gson = new Gson();
    }

    public Gson getGson() {
        return gson;
    }

    /**
     * json 字符串 转换成对应的实体类
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T str2Bean(String jsonStr, Class<T> clazz) {
        return gson.fromJson(jsonStr, clazz);
    }
    public <T> T str2Bean(String jsonStr, Type type) {
        return gson.fromJson(jsonStr, type);
    }

    /**
     * 实体类转换成json字符串
     * @param obj
     * @return
     */
    public String bean2Jsonstr(Object obj) {
        return gson.toJson(obj);
    }
}
