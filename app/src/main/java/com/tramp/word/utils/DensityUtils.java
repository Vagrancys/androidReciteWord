package com.tramp.word.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Administrator on 2019/2/18.
 */

public class DensityUtils {
    private DensityUtils(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static int dp2px(Context context,float deVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                deVal,context.getResources().getDisplayMetrics());
    }
}
