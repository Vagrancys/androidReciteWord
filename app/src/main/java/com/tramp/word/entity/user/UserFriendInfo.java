package com.tramp.word.entity.user;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/22
 * version:1.0
 */
public class UserFriendInfo {
    private int code;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private ArrayList<Add> adds;
        private ArrayList<Friend> friends;

        public ArrayList<Add> getAdds() {
            return adds;
        }

        public void setAdds(ArrayList<Add> adds) {
            this.adds = adds;
        }

        public ArrayList<Friend> getFriends() {
            return friends;
        }

        public void setFriends(ArrayList<Friend> friends) {
            this.friends = friends;
        }
        public class Add {
            private int item_id;
            private String add_avatar;
            private String add_name;
            private int add_id;

            public int getItem_id() {
                return item_id;
            }

            public void setItem_id(int item_id) {
                this.item_id = item_id;
            }

            public int getAdd_id() {
                return add_id;
            }

            public void setAdd_avatar(String add_avatar) {
                this.add_avatar = add_avatar;
            }

            public void setAdd_id(int add_id) {
                this.add_id = add_id;
            }

            public String getAdd_avatar() {
                return add_avatar;
            }

            public void setAdd_name(String add_name) {
                this.add_name = add_name;
            }

            public String getAdd_name() {
                return add_name;
            }
        }

        public class Friend{
            private int friend_id;
            private String friend_avatar;
            private String friend_name;
            private String friend_sign;
            private String friend_word;

            public int getFriend_id() {
                return friend_id;
            }

            public void setFriend_avatar(String friend_avatar) {
                this.friend_avatar = friend_avatar;
            }

            public String getFriend_avatar() {
                return friend_avatar;
            }

            public void setFriend_id(int friend_id) {
                this.friend_id = friend_id;
            }

            public String getFriend_name() {
                return friend_name;
            }

            public void setFriend_name(String friend_name) {
                this.friend_name = friend_name;
            }

            public String getFriend_sign() {
                return friend_sign;
            }

            public void setFriend_sign(String friend_sign) {
                this.friend_sign = friend_sign;
            }

            public String getFriend_word() {
                return friend_word;
            }

            public void setFriend_word(String friend_word) {
                this.friend_word = friend_word;
            }
        }
    }



}
