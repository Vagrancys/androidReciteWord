package com.tramp.word.entity.group;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/11
 * version:1.0
 */

public class GroupLevelInfo {
    private int code;
    private Level level;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public class Level{
        private String group_name;
        private int group_level;
        private int group_all_star;
        private int group_level_star;
        private int group_level_all_star;
        private int group_now_star;
        private ArrayList<Item> items;

        public ArrayList<Item> getItems() {
            return items;
        }

        public void setItems(ArrayList<Item> items) {
            this.items = items;
        }

        public String getGroup_name() {
            return group_name;
        }

        public int getGroup_all_star() {
            return group_all_star;
        }

        public void setGroup_all_star(int group_all_star) {
            this.group_all_star = group_all_star;
        }

        public int getGroup_now_star() {
            return group_now_star;
        }

        public void setGroup_now_star(int group_now_star) {
            this.group_now_star = group_now_star;
        }

        public void setGroup_level(int group_level) {
            this.group_level = group_level;
        }

        public void setGroup_level_all_star(int group_level_all_star) {
            this.group_level_all_star = group_level_all_star;
        }

        public void setGroup_level_star(int group_level_star) {
            this.group_level_star = group_level_star;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public int getGroup_level() {
            return group_level;
        }

        public int getGroup_level_all_star() {
            return group_level_all_star;
        }

        public int getGroup_level_star() {
            return group_level_star;
        }

        public class Item{
            private int item_level;
            private int item_star;
            private int item_number;
            private int item_time;

            public int getItem_level() {
                return item_level;
            }

            public void setItem_level(int item_level) {
                this.item_level = item_level;
            }

            public int getItem_number() {
                return item_number;
            }

            public int getItem_star() {
                return item_star;
            }

            public int getItem_time() {
                return item_time;
            }

            public void setItem_number(int item_number) {
                this.item_number = item_number;
            }

            public void setItem_star(int item_star) {
                this.item_star = item_star;
            }

            public void setItem_time(int item_time) {
                this.item_time = item_time;
            }
        }
    }
}
