package com.ian.ianrouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ian.route_annotation.Route;

@Route("ian://test_web")
public class TestWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web);
    }
}
