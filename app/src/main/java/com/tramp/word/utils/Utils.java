package com.tramp.word.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    public static Boolean getNetWork(Activity activity){
        ConnectivityManager cm=(ConnectivityManager) activity.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        return networkInfo !=null;
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

        return Formatter.formatFileSize(context,totalSize);
    }

    public static String getAvailableSize(Context context){
        File path= Environment.getExternalStorageDirectory();
        StatFs statFs=new StatFs(path.getPath());
        @SuppressWarnings("deprection")
        long blockSize=statFs.getBlockSizeLong();
        @SuppressWarnings("deprection")
        long availableBlock=statFs.getAvailableBlocksLong();

        long availableSize=blockSize*availableBlock;

        return Formatter.formatFileSize(context,availableSize);
    }

    public static String getRowTotalSize(Context context){
        File path= Environment.getDataDirectory();
        StatFs statFs=new StatFs(path.getPath());
        @SuppressWarnings("deprection")
        long blockSize=statFs.getBlockSizeLong();
        @SuppressWarnings("deprection")
        long totalBlock=statFs.getBlockCountLong();

        long totalSize=blockSize*totalBlock;

        return Formatter.formatFileSize(context,totalSize);
    }

    public static String getRowAvailableSize(Context context){
        File path= Environment.getDataDirectory();
        StatFs statFs=new StatFs(path.getPath());
        @SuppressWarnings("deprection")
        long blockSize=statFs.getBlockSizeLong();
        @SuppressWarnings("deprection")
        long availableBlock=statFs.getAvailableBlocksLong();

        long availableSize=blockSize*availableBlock;

        return Formatter.formatFileSize(context,availableSize);
    }

    public static int getGroupLevelImg(int level){
        switch (level){
            case 1:
                return R.drawable.group_level_small_1;
            case 2:
                return R.drawable.group_level_small_2;
            case 3:
                return R.drawable.group_level_small_3;
            case 4:
                return R.drawable.group_level_small_4;
            case 5:
                return R.drawable.group_level_small_5;
            case 6:
                return R.drawable.group_level_small_6;
            case 7:
                return R.drawable.group_level_small_7;
            case 8:
                return R.drawable.group_level_small_8;
            case 9:
                return R.drawable.group_level_small_9;
            case 10:
                return R.drawable.group_level_small_10;
            case 11:
                return R.drawable.group_level_small_11;
            case 12:
                return R.drawable.group_level_small_12;
            case 13:
                return R.drawable.group_level_small_13;
            case 14:
                return R.drawable.group_level_small_14;
            case 15:
                return R.drawable.group_level_small_15;
            case 16:
                return R.drawable.group_level_small_16;
            case 17:
                return R.drawable.group_level_small_17;
            case 18:
                return R.drawable.group_level_small_18;
        }
        return R.drawable.group_level_small_1;
    }

}




