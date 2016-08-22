package com.example.stan.keystonetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stan.keystonetest.NetWork.MyRetrofit.RetrofitApis;
import com.example.stan.keystonetest.NetWork.NetOkhttp.MyOkhttp;
import com.example.stan.keystonetest.NetWork.NetVolley.MyVolley;
import com.example.stan.keystonetest.NetWork.OnResult;
import com.example.stan.keystonetest.Utils.AESUtils;
import com.example.stan.keystonetest.model.LoginResponse;
import com.example.stan.keystonetest.model.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    EditText editText0;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    TextView textView;
    TextView textView1;
    Button button;
    Button button1;
    String url = "http://192.168.1.11:3000/api";
    String url1 = "http://192.168.1.11:3000/api/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        textView1 = (TextView) findViewById(R.id.text1);
        editText0 = (EditText) findViewById(R.id.editText0);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                postMethodOne(); //unencrypted * okHttp
//                postMethodTwo(); //encrypt model *okHttp

//                postMethodThree(); //unencrypted * retrofit
//                postMethodFour(); //encrypt req data * retrofit -- unrealized

                 getDataVolley();    //encrypt req data * volley
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    textView1.setText(AESUtils.handleCryptData(textView.getText().toString(),AESUtils.DECRYPT));
            }
        });
    }
    private void postMethodOne(){
        if (editText2.getText().toString().trim().equals("")){
                    getDataOkhttp(url1,editText0.getText().toString(),editText1.getText().toString(),editText2.getText().toString());
                }else {
                    getDataOkhttp(url, editText0.getText().toString(), editText1.getText().toString(), editText2.getText().toString());
                }
    }

    private void postMethodTwo(){
        JSONObject object = new JSONObject();
        try {
            object.put("name",editText0.getText().toString());
            object.put("password",editText1.getText().toString());
            object.put("type",editText2.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        postData(url,object.toString());
    }

    private void postMethodThree(){
        User user = new User(editText0.getText().toString(),editText1.getText().toString(),editText2.getText().toString());
        RetrofitApis.login(user);
    }

    private void postMethodFour(){
        User user = new User(editText0.getText().toString(),editText1.getText().toString(),editText2.getText().toString());
        Gson gson = new Gson();
        String data = gson.toJson(user,User.class);
        Log.e("ST","data-----: "+data);
        Log.e("ST","-----data---- : "+AESUtils.handleCryptData(data,AESUtils.ENCRYPT));
        RetrofitApis.login(data,myResult);
    }

    private void getDataVolley(){
        JSONObject object = new JSONObject();
        try {
            object.put("name",editText0.getText().toString());
            object.put("password",editText1.getText().toString());
            object.put("type",editText2.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyVolley.INSTANCE.getString(this, url,object.toString(), myResult);
    }




    private void getDataOkhttp(String url,String name,String password ,String type){
        MyOkhttp.INSTANCE.getData(url, name, password , type, myResult);
    }

    private void postData(String url,String data){
        MyOkhttp.INSTANCE.getData(url, data, myResult);
    }
    OnResult myResult = new OnResult() {
        @Override
        public void handleResult(final String data) {
            textView.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(data);
                    Gson gson = new Gson();
                    LoginResponse response = gson.fromJson(AESUtils.handleCryptData(data,AESUtils.DECRYPT),LoginResponse.class);
                    Log.e("ST","response_status "+response.status );
                    Log.e("ST","response_message "+response.message );
                    Log.e("ST","response_name "+response.data.playerName );
                    Log.e("ST","response_level "+response.data.playerLevel );
                    Log.e("ST","response_token "+response.data.token );
                }
            });
        }

        @Override
        public void handleError(Exception error) {
            textView.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText("");
                }
            });
        }
    };
}
