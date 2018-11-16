package com.ian.ianrouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ian.route_annotation.Route;

@Route("ian://test")
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        int key = getIntent().getExtras().getInt("int_key");

        TextView textView = findViewById(R.id.textView);
        textView.setText("接受到的参数 : " + key);
    }
}
