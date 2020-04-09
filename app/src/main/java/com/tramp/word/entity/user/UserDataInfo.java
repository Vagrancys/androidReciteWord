package com.tramp.word.entity.user;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/20
 * version:1.0
 */

public class UserDataInfo {
    private int code;
    private Data data;
    private ArrayList<Day> day;

    public Data getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setDay(ArrayList<Day> day) {
        this.day = day;
    }

    public ArrayList<Day> getDay() {
        return day;
    }

    public class Data{
        private int data_word;
        private int data_day;
        private int data_pk;
        private String date_title;

        public String getDate_title() {
            return date_title;
        }

        public void setDate_title(String date_title) {
            this.date_title = date_title;
        }

        public int getData_day() {
            return data_day;
        }

        public void setData_day(int data_day) {
            this.data_day = data_day;
        }

        public void setData_pk(int data_pk) {
            this.data_pk = data_pk;
        }

        public void setData_word(int data_word) {
            this.data_word = data_word;
        }

        public int getData_pk() {
            return data_pk;
        }

        public int getData_word() {
            return data_word;
        }
    }

    public class Day{
        private int day_status;
        private int day_number;

        public void setDay_number(int day_number) {
            this.day_number = day_number;
        }

        public int getDay_number() {
            return day_number;
        }

        public void setDay_status(int day_status) {
            this.day_status = day_status;
        }

        public int getDay_status() {
            return day_status;
        }

    }
}
