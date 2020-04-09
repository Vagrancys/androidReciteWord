package com.tramp.word.entity.pk;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/18
 * version:1.0
 */
public class PkSearchInfo {
    private int code;
    private Search search;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public Search getSearch() {
        return search;
    }

    public class Search{
        private int search_id;
        private String user_name;
        private String user_img;
        private String user_sign;
        private String user_id;

        public String getUser_sign() {
            return user_sign;
        }

        public void setUser_sign(String user_sign) {
            this.user_sign = user_sign;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public void setSearch_id(int search_id) {
            this.search_id = search_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setUser_img(String user_img) {
            this.user_img = user_img;
        }

        public String getUser_name() {
            return user_name;
        }

        public int getSearch_id() {
            return search_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getUser_img() {
            return user_img;
        }

    }
}
