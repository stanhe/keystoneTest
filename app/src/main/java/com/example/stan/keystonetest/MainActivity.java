package com.example.stan.keystonetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.stan.keystonetest.NetWork.NetOkhttp.MyOkhttp;
import com.example.stan.keystonetest.NetWork.NetVolley.MyVolley;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    String url = "http://192.168.1.11:3000/api";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText().equals(""))
//                    getDataVolley();
                        getDataOkhttp();
                else
                    textView.setText("");
            }
        });
    }
    private void getDataVolley(){
        MyVolley.INSTANCE.getString(this, url, new MyVolley.OnResult() {
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
    private void getDataOkhttp(){
        MyOkhttp.INSTANCE.getData(url, new MyOkhttp.OnResult() {
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
