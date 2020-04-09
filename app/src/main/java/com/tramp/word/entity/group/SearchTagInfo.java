package com.tramp.word.entity.group;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/09
 * version:1.0
 */

public class SearchTagInfo {
    private int code;
    private ArrayList<Tag> tags;

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
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
