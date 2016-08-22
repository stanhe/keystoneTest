package com.example.stan.keystonetest.NetWork.NetVolley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stan.keystonetest.NetWork.OnResult;
import com.example.stan.keystonetest.Utils.AESUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by stan on 16/8/10.
 */
public enum  MyVolley {
    INSTANCE;
    static RequestQueue mQueue;
    public void getString(Context context, String url, final String data, final OnResult result){
        if (mQueue==null)
            mQueue = Volley.newRequestQueue(context.getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response);
                        result.handleResult(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
                    result.handleError(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                String result = AESUtils.handleCryptData(data,AESUtils.ENCRYPT); //encrypt req data
                map.put("data",result);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(stringRequest);
        mQueue.start();
    }

}
