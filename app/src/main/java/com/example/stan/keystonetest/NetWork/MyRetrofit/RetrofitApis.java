package com.example.stan.keystonetest.NetWork.MyRetrofit;
import android.util.Log;

import com.example.stan.keystonetest.NetWork.OnResult;
import com.example.stan.keystonetest.model.LoginResponse;
import com.example.stan.keystonetest.model.User;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by stan on 16/8/18.
 */
public class RetrofitApis {
    public static void login(String data, OnResult result){
        MyRetrofit.postService().login(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        Log.e("ST","loginResponse_status "+loginResponse.status);
                        Log.e("ST","loginResponse_message "+loginResponse.message);
                        Log.e("ST","loginResponse_name "+loginResponse.data.playerName);
                    }
                });
    }
    public static void login(User user){
        MyRetrofit.postService().login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        Log.e("ST","loginResponse_status "+loginResponse.status);
                        Log.e("ST","loginResponse_message "+loginResponse.message);
                        Log.e("ST","loginResponse_name "+loginResponse.data.playerName);
                    }
                });
    }
}
