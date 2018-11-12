package com.ian.ianrouter;

import android.app.Application;

import com.ian.route_api.RouteDemo;

/**
 * Created by Ian.Lu on 2018/11/12.
 * Project : IanRouter
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RouteDemo.init(this);
    }
}
