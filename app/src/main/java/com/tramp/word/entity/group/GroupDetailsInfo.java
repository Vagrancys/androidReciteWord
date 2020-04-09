package com.tramp.word.entity.group;

import java.util.List;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/07
 * version:1.0
 */

public class GroupDetailsInfo {
    private int code;
    private Details details;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public Details getDetails() {
        return details;
    }

    public class Details{
        private int group_id;
        private String group_avatar;
        private String group_name;
        private int group_level;
        private String group_member;
        private String group_all_member;
        private String group_admin;
        private int group_admin_id;
        private String group_star;
        private String group_medal;
        private List<Medal> medals;
        private String group_tag;
        private String group_time;
        private String group_summary;
        private List<String> tags;
        private int group_status;

        public int getGroup_status() {
            return group_status;
        }

        public void setGroup_status(int group_status) {
            this.group_status = group_status;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public String getGroup_star() {
            return group_star;
        }

        public void setGroup_level(int group_level) {
            this.group_level = group_level;
        }

        public int getGroup_admin_id() {
            return group_admin_id;
        }

        public void setGroup_admin(String group_admin) {
            this.group_admin = group_admin;
        }

        public String getGroup_all_member() {
            return group_all_member;
        }

        public void setGroup_admin_id(int group_admin_id) {
            this.group_admin_id = group_admin_id;
        }

        public int getGroup_level() {
            return group_level;
        }

        public void setGroup_all_member(String group_all_member) {
            this.group_all_member = group_all_member;
        }

        public String getGroup_medal() {
            return group_medal;
        }

        public void setGroup_avatar(String group_avatar) {
            this.group_avatar = group_avatar;
        }

        public String getGroup_member() {
            return group_member;
        }

        public void setGroup_medal(String group_medal) {
            this.group_medal = group_medal;
        }

        public String getGroup_time() {
            return group_time;
        }

        public void setGroup_member(String group_member) {
            this.group_member = group_member;
        }

        public List<Medal> getMedals() {
            return medals;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public String getGroup_admin() {
            return group_admin;
        }

        public void setGroup_star(String group_star) {
            this.group_star = group_star;
        }

        public String getGroup_avatar() {
            return group_avatar;
        }

        public void setGroup_summary(String group_summary) {
            this.group_summary = group_summary;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_tag(String group_tag) {
            this.group_tag = group_tag;
        }

        public String getGroup_summary() {
            return group_summary;
        }

        public void setGroup_time(String group_time) {
            this.group_time = group_time;
        }

        public String getGroup_tag() {
            return group_tag;
        }

        public void setMedals(List<Medal> medals) {
            this.medals = medals;
        }

        public class Medal{
            private String medal_img;

            public String getMedal_img() {
                return medal_img;
            }

            public void setMedal_img(String medal_img) {
                this.medal_img = medal_img;
            }
        }

    }
}
