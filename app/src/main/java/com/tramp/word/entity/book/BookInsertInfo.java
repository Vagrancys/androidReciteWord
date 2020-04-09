package com.tramp.word.entity.book;

import com.tramp.word.entity.DefaultBookInfo;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/29
 * version:1.0
 */

public class BookInsertInfo {
    private int code;
    private int book_status;
    private DefaultBookInfo book;

    public DefaultBookInfo getBook() {
        return book;
    }

    public void setBook(DefaultBookInfo book) {
        this.book = book;
    }

    public int getBook_status() {
        return book_status;
    }

    public void setBook_status(int book_status) {
        this.book_status = book_status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
