package com.tramp.word.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tramp.word.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/22
 * version:1.0
 */

public class DateBaseHelper extends SQLiteOpenHelper{
    private String Table;
    private Context mContext;
    public DateBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext=context;
        Table=name;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        if(!tabIsExist("user",db)){
            executeRawSql(db,"user.db");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (newVersion){
            case 3:
                executeRawSql(db,"user.db");
                break;
            case 4:
                break;
        }
    }

    public void executeRawSql(SQLiteDatabase db,String schemeName){
        BufferedReader in=null;
        try {
            in=new BufferedReader(new InputStreamReader(mContext.getResources().openRawResource(R.raw.user_init)));
            String line;
            String buffer="";
            while ((line=in.readLine())!=null){
                buffer+=line;
                if(line.trim().endsWith(";")){
                    db.execSQL(buffer.replace(";",""));
                    buffer="";
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(in!=null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public boolean tabIsExist(String tabName,SQLiteDatabase db){
        boolean result=false;
        if(tabName==null){
            return false;
        }
        Cursor cursor=null;
        try {
            String sql="select count(*) as c form sqlite_master where type = 'table'and name ='"+tabName.trim()+"'";
            cursor=db.rawQuery(sql,null);
            if(cursor.moveToNext()){
                int count=cursor.getInt(0);
                if(count>0){
                    result=true;
                }
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}



































































