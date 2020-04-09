package com.tramp.word.entity.user;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/22
 * version:1.0
 */

public class LoginInfo {
    private int code;
    private String user_name;
    private String token;
    private int user_id;
    private String avatar;
    private String last_login_at;
    private int recited_book;
    private int book_status;

    public int getBook_status() {
        return book_status;
    }

    public void setBook_status(int book_status) {
        this.book_status = book_status;
    }

    public int getRecited_book() {
        return recited_book;
    }

    public void setRecited_book(int recited_book) {
        this.recited_book = recited_book;
    }

    public String getLast_login_at() {
        return last_login_at;
    }

    public void setLast_login_at(String last_login_at) {
        this.last_login_at = last_login_at;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}






