package com.tramp.word.entity.main;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/15
 * version:1.0
 */

public class HomeFindInfo {
    private int code;
    private find finds;

    public find getFinds() {
        return finds;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setFinds(find finds) {
        this.finds = finds;
    }

    public class find{
        private String tag_title;
        private ArrayList<banner> banners;
        private ArrayList<word> words;
        private ArrayList<item> items;
        private ArrayList<item> goods;

        public ArrayList<item> getGoods() {
            return goods;
        }

        public void setGoods(ArrayList<item> goods) {
            this.goods = goods;
        }

        public String getTag_title() {
            return tag_title;
        }

        public void setTag_title(String tag_title) {
            this.tag_title = tag_title;
        }

        public ArrayList<banner> getBanners() {
            return banners;
        }

        public void setBanners(ArrayList<banner> banners) {
            this.banners = banners;
        }

        public ArrayList<item> getItems() {
            return items;
        }

        public void setItems(ArrayList<item> items) {
            this.items = items;
        }

        public ArrayList<word> getWords() {
            return words;
        }

        public void setWords(ArrayList<word> words) {
            this.words = words;
        }

        public class banner{
            private String banner_url;

            private String banner_img;

            public String getBanner_url() {
                return banner_url;
            }

            public void setBanner_url(String banner_url) {
                this.banner_url = banner_url;
            }


            public String getBanner_img() {
                return banner_img;
            }

            public void setBanner_img(String banner_img) {
                this.banner_img = banner_img;
            }

        }

        public class word{
            private int word_id;
            private int word_hot;
            private String word_img;
            private String word_name;
            private String word_number;

            public int getWord_hot() {
                return word_hot;
            }

            public int getWord_id() {
                return word_id;
            }

            public void setWord_hot(int word_hot) {
                this.word_hot = word_hot;
            }

            public String getWord_img() {
                return word_img;
            }

            public void setWord_id(int word_id) {
                this.word_id = word_id;
            }

            public String getWord_name() {
                return word_name;
            }

            public void setWord_img(String word_img) {
                this.word_img = word_img;
            }

            public String getWord_number() {
                return word_number;
            }

            public void setWord_name(String word_name) {
                this.word_name = word_name;
            }

            public void setWord_number(String word_number) {
                this.word_number = word_number;
            }
        }

        public class item{
            private int good_id;
            private String good_name;
            private String good_img;
            private String good_title;

            public int getGood_id() {
                return good_id;
            }

            public void setGood_id(int good_id) {
                this.good_id = good_id;
            }

            public String getGood_img() {
                return good_img;
            }

            public void setGood_img(String good_img) {
                this.good_img = good_img;
            }

            public String getGood_name() {
                return good_name;
            }

            public void setGood_name(String good_name) {
                this.good_name = good_name;
            }

            public String getGood_title() {
                return good_title;
            }

            public void setGood_title(String good_title) {
                this.good_title = good_title;
            }
        }

    }

}
