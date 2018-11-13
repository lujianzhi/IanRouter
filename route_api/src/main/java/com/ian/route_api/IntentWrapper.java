package com.ian.route_api;

import android.os.Bundle;

/**
 * Created by Ian.Lu on 2018/11/13.
 * Project : IanRouter
 */
public class IntentWrapper {
    private Bundle mBundle;
    private String mOriginalUrl;
    private RouteDemo mRouteDemo;

    private static IntentWrapper instance = new IntentWrapper();

    public static IntentWrapper getInstance() {
        return instance;
    }

    public IntentWrapper build(RouteDemo routeDemo, String url) {
        mRouteDemo = routeDemo;
        mOriginalUrl = url;
        mBundle = new Bundle();
        return this;
    }

    public IntentWrapper withString(String key, String value) {
        mBundle.putString(key, value);
        return this;
    }

    public IntentWrapper withInt(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public void openWithBundle() {
        mRouteDemo.open(mOriginalUrl, mBundle);
    }

    public void open() {
        mRouteDemo.open(mOriginalUrl);
    }
}
