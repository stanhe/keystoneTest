package com.example.stan.keystonetest.NetWork.MyRetrofit;


import com.example.stan.keystonetest.model.LoginResponse;
import com.example.stan.keystonetest.model.User;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by stan on 16/8/18.
 */
public interface RetrofitService {

    @POST("api")
    Observable<LoginResponse> login(
            @Body User user
    );


    @FormUrlEncoded //POST表单的方式上传文件
    @POST("api")
    Observable<LoginResponse> login(
//            @Field("name") String name, // 添加FormUrlEncoded，然后通过@Field添加参数
//            @Field("password") String password,
//            @Field("type") String type
            @Field("one") String data
    );

}
