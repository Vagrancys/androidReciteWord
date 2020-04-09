package com.tramp.word.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2019/1/21.
 */

public class AssetsUtils {
    public static InputStream getAssetsOpen(Context context,String value){
        InputStream resources =null;
        try {
            resources=context.getAssets().open(value);
        }catch (IOException e){
            e.printStackTrace();
        }
        return resources;
    }
}
