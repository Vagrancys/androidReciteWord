package com.tramp.word.entity.friend;

import java.util.List;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/22
 * version:1.0
 */
public class FriendMainInfo {
    private int code;
    private Friend friends;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Friend getFriends() {
        return friends;
    }

    public void setFriends(Friend friends) {
        this.friends = friends;
    }

    public class Friend{
        private int friend_id;
        private String friend_avatar;
        private String friend_name;
        private String friend_sign;
        private int friend_status;
        private int word_status;
        private Word word;
        private int group_status;
        private Group group;

        public String getFriend_sign() {
            return friend_sign;
        }

        public void setFriend_sign(String friend_sign) {
            this.friend_sign = friend_sign;
        }

        public void setFriend_name(String friend_name) {
            this.friend_name = friend_name;
        }

        public String getFriend_name() {
            return friend_name;
        }

        public void setFriend_id(int friend_id) {
            this.friend_id = friend_id;
        }

        public String getFriend_avatar() {
            return friend_avatar;
        }

        public void setFriend_avatar(String friend_avatar) {
            this.friend_avatar = friend_avatar;
        }

        public int getFriend_id() {
            return friend_id;
        }

        public void setFriend_status(int friend_status) {
            this.friend_status = friend_status;
        }

        public Group getGroup() {
            return group;
        }

        public void setGroup(Group group) {
            this.group = group;
        }

        public int getFriend_status() {
            return friend_status;
        }

        public void setGroup_status(int group_status) {
            this.group_status = group_status;
        }

        public int getGroup_status() {
            return group_status;
        }

        public void setWord(Word word) {
            this.word = word;
        }

        public int getWord_status() {
            return word_status;
        }

        public void setWord_status(int word_status) {
            this.word_status = word_status;
        }

        public Word getWord() {
            return word;
        }

        public class Word{
            private int word_id;
            private String word_avatar;
            private String word_name;
            private int word_pk;
            private int pk_status;

            public int getWord_id() {
                return word_id;
            }

            public void setWord_avatar(String word_avatar) {
                this.word_avatar = word_avatar;
            }

            public void setWord_id(int word_id) {
                this.word_id = word_id;
            }

            public int getWord_pk() {
                return word_pk;
            }

            public void setWord_name(String word_name) {
                this.word_name = word_name;
            }

            public String getWord_avatar() {
                return word_avatar;
            }

            public void setWord_pk(int word_pk) {
                this.word_pk = word_pk;
            }

            public String getWord_name() {
                return word_name;
            }

            public int getPk_status() {
                return pk_status;
            }

            public void setPk_status(int pk_status) {
                this.pk_status = pk_status;
            }
        }

        public class Group{
            private int group_id;
            private String group_avatar;
            private List<Medal> medals;
            private String group_name;
            private int group_level;
            private int group_medal;

            public int getGroup_id() {
                return group_id;
            }

            public void setGroup_avatar(String group_avatar) {
                this.group_avatar = group_avatar;
            }

            public int getGroup_level() {
                return group_level;
            }

            public void setGroup_id(int group_id) {
                this.group_id = group_id;
            }

            public int getGroup_medal() {
                return group_medal;
            }

            public void setGroup_level(int group_level) {
                this.group_level = group_level;
            }

            public List<Medal> getMedals() {
                return medals;
            }

            public void setGroup_medal(int group_medal) {
                this.group_medal = group_medal;
            }

            public String getGroup_avatar() {
                return group_avatar;
            }

            public void setGroup_name(String group_name) {
                this.group_name = group_name;
            }

            public String getGroup_name() {
                return group_name;
            }

            public void setMedals(List<Medal> medals) {
                this.medals = medals;
            }

            public class Medal{
                private String medal_avatar;

                public String getMedal_avatar() {
                    return medal_avatar;
                }

                public void setMedal_avatar(String medal_avatar) {
                    this.medal_avatar = medal_avatar;
                }
            }
        }
    }
}
