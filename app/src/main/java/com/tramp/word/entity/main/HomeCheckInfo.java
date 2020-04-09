package com.tramp.word.entity.main;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/15
 * version:1.0
 */

public class HomeCheckInfo {
    private int code;
    private ArrayList<item> books;
    private ArrayList<item> goods;
    private ArrayList<item> words;

    public ArrayList<item> getBooks() {
        return books;
    }

    public void setWords(ArrayList<item> words) {
        this.words = words;
    }

    public void setBooks(ArrayList<item> books) {
        this.books = books;
    }

    public void setGoods(ArrayList<item> goods) {
        this.goods = goods;
    }

    public ArrayList<item> getGoods() {
        return goods;
    }

    public ArrayList<item> getWords() {
        return words;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public class item{
        private int item_id;
        private String item_img;
        private String item_name;
        private String item_title;

        public int getItem_id() {
            return item_id;
        }

        public void setItem_id(int item_id) {
            this.item_id = item_id;
        }

        public String getItem_img() {
            return item_img;
        }

        public void setItem_img(String item_img) {
            this.item_img = item_img;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getItem_title() {
            return item_title;
        }

        public void setItem_title(String item_title) {
            this.item_title = item_title;
        }
    }
}
