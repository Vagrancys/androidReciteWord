package com.tramp.word.entity.group;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/09
 * version:1.0
 */

public class GroupMainRankInfo {
    private int code;
    private Rank rank;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public class Rank{
        private ArrayList<Item> items;
        private ArrayList<Item> notitems;

        public ArrayList<Item> getNotitems() {
            return notitems;
        }

        public void setNotitems(ArrayList<Item> notitems) {
            this.notitems = notitems;
        }

        public ArrayList<Item> getItems() {
            return items;
        }

        public void setItems(ArrayList<Item> items) {
            this.items = items;
        }


        public class Item{
            private int user_id;
            private String user_img;
            private String user_name;
            private int user_star;
            private int user_praise;

            public int getUser_star() {
                return user_star;
            }

            public void setUser_star(int user_star) {
                this.user_star = user_star;
            }

            public String getUser_img() {
                return user_img;
            }

            public void setUser_img(String user_img) {
                this.user_img = user_img;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_name() {
                return user_name;
            }

            public int getUser_praise() {
                return user_praise;
            }

            public void setUser_praise(int user_praise) {
                this.user_praise = user_praise;
            }
        }
    }
}






