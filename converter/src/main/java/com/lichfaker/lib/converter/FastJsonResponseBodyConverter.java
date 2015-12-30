package com.lichfaker.lib.converter;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Type;

import okio.BufferedSource;
import okio.Okio;
import retrofit.Converter;

/**
 * @author LichFaker on 15/12/28.
 * @Email lichfaker@gmail.com
 */
public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private Type mType;

    public FastJsonResponseBodyConverter(Type type) {
        this.mType = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        return JSON.parseObject(tempStr, mType);
    }
}
