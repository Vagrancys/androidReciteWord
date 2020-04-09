package com.tramp.word.entity.group;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/06
 * version:1.0
 */

public class GroupRankInfo {
    private int code;
    private ArrayList<Rank> ranks;

    public ArrayList<Rank> getRanks() {
        return ranks;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setRanks(ArrayList<Rank> ranks) {
        this.ranks = ranks;
    }

    public class Rank{
        private int group_id;
        private String group_img;
        private String group_name;
        private int group_level;
        private String group_all_star;
        private String group_hot;
        private int group_day;
        private String group_star;
        private int group_number;

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_star(String group_star) {
            this.group_star = group_star;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public void setGroup_all_star(String group_all_star) {
            this.group_all_star = group_all_star;
        }

        public void setGroup_day(int group_day) {
            this.group_day = group_day;
        }

        public void setGroup_hot(String group_hot) {
            this.group_hot = group_hot;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public void setGroup_img(String group_img) {
            this.group_img = group_img;
        }

        public void setGroup_level(int group_level) {
            this.group_level = group_level;
        }

        public void setGroup_number(int group_number) {
            this.group_number = group_number;
        }

        public String getGroup_all_star() {
            return group_all_star;
        }

        public int getGroup_day() {
            return group_day;
        }

        public String getGroup_hot() {
            return group_hot;
        }

        public int getGroup_id() {
            return group_id;
        }

        public int getGroup_level() {
            return group_level;
        }

        public int getGroup_number() {
            return group_number;
        }

        public String getGroup_star() {
            return group_star;
        }

        public String getGroup_img() {
            return group_img;
        }

    }

}
