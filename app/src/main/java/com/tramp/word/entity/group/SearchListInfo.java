package com.tramp.word.entity.group;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/09
 * version:1.0
 */

public class SearchListInfo {
    private int code;
    private ArrayList<List> lists;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<List> getLists() {
        return lists;
    }

    public void setLists(ArrayList<List> lists) {
        this.lists = lists;
    }

    public class List{
        private int group_id;
        private String group_img;
        private String group_name;
        private int group_level;
        private ArrayList<Tag> tags;
        private int group_star;

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setTags(ArrayList<Tag> tags) {
            this.tags = tags;
        }

        public void setGroup_img(String group_img) {
            this.group_img = group_img;
        }

        public ArrayList<Tag> getTags() {
            return tags;
        }

        public void setGroup_level(int group_level) {
            this.group_level = group_level;
        }

        public int getGroup_level() {
            return group_level;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public int getGroup_star() {
            return group_star;
        }

        public void setGroup_star(int group_star) {
            this.group_star = group_star;
        }

        public String getGroup_img() {
            return group_img;
        }

        public class Tag{
            private String tag_name;

            public String getTag_name() {
                return tag_name;
            }

            public void setTag_name(String tag_name) {
                this.tag_name = tag_name;
            }
        }
    }
}
