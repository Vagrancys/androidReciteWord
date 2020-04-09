package com.tramp.word.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/06/08
 * version:1.0
 */
public class AppManagerUtils {
    private static Stack<Activity> activityStack;
    private static AppManagerUtils instance;

    //单一实例
    public static AppManagerUtils getAppManager(){
        if(instance==null){
            instance=new AppManagerUtils();
        }
        return instance;
    }

    //获取堆栈
    public Stack getActivityStack(){
        return activityStack;
    }

    //添加Activity到堆栈
    public void addActivity(Activity activity){
        if(activityStack==null){
            activityStack=new Stack<>();
        }
        activityStack.add(activity);
    }

    //获取当前Activity
    public Activity currentActivity(){
        return activityStack.lastElement();
    }

    //结束当前Activity
    public void finishActivity(){
        Activity activity=activityStack.lastElement();
        finishActivity(activity);
    }

    //结束指定的Activity
    public void finishActivity(Activity activity){
        if(activity!=null){
            activityStack.remove(activity);
            activity.finish();
            activity=null;
        }
    }

    //结束指定类名的Activity
    public void finishActivity(Class<?> cls){
        for (Activity activity:activityStack){
            if(activity.getClass().equals(cls)){
                finishActivity(activity);
            }
        }
    }

    //结束所有Activity
    public void finishAllActivity(){
        for (int i=0,size=activityStack.size();i<size;i++){
            if(null!=activityStack.get(i)){
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    //退出应用程序
    public void AppExit(Context context){
        try {
            finishAllActivity();
            ActivityManager activityManager=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}





