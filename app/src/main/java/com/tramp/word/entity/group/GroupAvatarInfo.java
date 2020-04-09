package com.tramp.word.entity.group;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/16
 * version:1.0
 */

public class GroupAvatarInfo {
    private int code;
    private ArrayList<Item> items;

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public class Item{
        private int avatar_id;
        private String avatar_url;

        public int getAvatar_id() {
            return avatar_id;
        }

        public void setAvatar_id(int avatar_id) {
            this.avatar_id = avatar_id;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }
    }
}
