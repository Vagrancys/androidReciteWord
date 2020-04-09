package com.tramp.word.entity;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/19
 * version:1.0
 */

public class DefaultSummaryInfo {
    private String group_summary;
    private int group_link;
    private String group_links;
    private String group_tag;
    private String group_tag_id;
    private int group_id;

    public String getGroup_tag_id() {
        return group_tag_id;
    }

    public void setGroup_tag_id(String group_tag_id) {
        this.group_tag_id = group_tag_id;
    }

    public String getGroup_summary() {
        return group_summary;
    }

    public void setGroup_tag(String group_tag) {
        this.group_tag = group_tag;
    }

    public String getGroup_links() {
        return group_links;
    }

    public void setGroup_summary(String group_summary) {
        this.group_summary = group_summary;
    }

    public int getGroup_link() {
        return group_link;
    }

    public void setGroup_links(String group_links) {
        this.group_links = group_links;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_tag() {
        return group_tag;
    }

    public void setGroup_link(int group_link) {
        this.group_link = group_link;
    }
}
