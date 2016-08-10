package com.example.stan.keystonetest.NetWork.NetOkhttp;

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
    public interface OnResult{
        void handleResult(String data);
        void handleError(Exception error);
    }
    public void getData(String url, final OnResult result){
        if (mOkHttpClient==null)
            mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("name","stan2");
        builder.add("type","action2");
        final Request request = new Request.Builder().url(url).post(builder.build()).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                result.handleError(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String data = response.body().toString();
                result.handleResult(data);
            }
        });
    }
}
