package com.tramp.word.entity.group;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/15
 * version:1.0
 */

public class MemberStatusInfo {
    private int code;
    private ArrayList<member> members;

    public ArrayList<member> getMembers() {
        return members;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMembers(ArrayList<member> members) {
        this.members = members;
    }

    public class member{
        private int member_id;
        private String member_avatar;
        private String member_name;
        private String member_time;

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }

        public String getMember_avatar() {
            return member_avatar;
        }

        public void setMember_avatar(String member_avatar) {
            this.member_avatar = member_avatar;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_time() {
            return member_time;
        }

        public void setMember_time(String member_time) {
            this.member_time = member_time;
        }
    }
}
