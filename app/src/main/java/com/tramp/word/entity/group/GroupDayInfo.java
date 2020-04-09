package com.tramp.word.entity.group;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/11
 * version:1.0
 */

public class GroupDayInfo {
    private int code;
    private Day days;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDays(Day days) {
        this.days = days;
    }

    public Day getDays() {
        return days;
    }

    public class Day{
        private int group_status;
        private String group_day_star;
        private String group_star;
        private int group_now_star;
        private int group_all_star;
        private int group_member;
        private int group_not_member;
        private int user_status;
        private String user_star;
        private int user_praise;
        private int group_level_status;
        private ArrayList<Praise> praises;
        private ArrayList<List> lists;
        private int group_level;

        public int getGroup_level() {
            return group_level;
        }

        public void setGroup_level(int group_level) {
            this.group_level = group_level;
        }

        public int getGroup_level_status() {
            return group_level_status;
        }

        public void setGroup_level_status(int group_level_status) {
            this.group_level_status = group_level_status;
        }

        public String getUser_star() {
            return user_star;
        }

        public void setGroup_all_star(int group_all_star) {
            this.group_all_star = group_all_star;
        }

        public ArrayList<List> getLists() {
            return lists;
        }

        public void setGroup_day_star(String group_day_star) {
            this.group_day_star = group_day_star;
        }

        public ArrayList<Praise> getPraises() {
            return praises;
        }

        public void setGroup_member(int group_member) {
            this.group_member = group_member;
        }

        public int getGroup_all_star() {
            return group_all_star;
        }

        public void setGroup_not_member(int group_not_member) {
            this.group_not_member = group_not_member;
        }

        public String getGroup_day_star() {
            return group_day_star;
        }

        public void setGroup_now_star(int group_now_star) {
            this.group_now_star = group_now_star;
        }

        public int getGroup_member() {
            return group_member;
        }

        public int getGroup_not_member() {
            return group_not_member;
        }

        public void setGroup_star(String group_star) {
            this.group_star = group_star;
        }

        public int getGroup_now_star() {
            return group_now_star;
        }

        public void setGroup_status(int group_status) {
            this.group_status = group_status;
        }

        public String getGroup_star() {
            return group_star;
        }

        public void setLists(ArrayList<List> lists) {
            this.lists = lists;
        }

        public int getGroup_status() {
            return group_status;
        }

        public void setPraises(ArrayList<Praise> praises) {
            this.praises = praises;
        }

        public void setUser_praise(int user_praise) {
            this.user_praise = user_praise;
        }

        public int getUser_praise() {
            return user_praise;
        }

        public void setUser_star(String user_star) {
            this.user_star = user_star;
        }

        public int getUser_status() {
            return user_status;
        }

        public void setUser_status(int user_status) {
            this.user_status = user_status;
        }

        public class List{
            private String user_name;
            private String user_img;

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public void setUser_img(String user_img) {
                this.user_img = user_img;
            }

            public String getUser_img() {
                return user_img;
            }
        }

        public class Praise{
            private String user_img;

            public String getUser_img() {
                return user_img;
            }

            public void setUser_img(String user_img) {
                this.user_img = user_img;
            }
        }
    }
}
