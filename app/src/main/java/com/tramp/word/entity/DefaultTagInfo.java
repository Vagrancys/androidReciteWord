package com.tramp.word.entity;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/17
 * version:1.0
 */

public class DefaultTagInfo {
    private int tag_id;
    private int tag_status;
    private String tag_title;

    public String getTag_title() {
        return tag_title;
    }

    public void setTag_title(String tag_title) {
        this.tag_title = tag_title;
    }

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public int getTag_status() {
        return tag_status;
    }

    public void setTag_status(int tag_status) {
        this.tag_status = tag_status;
    }
}
