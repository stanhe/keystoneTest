package com.example.stan.keystonetest.NetWork.MyRetrofit;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by stan on 16/8/18.
 */
public class MyRetrofit {
    static RetrofitService service;
    public static final String url = "http://192.168.1.11:3000/";
    public static RetrofitService postService(){
        if (service==null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(RetrofitService.class);
        }
        return service;
    }


}
