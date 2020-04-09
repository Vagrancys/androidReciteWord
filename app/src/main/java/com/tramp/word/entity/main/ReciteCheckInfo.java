package com.tramp.word.entity.main;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/16
 * version:1.0
 */
public class ReciteCheckInfo {
    private int code;
    private Check check;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public Check getCheck() {
        return check;
    }

    public class Check{
        private int check_time;
        private String check_gate;
        private String check_book;
        private String check_avatar;
        private String check_name;
        private String check_star;
        private String check_rank;

        public String getCheck_book() {
            return check_book;
        }

        public void setCheck_rank(String check_rank) {
            this.check_rank = check_rank;
        }

        public void setCheck_star(String check_star) {
            this.check_star = check_star;
        }

        public void setCheck_gate(String check_gate) {
            this.check_gate = check_gate;
        }

        public void setCheck_book(String check_book) {
            this.check_book = check_book;
        }

        public String getCheck_gate() {
            return check_gate;
        }

        public String getCheck_rank() {
            return check_rank;
        }

        public String getCheck_star() {
            return check_star;
        }

        public void setCheck_avatar(String check_avatar) {
            this.check_avatar = check_avatar;
        }

        public void setCheck_name(String check_name) {
            this.check_name = check_name;
        }

        public String getCheck_avatar() {
            return check_avatar;
        }

        public String getCheck_name() {
            return check_name;
        }

        public int getCheck_time() {
            return check_time;
        }

        public void setCheck_time(int check_time) {
            this.check_time = check_time;
        }
    }
}
