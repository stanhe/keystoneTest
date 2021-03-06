package com.example.stan.keystonetest.NetWork.NetOkhttp;

import android.util.Log;

import com.example.stan.keystonetest.NetWork.OnResult;
import com.example.stan.keystonetest.Utils.AESUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


/**
 * Created by stan on 16/8/10.
 */
public enum MyOkhttp {
    INSTANCE;
    OkHttpClient mOkHttpClient;

    public void getData(String url,String name,String password ,String type, final OnResult result){
        if (mOkHttpClient==null)
            mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("name",name);
        builder.add("password",password);
        if (!type.equals(""))
        builder.add("type",type);
        final Request request = new Request.Builder().url(url).post(builder.build()).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                result.handleError(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String data = response.body().string();
                Log.e("ST","data: "+data);
                result.handleResult(data);
            }
        });
    }

    /**
     *  加密模式~
     * @param url
     * @param data
     * @param result
     */
    public void getData(String url, String data, final OnResult result){
        if (mOkHttpClient == null)
            mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        data = AESUtils.handleCryptData(data,AESUtils.ENCRYPT);
        Log.e("ST","postData : "+data);
        builder.add("data",data);
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                result.handleError(e);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                String data = response.body().string();
                Log.e("ST","data: "+data);
                result.handleResult(data);
            }
        });
    }
}
