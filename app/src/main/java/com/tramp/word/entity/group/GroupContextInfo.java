package com.tramp.word.entity.group;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/04
 * version:1.0
 */

public class GroupContextInfo {
    private int code;
    private group groups;
    private ArrayList<Item> learns;
    private ArrayList<Item> active;
    private ArrayList<Item> lists;
    public class group{
        private int group_status;
        private int group_money;

        public int getGroup_money() {
            return group_money;
        }

        public int getGroup_status() {
            return group_status;
        }

        public void setGroup_money(int group_money) {
            this.group_money = group_money;
        }

        public void setGroup_status(int group_status) {
            this.group_status = group_status;
        }
    }

    public group getGroups() {
        return groups;
    }

    public void setGroups(group groups) {
        this.groups = groups;
    }

    public ArrayList<Item> getActive() {
        return active;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Item> getLearns() {
        return learns;
    }

    public void setActive(ArrayList<Item> active) {
        this.active = active;
    }

    public ArrayList<Item> getLists() {
        return lists;
    }

    public void setLearns(ArrayList<Item> learns) {
        this.learns = learns;
    }

    public int getCode() {
        return code;
    }

    public void setLists(ArrayList<Item> lists) {
        this.lists = lists;
    }

    public class Item{
        private int group_id;
        private int group_member;
        private int group_all_member;
        private String group_avatar;
        private String group_name;
        private int group_done;
        private String group_tag;
        private String group_star;
        private String group_summary;

        public int getGroup_all_member() {
            return group_all_member;
        }

        public void setGroup_all_member(int group_all_member) {
            this.group_all_member = group_all_member;
        }

        public int getGroup_done() {
            return group_done;
        }

        public void setGroup_avatar(String group_avatar) {
            this.group_avatar = group_avatar;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_done(int group_done) {
            this.group_done = group_done;
        }

        public int getGroup_member() {
            return group_member;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public String getGroup_star() {
            return group_star;
        }

        public void setGroup_member(int group_member) {
            this.group_member = group_member;
        }

        public String getGroup_avatar() {
            return group_avatar;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_star(String group_star) {
            this.group_star = group_star;
        }

        public String getGroup_summary() {
            return group_summary;
        }

        public void setGroup_summary(String group_summary) {
            this.group_summary = group_summary;
        }

        public String getGroup_tag() {
            return group_tag;
        }

        public void setGroup_tag(String group_tag) {
            this.group_tag = group_tag;
        }
    }
}
