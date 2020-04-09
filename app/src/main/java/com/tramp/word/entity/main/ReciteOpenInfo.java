package com.tramp.word.entity.main;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/16
 * version:1.0
 */
public class ReciteOpenInfo {
    private int code;
    private Open open;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Open getOpen() {
        return open;
    }

    public void setOpen(Open open) {
        this.open = open;
    }

    public class Open{
        private String open_title;
        private String open_day;
        private String open_word;
        private String open_avatar;
        private String open_time;

        public String getOpen_avatar() {
            return open_avatar;
        }

        public void setOpen_avatar(String open_avatar) {
            this.open_avatar = open_avatar;
        }

        public void setOpen_day(String open_day) {
            this.open_day = open_day;
        }

        public String getOpen_day() {
            return open_day;
        }

        public void setOpen_time(String open_time) {
            this.open_time = open_time;
        }

        public String getOpen_time() {
            return open_time;
        }

        public void setOpen_title(String open_title) {
            this.open_title = open_title;
        }

        public String getOpen_title() {
            return open_title;
        }

        public void setOpen_word(String open_word) {
            this.open_word = open_word;
        }

        public String getOpen_word() {
            return open_word;
        }
    }
}
