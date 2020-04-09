package com.tramp.word.entity.user;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/01
 * version:1.0
 */

public class FriendSearchInfo {
    private int code;
    private ArrayList<search> search;

    public ArrayList<search> getSearch() {
        return search;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setSearch(ArrayList<search> search) {
        this.search = search;
    }

    public class search{
        private int user_id;
        private int user_avatar;
        private int user_name;
        private int user_status;

        public int getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(int user_avatar) {
            this.user_avatar = user_avatar;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_name(int user_name) {
            this.user_name = user_name;
        }

        public int getUser_name() {
            return user_name;
        }

        public void setUser_status(int user_status) {
            this.user_status = user_status;
        }

        public int getUser_status() {
            return user_status;
        }
    }
}
