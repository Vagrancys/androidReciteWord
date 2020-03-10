package com.tramp.word.widget.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2019/2/6.
 */

public class CommonSqlHelper extends SQLiteOpenHelper {
    private static final String CREATE_BOOK="create table book("
            +"id integer primary key autoincrement,"
            +"author text,"
            +"price real,"
            +"pages integer"
            +"name text)";
    private Context mContext;
    public CommonSqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}















