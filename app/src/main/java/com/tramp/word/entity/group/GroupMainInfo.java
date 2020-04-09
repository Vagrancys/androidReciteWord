package com.tramp.word.entity.group;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/09
 * version:1.0
 */

public class GroupMainInfo {
    private int code;
    private Main mains;
    private Add add;
    private ArrayList<medal> medal;
    private Rank rank;

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Add getAdd() {
        return add;
    }

    public void setAdd(Add add) {
        this.add = add;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMains(Main mains) {
        mains = mains;
    }

    public Main getMains() {
        return mains;
    }

    public ArrayList<GroupMainInfo.medal> getMedal() {
        return medal;
    }

    public void setMedal(ArrayList<GroupMainInfo.medal> medal) {
        this.medal = medal;
    }

    public class Main{
        private int group_id;
        private String group_img;
        private String group_name;
        private int group_level;
        private int group_new;
        private int group_star;
        private int group_all_star;
        private int group_comment;
        private int group_admin;

        public int getGroup_admin() {
            return group_admin;
        }

        public void setGroup_admin(int group_admin) {
            this.group_admin = group_admin;
        }

        public String getGroup_img() {
            return group_img;
        }

        public void setGroup_img(String group_img) {
            this.group_img = group_img;
        }

        public int getGroup_level() {
            return group_level;
        }

        public void setGroup_level(int group_level) {
            this.group_level = group_level;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_star(int group_star) {
            this.group_star = group_star;
        }

        public int getGroup_all_star() {
            return group_all_star;
        }

        public void setGroup_all_star(int group_all_star) {
            this.group_all_star = group_all_star;
        }

        public void setGroup_comment(int group_comment) {
            this.group_comment = group_comment;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public void setGroup_new(int group_new) {
            this.group_new = group_new;
        }

        public int getGroup_comment() {
            return group_comment;
        }

        public int getGroup_new() {
            return group_new;
        }

        public int getGroup_star() {
            return group_star;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }


    }

    public class medal{
        private String medal_img;

        public String getMedal_img() {
            return medal_img;
        }

        public void setMedal_img(String medal_img) {
            this.medal_img = medal_img;
        }
    }

    public class Add{
        private String user_name;
        private String group_name;
        private String group_rank;
        private int group_star;
        private String admin_name;
        private String admin_img;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public int getGroup_star() {
            return group_star;
        }

        public void setAdmin_img(String admin_img) {
            this.admin_img = admin_img;
        }

        public String getAdmin_img() {
            return admin_img;
        }

        public void setAdmin_name(String admin_name) {
            this.admin_name = admin_name;
        }

        public String getAdmin_name() {
            return admin_name;
        }

        public void setGroup_rank(String group_rank) {
            this.group_rank = group_rank;
        }

        public String getGroup_rank() {
            return group_rank;
        }

        public void setGroup_star(int group_star) {
            this.group_star = group_star;
        }
    }

    public class Rank{
        private String user_rank;
        private String user_img;
        private int user_star;
        private int user_star_up;

        public void setUser_img(String user_img) {
            this.user_img = user_img;
        }

        public void setUser_rank(String user_rank) {
            this.user_rank = user_rank;
        }

        public void setUser_star(int user_star) {
            this.user_star = user_star;
        }

        public String getUser_rank() {
            return user_rank;
        }

        public int getUser_star() {
            return user_star;
        }

        public void setUser_star_up(int user_star_up) {
            this.user_star_up = user_star_up;
        }

        public int getUser_star_up() {
            return user_star_up;
        }

        public String getUser_img() {
            return user_img;
        }
    }
}






































