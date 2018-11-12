package com.ian.route_api;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Ian.Lu on 2018/11/12.
 * Project : IanRouter
 */
public class RouteDemo {

    private static HashMap<String, Class> activityMap = new HashMap<>();
    private static Application mApplication;

    public static void init(Application application) {
        mApplication = application;
        try {
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

    public static void open(String url) {
        for (String key : activityMap.keySet()) {
            if (url.equals(key)) {
                Intent intent = new Intent(mApplication, activityMap.get(key));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mApplication.startActivity(intent);
            }
        }
    }
}
