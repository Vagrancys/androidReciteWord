package com.tramp.word.entity.book;

import com.tramp.word.entity.DefaultBookInfo;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/26
 * version:1.0
 */

public class BookInfo {
    private int code;
    private ArrayList<DefaultBookInfo> BookList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<DefaultBookInfo> getBookList() {
        return BookList;
    }

    public void setBookList(ArrayList<DefaultBookInfo> bookList) {
        BookList = bookList;
    }

}
