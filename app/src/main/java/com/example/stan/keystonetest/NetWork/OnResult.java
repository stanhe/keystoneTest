package com.example.stan.keystonetest.NetWork;



/**
 * Created by stan on 16/8/18.
 */
public interface OnResult{
    void handleResult(String data);
    void handleError(Exception error);
}
