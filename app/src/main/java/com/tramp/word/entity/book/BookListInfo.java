package com.tramp.word.entity.book;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/26
 * version:1.0
 */

public class BookListInfo {
    private int code;
    private ArrayList<One> ones;
    private ArrayList<Two> twos;

    public ArrayList<One> getOnes() {
        return ones;
    }

    public void setOnes(ArrayList<One> ones) {
        this.ones = ones;
    }

    public void setTwos(ArrayList<Two> twos) {
        this.twos = twos;
    }

    public ArrayList<Two> getTwos() {
        return twos;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class One{
        private int one_id;
        private String one_name;
        private int one_number;
        private int one_language;
        private int status;

        public int getOne_number() {
            return one_number;
        }

        public void setOne_number(int one_number) {
            this.one_number = one_number;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getOne_language() {
            return one_language;
        }

        public void setOne_language(int one_language) {
            this.one_language = one_language;
        }

        public int getOne_id() {
            return one_id;
        }

        public void setOne_id(int one_id) {
            this.one_id = one_id;
        }

        public String getOne_name() {
            return one_name;
        }

        public void setOne_name(String one_name) {
            this.one_name = one_name;
        }
    }

    public static class Two{
        private int two_id;
        private String two_name;
        private int two_language;
        private int two_one;
        private int two_number;
        private int status;

        public int getTwo_number() {
            return two_number;
        }

        public void setTwo_number(int two_number) {
            this.two_number = two_number;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTwo_id() {
            return two_id;
        }

        public void setTwo_id(int two_id) {
            this.two_id = two_id;
        }

        public void setTwo_name(String two_name) {
            this.two_name = two_name;
        }

        public String getTwo_name() {
            return two_name;
        }

        public int getTwo_language() {
            return two_language;
        }

        public int getTwo_one() {
            return two_one;
        }

        public void setTwo_language(int two_language) {
            this.two_language = two_language;
        }

        public void setTwo_one(int two_one) {
            this.two_one = two_one;
        }
    }
}
