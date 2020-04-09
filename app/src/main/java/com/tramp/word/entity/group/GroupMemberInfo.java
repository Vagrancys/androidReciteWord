package com.tramp.word.entity.group;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/15
 * version:1.0
 */

public class GroupMemberInfo {
    private int code;
    private Member member;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public class Member{
        private String out_number;
        private String in_number;
        private String member_number;
        private ArrayList<item> outs;
        private ArrayList<item> ins;
        private ArrayList<list> lists;
        private String member_name;
        private String member_day;
        private String member_star;
        private String member_img;

        public String getMember_img() {
            return member_img;
        }

        public void setMember_img(String member_img) {
            this.member_img = member_img;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_day() {
            return member_day;
        }

        public void setMember_day(String member_day) {
            this.member_day = member_day;
        }

        public String getMember_star() {
            return member_star;
        }

        public void setMember_star(String member_star) {
            this.member_star = member_star;
        }

        public ArrayList<item> getIns() {
            return ins;
        }

        public void setIn_number(String in_number) {
            this.in_number = in_number;
        }

        public void setIns(ArrayList<item> ins) {
            this.ins = ins;
        }

        public void setOut_number(String out_number) {
            this.out_number = out_number;
        }

        public void setOuts(ArrayList<item> outs) {
            this.outs = outs;
        }

        public ArrayList<item> getOuts() {
            return outs;
        }

        public String getIn_number() {
            return in_number;
        }

        public String getOut_number() {
            return out_number;
        }

        public ArrayList<list> getLists() {
            return lists;
        }

        public void setLists(ArrayList<list> lists) {
            this.lists = lists;
        }


        public void setMember_number(String member_number) {
            this.member_number = member_number;
        }

        public String getMember_number() {
            return member_number;
        }

        public class item{
            private String user_avatar;

            public String getUser_avatar() {
                return user_avatar;
            }

            public void setUser_avatar(String user_avatar) {
                this.user_avatar = user_avatar;
            }
        }
        public class list{
            private int user_id;
            private String user_avatar;
            private String user_name;
            private int user_praise;
            private int user_done;
            private int user_star;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_avatar(String user_avatar) {
                this.user_avatar = user_avatar;
            }

            public String getUser_avatar() {
                return user_avatar;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public int getUser_praise() {
                return user_praise;
            }

            public void setUser_praise(int user_praise) {
                this.user_praise = user_praise;
            }

            public int getUser_done() {
                return user_done;
            }

            public void setUser_done(int user_done) {
                this.user_done = user_done;
            }

            public int getUser_star() {
                return user_star;
            }

            public void setUser_star(int user_star) {
                this.user_star = user_star;
            }
        }
    }
}
