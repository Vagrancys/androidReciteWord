package com.tramp.word;

import android.app.Application;

/**
 * Created by Administrator on 2019/1/8.
 */

public class WordAPP extends Application {
    private static WordAPP mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public static WordAPP getInstance(){
        return mInstance;
    }

}


