package com.lichfaker.lib.converter;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * @author LichFaker on 15/12/28.
 * @Email lichfaker@gmail.com
 */
public class FastJsonConverterFactory extends Converter.Factory {

    public static FastJsonConverterFactory create() {
        return new FastJsonConverterFactory();
    }

    /**
     * Create a {@link Converter} for converting an HTTP response body to {@code type} or null if it
     * cannot be handled by this factory.
     */
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new FastJsonResponseBodyConverter<>(type);
    }

    /**
     * Create a {@link Converter} for converting {@code type} to an HTTP request body or null if it
     * cannot be handled by this factory.
     */
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return new FastJsonRequestBodyConverter<>();
    }
}

