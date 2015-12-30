package com.lichfaker.example;


import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * @author LichFaker on 15/12/28.
 * @Email lichfaker@gmail.com
 */
public interface ApiService {

    @POST("index.php")
    Call test(@Body SimpleData data);

    @POST("index.php")
    Observable<SimpleData> rxTest(@Body SimpleData data);

}
