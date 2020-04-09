package com.tramp.word.entity.user;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/17
 * version:1.0
 */

public class UserMainInfo {
    private int code;
    private main mains;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public main getMains() {
        return mains;
    }

    public void setMains(main mains) {
        this.mains = mains;
    }

    public class main{
        private String user_money;
        private String user_avatar;
        private String user_name;
        private String user_sign;
        private int user_group;
        private int sign;
        private Group group;

        public int getSign() {
            return sign;
        }

        public void setSign(int sign) {
            this.sign = sign;
        }

        public Group getGroup() {
            return group;
        }

        public void setGroup(Group group) {
            this.group = group;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public void setUser_group(int user_group) {
            this.user_group = user_group;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_sign() {
            return user_sign;
        }

        public void setUser_sign(String user_sign) {
            this.user_sign = user_sign;
        }

        public int getUser_group() {
            return user_group;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public String getUser_name() {
            return user_name;
        }

        public class Group{
            private String group_img;
            private int group_level;
            private int group_number;
            private String group_name;
            private ArrayList<Medal> medals;

            public ArrayList<Medal> getMedals() {
                return medals;
            }

            public void setGroup_img(String group_img) {
                this.group_img = group_img;
            }

            public int getGroup_level() {
                return group_level;
            }

            public void setGroup_level(int group_level) {
                this.group_level = group_level;
            }

            public int getGroup_number() {
                return group_number;
            }

            public void setGroup_name(String group_name) {
                this.group_name = group_name;
            }

            public String getGroup_img() {
                return group_img;
            }

            public void setGroup_number(int group_number) {
                this.group_number = group_number;
            }

            public void setMedals(ArrayList<Medal> medals) {
                this.medals = medals;
            }

            public String getGroup_name() {
                return group_name;
            }

            public class Medal{
                private String medal_img;

                public String getMedal_img() {
                    return medal_img;
                }

                public void setMedal_img(String medal_img) {
                    this.medal_img = medal_img;
                }
            }
        }

        public String getUser_money() {
            return user_money;
        }

        public void setUser_money(String user_money) {
            this.user_money = user_money;
        }
    }
}
