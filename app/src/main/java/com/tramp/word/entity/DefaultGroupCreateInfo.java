package com.tramp.word.entity;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/17
 * version:1.0
 */

public class DefaultGroupCreateInfo {
    private String group_name;
    private int group_avatar;
    private String group_tag;
    private String group_summary;
    private int group_link;
    private String group_links;
    private int group_star;
    private int group_re;
    private int group_as;
    private int group_admin;

    public int getGroup_admin() {
        return group_admin;
    }

    public void setGroup_admin(int group_admin) {
        this.group_admin = group_admin;
    }

    public void setGroup_tag(String group_tag) {
        this.group_tag = group_tag;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_as(int group_as) {
        this.group_as = group_as;
    }

    public int getGroup_as() {
        return group_as;
    }

    public void setGroup_avatar(int group_avatar) {
        this.group_avatar = group_avatar;
    }

    public String getGroup_tag() {
        return group_tag;
    }

    public void setGroup_link(int group_link) {
        this.group_link = group_link;
    }

    public int getGroup_avatar() {
        return group_avatar;
    }

    public void setGroup_links(String group_links) {
        this.group_links = group_links;
    }

    public int getGroup_link() {
        return group_link;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getGroup_re() {
        return group_re;
    }

    public void setGroup_re(int group_re) {
        this.group_re = group_re;
    }

    public int getGroup_star() {
        return group_star;
    }

    public void setGroup_star(int group_star) {
        this.group_star = group_star;
    }

    public String getGroup_links() {
        return group_links;
    }

    public String getGroup_summary() {
        return group_summary;
    }

    public void setGroup_summary(String group_summary) {
        this.group_summary = group_summary;
    }
}
