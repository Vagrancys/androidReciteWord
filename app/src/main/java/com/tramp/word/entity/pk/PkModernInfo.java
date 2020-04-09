package com.tramp.word.entity.pk;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/10
 * version:1.0
 */

public class PkModernInfo {
    private int code;
    private ArrayList<Modern> moderns;

    public ArrayList<Modern> getModerns() {
        return moderns;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setModerns(ArrayList<Modern> moderns) {
        this.moderns = moderns;
    }

    public class Modern{
        private int modern_id;
        private String modern_img;
        private String modern_name;
        private String modern_word;
        private int modern_time;

        public int getModern_id() {
            return modern_id;
        }

        public void setModern_id(int modern_id) {
            this.modern_id = modern_id;
        }

        public int getModern_time() {
            return modern_time;
        }

        public void setModern_img(String modern_img) {
            this.modern_img = modern_img;
        }

        public String getModern_img() {
            return modern_img;
        }

        public void setModern_name(String modern_name) {
            this.modern_name = modern_name;
        }

        public String getModern_name() {
            return modern_name;
        }

        public void setModern_time(int modern_time) {
            this.modern_time = modern_time;
        }

        public String getModern_word() {
            return modern_word;
        }

        public void setModern_word(String modern_word) {
            this.modern_word = modern_word;
        }
    }
}
