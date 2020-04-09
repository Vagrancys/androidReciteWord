package com.tramp.word.entity.book;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/27
 * version:1.0
 */

public class BookItemInfo {
    private int code;
    public ArrayList<Book> items;

    public ArrayList<Book> getItems() {
        return items;
    }

    public void setItems(ArrayList<Book> items) {
        this.items = items;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public class Book{
        private int book_id;
        private String book_name;
        private String book_img;
        private int book_total;
        private int book_star;
        private int book_gate;
        private int book_good;
        private int book_one;
        private int book_two;
        private int book_language;
        private int book_number;
        private String book_summary;

        public int getBook_language() {
            return book_language;
        }

        public void setBook_language(int book_language) {
            this.book_language = book_language;
        }

        public void setBook_one(int book_one) {
            this.book_one = book_one;
        }

        public int getBook_one() {
            return book_one;
        }

        public void setBook_two(int book_two) {
            this.book_two = book_two;
        }

        public int getBook_two() {
            return book_two;
        }

        public int getBook_gate() {
            return book_gate;
        }

        public void setBook_gate(int book_gate) {
            this.book_gate = book_gate;
        }

        public void setBook_good(int book_good) {
            this.book_good = book_good;
        }

        public void setBook_id(int book_id) {
            this.book_id = book_id;
        }

        public void setBook_img(String book_img) {
            this.book_img = book_img;
        }

        public void setBook_name(String book_name) {
            this.book_name = book_name;
        }

        public void setBook_number(int book_number) {
            this.book_number = book_number;
        }

        public void setBook_star(int book_star) {
            this.book_star = book_star;
        }

        public void setBook_summary(String book_summary) {
            this.book_summary = book_summary;
        }

        public void setBook_total(int book_total) {
            this.book_total = book_total;
        }

        public int getBook_good() {
            return book_good;
        }

        public int getBook_id() {
            return book_id;
        }

        public int getBook_number() {
            return book_number;
        }

        public int getBook_star() {
            return book_star;
        }

        public int getBook_total() {
            return book_total;
        }

        public String getBook_img() {
            return book_img;
        }

        public String getBook_name() {
            return book_name;
        }

        public String getBook_summary() {
            return book_summary;
        }
    }
}

