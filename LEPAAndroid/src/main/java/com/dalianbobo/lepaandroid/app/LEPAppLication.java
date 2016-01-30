package com.dalianbobo.lepaandroid.app;

import android.app.Activity;
import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dalianbobo.lepaandroid.cache.fresco.ConfigConstants;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.LinkedList;
import java.util.List;

/**
 * 全局初始化
 * Created by Administrator on 2015/11/24 on 10:23.
 * Author Clown Fish
 */
public class LEPAppLication extends Application {
    public static LEPAppLication sInstance;
    public static boolean isLogin = true;
    public static boolean toLogin = false;
    public static boolean isToLogin = false;
    //Activity的集合
    private List<Activity> mList = new LinkedList<Activity>();
    private RequestQueue mQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Fresco.initialize(getApplicationContext(), ConfigConstants.getImagePipelineConfig(this));
    }

    /**
     * 程序控制器返回单例实例
     *
     * @return
     */
    public static synchronized LEPAppLication getInstance() {
        return sInstance;
    }

    /**
     * 退出后关闭所有存在的activity
     */
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mList.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mList) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    // 添加 Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    /**
     * 返回凌空请求队列,将创建队列是否为空
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mQueue == null) {
            // 1
            // 2
            synchronized (LEPAppLication.class) {
                if (mQueue == null) {
                    mQueue = Volley.newRequestQueue(getApplicationContext());

                }
            }
        }
        return mQueue;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
