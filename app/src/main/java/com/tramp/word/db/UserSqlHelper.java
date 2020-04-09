package com.tramp.word.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tramp.word.entity.DefaultBookInfo;
import com.tramp.word.entity.book.WordQueryInfo;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/22
 * version:1.0
 */

public class UserSqlHelper {
    public final String LOG="cursor";
    private final String User_Db="user";
    private final String User_Table="user";
    private final String Book_Table="book";
    private SQLiteOpenHelper dbHelper;
    private Context mContext;

    public UserSqlHelper(Context context){
        this.mContext=context;
        dbHelper=new DateBaseHelper(context,User_Db,null,1);
    }

    public void insert(ContentValues values){
        if(isExist()){
            update(values);
        }else{
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            db.insert(User_Table,null,values);
            db.close();
        }
    }

    public void update(ContentValues values){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.update(User_Table,values,"_id=?",new String[] {"1"});
        db.close();
    }

    public Boolean isExist() {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select user_id from user where _id=?", new String[]{"1"});
        cursor.moveToFirst();
        if (cursor.getCount()!=0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public Cursor UserQuery(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from user where _id=?",new String[]{"1"});
        cursor.moveToFirst();
        return cursor;
    }

    public int UserId(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select user_id from user where _id=?",new String[]{"1"});
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            int id=cursor.getInt(cursor.getColumnIndex("user_id"));
            cursor.close();
            db.close();
            return id;
        }else{
            cursor.close();
            db.close();
            return 0;
        }
    }

    public int WordId(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from user where _id=?",new String[]{"1"});
        int word_id;
        if(cursor != null&&cursor.getCount()>0){
            cursor.moveToFirst();
            word_id=cursor.getInt(cursor.getColumnIndex("recited_book"));
            cursor.close();
        }else{
            word_id = 0;
        }

        return word_id;
    }

    public int QueryWordGate(int word_id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select finish_gate from book where book_id=?",new String[]{String.valueOf(word_id)});
        cursor.moveToFirst();
        int gate=cursor.getInt(cursor.getColumnIndex("finish_gate"));
        cursor.close();
        return gate;
    }

    public void DeleteUser(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(User_Table,"_id=?",new String[]{"1"});
        db.close();
    }

    public Cursor NewBook(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from book inner join user on user.recited_book=book.book_id",null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor BookList(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from book",null);
        cursor.moveToFirst();
        return cursor;
    }

    public Boolean isExistBook(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select recited_book from user where _id=?",new String[]{"1"});
        cursor.moveToFirst();
        if(cursor.getCount()>0&&cursor.getInt(cursor.getColumnIndex("recited_book"))==0){
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }
    }

    public Boolean isBookEmpty(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from book",null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    public Boolean isWordEmpty(int book_id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT word_book FROM word WHERE word_book=?",new String[]{String.valueOf(book_id)});
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }
    }

    public Boolean isBook(int book_id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select book_id from book where book_id=?",new String[]{String.valueOf(book_id)});
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    /*public void insertAllBook(ArrayList<DefaultBookInfo> newBooks){
        if(null==newBooks||newBooks.size()<=0){
            return;
        }
        SQLiteDatabase db=null;
        try {
            db=dbHelper.getWritableDatabase();
            String sql="insert into book (book_id,book_name,book_img,total_num," +
                    "new_num,level_star,star,all_star," +
                    "started_at,finished_at,last_recited_at,now_gate,finish_gate,good,number,summary,book_url) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            SQLiteStatement stat=db.compileStatement(sql);
            db.beginTransaction();
            for (DefaultBookInfo book: newBooks){
                stat.bindLong(1,book.getBook_id());
                stat.bindString(2,book.getBook_name());
                stat.bindString(3,book.getBook_img());
                stat.bindLong(4,book.getTotal_num());
                stat.bindLong(5,book.getNew_num());
                stat.bindLong(6,book.getLevel_star());
                stat.bindLong(7,book.getStar());
                stat.bindLong(8,book.getAll_star());
                stat.bindLong(9,book.getStarted_at());
                stat.bindLong(10,book.getFinished_at());
                stat.bindLong(11,book.getLast_finished_at());
                stat.bindLong(12,book.getNow_gate());
                stat.bindLong(13,book.getFinish_gate());
                stat.bindLong(14,book.getGood());
                stat.bindLong(15,book.getNumber());
                stat.bindString(16,book.getSummary());
                stat.bindString(17,book.getBook_url());
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(db !=null){
                    db.endTransaction();
                    db.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }*/

    public Cursor BookQuery(int book){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from book where book_id=?",new String[]{String.valueOf(book)});
        cursor.moveToFirst();
        return cursor;
    }

    public int BookSize(int book_user){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT _id FROM book where book_user=?",new String[]{String.valueOf(book_user)});
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            int number=cursor.getCount();
            cursor.close();
            return number;
        }else{
            cursor.close();
            return 0;
        }
    }

    public void insertBook(DefaultBookInfo book){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        //词书id
        values.put("book_id",book.getBook_id());
        //词书用户
        values.put("book_user",book.getBook_user());
        //词书名称
        values.put("book_name",book.getBook_name());
        //词书图片
        values.put("book_img",book.getBook_img());
        //词书路径
        values.put("book_url",book.getBook_url());
        //总单词数
        values.put("total_num",book.getTotal_num());
        //当前背完
        values.put("new_num",book.getNew_num());
        //当前星星数
        values.put("level_star",book.getLevel_star());
        //词书星星数
        values.put("star",book.getStar());
        //总星星数
        values.put("all_star",book.getAll_star());
        //添加时间
        values.put("started_at",book.getStarted_at());
        //结束时间
        values.put("finished_at",book.getFinished_at());
        //最后复习时间
        values.put("last_finished_at",book.getLast_finished_at());
        //现在关卡
        values.put("now_gate",book.getNow_gate());
        //总关卡
        values.put("finish_gate",book.getFinish_gate());
        //精彩数
        values.put("good",book.getGood());
        //人数
        values.put("number",book.getNumber());
        //简介
        values.put("summary",book.getSummary());
        //判断是否通关
        values.put("finished",book.getFinished());
        db.insert(Book_Table,null,values);
        db.close();
    }

    public void DeleteBook(int book_id,int user_id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.beginTransaction();
        db.delete("book","book_id=? and book_user=?",new String[]{String.valueOf(book_id),String.valueOf(user_id)});
        db.endTransaction();
        db.close();
    }

    public void DeleteWord(int book_id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete("word","word_book=?",new String[]{String.valueOf(book_id)});
        db.close();
    }

    public void DeleteGate(int book_id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.beginTransaction();
        db.delete("gate","gate_uid=?",new String[]{String.valueOf(book_id)});
        db.endTransaction();
        db.close();
    }

    public void insertWord(WordQueryInfo.Word book){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("word_book",book.getWord_book());
        values.put("word_gate",book.getWord_gate());
        values.put("word_name",book.getWord_name());
        values.put("word_music",book.getWord_music());
        values.put("word_music_url",book.getWord_music_url());
        values.put("word_meaning",book.getWord_meaning());
        values.put("word_sentence",book.getWord_sentence());
        values.put("word_sentence_meaning",book.getWord_sentence_meaning());
        values.put("word_sentence_url",book.getWord_sentence_url());
        values.put("word_root",book.getWord_root());
        values.put("word_error",0);
        values.put("word_error_text","");
        values.put("word_life",0);
        values.put("word_letter",book.getWord_letter());
        values.put("word_study",0);
        values.put("word_time",0);
        values.put("word_last_time",0);
        db.insert("word",null,values);
        Log.e(LOG,"word="+1);
        db.close();
    }

    public Cursor WordManageAll(int word_id,int manage){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=null;
        if(manage==0){
            cursor=db.query("word",new String[]{"word_id","word_name"},
                    "word_book=? and word_life=? and word_study=?",new String[]{String.valueOf(word_id),"0","1"},
                    null,null,"word_letter desc",null);
        }else if(manage==1){
            cursor=db.query("word",new String[]{"word_id","word_name"},
                    "word_book=? and word_life=? and word_study=?",new String[]{String.valueOf(word_id),"1","1"},
                    null,null,"word_letter desc",null);
        }
        return cursor;
    }

    public void UpdateWordLife(int word_id,int manage){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("word_life",manage);
        db.update("word",values,"word_id=?",new String[]{String.valueOf(word_id)});
    }

    public void UpdateWordError(int word_id,int word_error,String word_error_text){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("word_error",word_error);
        values.put("word_error_text",word_error_text);
        db.update("word",values,"word_id=?",new String[]{String.valueOf(word_id)});
    }

    public void UpdateWordWin(int word_id,int word_error,String word_error_text,long time, long last_time){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("word_error",word_error);
        values.put("word_error_text",word_error_text);
        values.put("word_study",1);
        values.put("word_time",time);
        values.put("word_last_time",last_time);
        db.update("word",values,"word_id=?",new String[]{String.valueOf(word_id)});
    }

    public void UpdateWordGate(int word_id,int word_gate,int star){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select gate_id from gate where gate_uid=? and gate_number=?",new String[]{String.valueOf(word_id),String.valueOf(word_gate)});
        cursor.moveToFirst();
        ContentValues values=new ContentValues();
        if(cursor.getCount()==0){
            values.put("gate_uid",word_id);
            values.put("gate_number",word_gate);
            values.put("gate_star",star);
            db.insert("gate",null,values);
        }else{
            values.put("gate_star",star);
            db.update("gate",values,"gate_uid=? and gate_number=?",new String[]{String.valueOf(word_id),String.valueOf(word_gate)});
        }
        cursor.close();
    }

    public Cursor WordContentAll(int word_id,int word_status,int word_gate){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String where;
        String[] whereArgs;
        String orderBy;
        switch (word_status){
            case 1:
                where="word_book=? and word_life=? and word_study=?";
                whereArgs=new String[]{String.valueOf(word_id),"1","1"};
                orderBy="word_gate desc";
                break;
            case 2:
                where="word_book=? and word_life=? and word_study=?";
                whereArgs=new String[]{String.valueOf(word_id),"1","1"};
                orderBy="word_letter desc";
                break;
            case 3:
                where="word_book=? and word_study=?";
                whereArgs=new String[]{String.valueOf(word_id),"1"};
                orderBy="word_time desc";
                break;
            case 4:
                where="word_book=? and word_study=?and word_life=?";
                whereArgs=new String[]{String.valueOf(word_id),"0","0"};
                orderBy="word_gate desc";
                break;
            case 5:
                where="word_book=? and word_study=? and word_life=?";
                whereArgs=new String[]{String.valueOf(word_id),"0","0"};
                orderBy="word_letter desc";
                break;
            case 6:
                where="word_book=? and word_study=? and word_life=? and word_time=?";
                whereArgs=new String[]{String.valueOf(word_id),"1","0","102345"};
                orderBy="word_id desc";
                break;
            case 7:
                where="word_book=? and word_gate=?";
                whereArgs=new String[]{String.valueOf(word_id),String.valueOf(word_gate)};
                orderBy=null;
                break;
            default:
                where="word_book=?";
                whereArgs=new String[]{String.valueOf(word_id)};
                orderBy="word_id desc";
                break;
        }
        return db.query("word",new String[]{"*"},where,whereArgs,null,null,orderBy,null);
    }

    public Cursor ItemGateAll(int word_id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        return db.rawQuery("SELECT * FROM gate where gate_uid=?",new String[]{String.valueOf(word_id)});
    }

    public Cursor GateAll(int word_book,int word_gate){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        return db.rawQuery("SELECT * FROM word where word_book=? and word_gate=?",new String[]{String.valueOf(word_book),String.valueOf(word_gate)});
    }

    public void UpdateBookRecite(int book_id,ContentValues values){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.update("book",values,"book_id=?",new String[]{String.valueOf(book_id)});
    }

    public int WordAllCount(int word_id,long time){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM word where word_book=? and word_time<?",new String[]{String.valueOf(word_id),String.valueOf(time)});
        if(cursor !=null&&cursor.getCount()>0){
            int count=cursor.getCount();
            cursor.close();
            return count;
        }else{
            cursor.close();
            return 0;
        }
    }

    public int WordStudyCount(int word_id,long time,int study){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM word where word_book=? and word_time<? and word_study=?",new String[]{String.valueOf(word_id),String.valueOf(time),String.valueOf(study)});
        if(cursor !=null&&cursor.getCount()>0){
            int count=cursor.getCount();
            cursor.close();
            return count;
        }else{
            cursor.close();
            return 0;
        }
    }

    public Cursor WordReviseAll(int word_id,long time){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String where="word_book=? and word_study=? and word_life=? and word_time<?";
        String[] whereArgs=new String[]{String.valueOf(word_id),"1","0",String.valueOf(time)};
        String orderBy="word_id desc";
        return db.query("word",new String[]{"*"},where,whereArgs,null,null,orderBy,null);
    }

    public void UpdateWordTime(int word_id,int time,int last_time){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("word_time",time);
        values.put("word_last_time",last_time);
        db.update("word",values,"word_id=?",new String[]{String.valueOf(word_id)});
    }
}






































