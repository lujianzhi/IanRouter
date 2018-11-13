package com.ian.ianrouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ian.route_api.RouteDemo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteDemo.getInstance().build("ian://test").withInt("int_key", 123).openWithBundle();
//                RouteDemo.getInstance().build("ian://test?int_key=123").open();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RouteDemo.getInstance().build("ian://test_web").withString("url", "https://www.baidu.com/").openWithBundle();
                RouteDemo.getInstance().build("ian://test_web?url=https://www.baidu.com/").open();
            }
        });
    }
}
