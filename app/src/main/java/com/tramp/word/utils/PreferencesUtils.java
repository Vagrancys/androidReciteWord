package com.tramp.word.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.lang.UCharacter;
import android.preference.PreferenceManager;

import com.tramp.word.WordAPP;

/**
 * Created by Administrator on 2019/1/8.
 */

public class PreferencesUtils {
    public static void putBoolean(String key,boolean value){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(WordAPP.getInstance());
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public static Boolean getBoolean(String key, Boolean defValue){
        return PreferenceManager.getDefaultSharedPreferences(WordAPP.getInstance()).getBoolean(key,defValue);
    }

    public static void putInt(String key,int value){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(WordAPP.getInstance());
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }
    public static int getInt(String key,int defValue){
        return PreferenceManager.getDefaultSharedPreferences(WordAPP.getInstance()).getInt(key,defValue);
    }
}
