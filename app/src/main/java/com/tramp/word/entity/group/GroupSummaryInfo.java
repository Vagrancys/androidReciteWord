package com.tramp.word.entity.group;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/19
 * version:1.0
 */

public class GroupSummaryInfo {
    private int code;
    private Summary summary;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public class Summary{
        private int group_id;
        private String group_summary;
        private int group_link;
        private String group_links;
        private ArrayList<String> tags;
        private ArrayList<Integer> tags_id;

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_link(int group_link) {
            this.group_link = group_link;
        }

        public int getGroup_link() {
            return group_link;
        }

        public void setGroup_links(String group_links) {
            this.group_links = group_links;
        }

        public String getGroup_links() {
            return group_links;
        }

        public void setGroup_summary(String group_summary) {
            this.group_summary = group_summary;
        }

        public String getGroup_summary() {
            return group_summary;
        }


        public ArrayList<Integer> getTags_id() {
            return tags_id;
        }

        public ArrayList<String> getTags() {
            return tags;
        }

        public void setTags(ArrayList<String> tags) {
            this.tags = tags;
        }

        public void setTags_id(ArrayList<Integer> tags_id) {
            this.tags_id = tags_id;
        }

    }
}
