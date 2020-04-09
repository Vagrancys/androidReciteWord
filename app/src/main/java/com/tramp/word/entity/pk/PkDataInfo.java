package com.tramp.word.entity.pk;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/31
 * version:1.0
 */

public class PkDataInfo {
    private int code;
    private ArrayList<Data> data;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public class Data{
        private int data_id;
        private int data_user;
        private String data_avatar;
        private String data_name;
        private String data_number;
        private String data_num;
        private String data_word;
        private String data_time;
        private int data_status;

        public int getData_id() {
            return data_id;
        }

        public int getData_user() {
            return data_user;
        }

        public void setData_user(int data_user) {
            this.data_user = data_user;
        }

        public String getData_avatar() {
            return data_avatar;
        }

        public void setData_avatar(String data_avatar) {
            this.data_avatar = data_avatar;
        }

        public String getData_name() {
            return data_name;
        }

        public void setData_name(String data_name) {
            this.data_name = data_name;
        }

        public String getData_num() {
            return data_num;
        }

        public String getData_number() {
            return data_number;
        }

        public void setData_num(String data_num) {
            this.data_num = data_num;
        }

        public void setData_number(String data_number) {
            this.data_number = data_number;
        }

        public void setData_id(int data_id) {
            this.data_id = data_id;
        }

        public int getData_status() {
            return data_status;
        }

        public String getData_time() {
            return data_time;
        }

        public void setData_status(int data_status) {
            this.data_status = data_status;
        }


        public void setData_time(String data_time) {
            this.data_time = data_time;
        }

        public void setData_word(String data_word) {
            this.data_word = data_word;
        }

        public String getData_word() {
            return data_word;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
