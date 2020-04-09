package com.tramp.word.entity.book;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/06/09
 * version:1.0
 */
public class BookSearchInfo {
    private int code;
    private ArrayList<Language> languages;

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<Language> languages) {
        this.languages = languages;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public class Language{
        private ArrayList<Book> books;

        public ArrayList<Book> getBooks() {
            return books;
        }

        public void setBooks(ArrayList<Book> books) {
            this.books = books;
        }

        public class Book{
            private int book_id;
            private String book_name;
            private String book_img;
            private int book_total;
            private int book_good;
            private int book_number;
            private int book_select;
            private String book_summary;

            public int getBook_select() {
                return book_select;
            }

            public void setBook_select(int book_select) {
                this.book_select = book_select;
            }

            public int getBook_good() {
                return book_good;
            }

            public void setBook_good(int book_good) {
                this.book_good = book_good;
            }

            public void setBook_number(int book_number) {
                this.book_number = book_number;
            }

            public int getBook_number() {
                return book_number;
            }

            public void setBook_summary(String book_summary) {
                this.book_summary = book_summary;
            }

            public int getBook_total() {
                return book_total;
            }

            public void setBook_total(int book_total) {
                this.book_total = book_total;
            }

            public String getBook_summary() {
                return book_summary;
            }

            public void setBook_id(int book_id) {
                this.book_id = book_id;
            }

            public int getBook_id() {
                return book_id;
            }

            public void setBook_img(String book_img) {
                this.book_img = book_img;
            }

            public String getBook_name() {
                return book_name;
            }

            public String getBook_img() {
                return book_img;
            }

            public void setBook_name(String book_name) {
                this.book_name = book_name;
            }
        }
    }

}
