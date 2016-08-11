package com.example.stan.keystonetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.stan.keystonetest.NetWork.NetOkhttp.MyOkhttp;
import com.example.stan.keystonetest.NetWork.NetVolley.MyVolley;


public class MainActivity extends AppCompatActivity {
    EditText editText0;
    EditText editText1;
    EditText editText2;
    TextView textView;
    Button button;
    String url = "http://192.168.1.11:3000/api";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        editText0 = (EditText) findViewById(R.id.editText0);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText().equals(""))
                    getDataOkhttp(url,editText0.getText().toString(),editText1.getText().toString(),editText2.getText().toString());
                else
                    textView.setText("");
            }
        });
    }
    private void getDataVolley(String url,String name,String password ,String type){
        MyVolley.INSTANCE.getString(this, url,name,password,type, new MyVolley.OnResult() {
            @Override
            public void handleResult(String data) {
                textView.setText(data);
            }

            @Override
            public void handleError(VolleyError error) {
                textView.setText("error");
            }
        });
    }
    private void getDataOkhttp(String url,String name,String password ,String type){
        MyOkhttp.INSTANCE.getData(url, name, password , type, new MyOkhttp.OnResult() {
            @Override
            public void handleResult(final String data) {
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(data);
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
        });
    }

}
