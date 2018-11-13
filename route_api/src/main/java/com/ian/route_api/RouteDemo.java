package com.ian.route_api;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Ian.Lu on 2018/11/12.
 * Project : IanRouter
 */
public class RouteDemo {

    private HashMap<String, Class> activityMap = new HashMap<>();
    private Application mApplication;

    private static RouteDemo instance = new RouteDemo();

    public static RouteDemo getInstance() {
        return instance;
    }

    public void init(Application application) {
        mApplication = application;
        try {
            //通过反射调用AutoCreateModuleActivityMap_app类的方法,并给activityMap赋值
            Class clazz = Class.forName("com.ian.ianrouter.AutoCreateModuleActivityMap_app");
            Method method = clazz.getMethod("initActivityMap", HashMap.class);
            method.invoke(null, activityMap);
            for (String key : activityMap.keySet()) {
                Log.i("Ian", "activityMap = " + activityMap.get(key));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public IntentWrapper build(String url) {
        return IntentWrapper.getInstance().build(this, url);
    }

    private boolean checkUrlPath(String targetUrl, String matchUrl) {
        Uri targetUri = Uri.parse(targetUrl);
        Uri matchUri = Uri.parse(matchUrl);

        if (targetUri.getScheme().equals(matchUri.getScheme()) &&
                targetUri.getHost().equals(matchUri.getHost())) {
            return TextUtils.equals(targetUri.getPath(), matchUri.getPath());
        } else {
            return false;
        }
    }

    private Intent parseParams(Intent intent, String targetUrl) {
        Uri uri = Uri.parse(targetUrl);
        Set<String> queryParameterNames = uri.getQueryParameterNames();
        for (String paramName : queryParameterNames) {
            intent.putExtra(paramName, uri.getQueryParameter(paramName));
        }
        return intent;
    }

    public void open(String url, Bundle bundle) {
        for (String key : activityMap.keySet()) {
            if (checkUrlPath(url, key)) {
                Intent intent = new Intent(mApplication, activityMap.get(key));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                mApplication.startActivity(intent);
                break;
            }
        }
    }

    public void open(String url) {
        for (String key : activityMap.keySet()) {
            if (checkUrlPath(url, key)) {
                Intent intent = new Intent(mApplication, activityMap.get(key));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent = parseParams(intent, url);
                mApplication.startActivity(intent);
                break;
            }
        }
    }
}
