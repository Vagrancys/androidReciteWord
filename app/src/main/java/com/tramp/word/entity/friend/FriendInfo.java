package com.tramp.word.entity.friend;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/31
 * version:1.0
 */

public class FriendInfo {
    private int code;
    private ArrayList<Friend> friends;

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    public class Friend{
        private int friend_id;
        private String friend_img;
        private String friend_name;
        private String friend_summary;

        public int getFriend_id() {
            return friend_id;
        }

        public void setFriend_id(int friend_id) {
            this.friend_id = friend_id;
        }

        public String getFriend_img() {
            return friend_img;
        }

        public void setFriend_img(String friend_img) {
            this.friend_img = friend_img;
        }

        public String getFriend_name() {
            return friend_name;
        }

        public void setFriend_name(String friend_name) {
            this.friend_name = friend_name;
        }

        public void setFriend_summary(String friend_summary) {
            this.friend_summary = friend_summary;
        }

        public String getFriend_summary() {
            return friend_summary;
        }

    }
}
