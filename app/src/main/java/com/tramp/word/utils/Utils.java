package com.tramp.word.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.widget.Toast;

import com.tramp.word.R;

import java.io.File;

/**
 * Created by Administrator on 2019/1/10.
 */

public class Utils {
    private static Toast mToast;
    private Utils() {
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static void ShowToast(Context context,String text){

        if(mToast==null){
            mToast=Toast.makeText(context,text,Toast.LENGTH_SHORT);
        }else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public static void StarActivityInAnim(Activity activity){
        activity.overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
    }

    public static String getTotalSize(Context context){
        File path= Environment.getExternalStorageDirectory();
        StatFs statFs=new StatFs(path.getPath());
        @SuppressWarnings("deprection")
        long blockSize=statFs.getBlockSizeLong();
        @SuppressWarnings("deprection")
        long totalBlock=statFs.getBlockCountLong();

        long totalSize=blockSize*totalBlock;

        String total= Formatter.formatFileSize(context,totalSize);
        return total;
    }

    public static String getAvailableSize(Context context){
        File path= Environment.getExternalStorageDirectory();
        StatFs statFs=new StatFs(path.getPath());
        @SuppressWarnings("deprection")
        long blockSize=statFs.getBlockSizeLong();
        @SuppressWarnings("deprection")
        long availableBlock=statFs.getAvailableBlocksLong();

        long availableSize=blockSize*availableBlock;

        String available=Formatter.formatFileSize(context,availableSize);
        return available;
    }

    public static String getRowTotalSize(Context context){
        File path= Environment.getDataDirectory();
        StatFs statFs=new StatFs(path.getPath());
        @SuppressWarnings("deprection")
        long blockSize=statFs.getBlockSizeLong();
        @SuppressWarnings("deprection")
        long totalBlock=statFs.getBlockCountLong();

        long totalSize=blockSize*totalBlock;

        String total= Formatter.formatFileSize(context,totalSize);
        return total;
    }

    public static String getRowAvailableSize(Context context){
        File path= Environment.getDataDirectory();
        StatFs statFs=new StatFs(path.getPath());
        @SuppressWarnings("deprection")
        long blockSize=statFs.getBlockSizeLong();
        @SuppressWarnings("deprection")
        long availableBlock=statFs.getAvailableBlocksLong();

        long availableSize=blockSize*availableBlock;

        String available=Formatter.formatFileSize(context,availableSize);
        return available;
    }
}




