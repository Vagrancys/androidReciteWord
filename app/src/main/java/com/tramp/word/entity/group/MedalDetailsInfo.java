package com.tramp.word.entity.group;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/08
 * version:1.0
 */

public class MedalDetailsInfo {
    private int code;
    private Details details;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public class Details{
        private String medal_id;
        private String medal_img;
        private String medal_name;
        private String medal_task;
        private int medal_time;
        private int medal_number;

        public String getMedal_img() {
            return medal_img;
        }

        public void setMedal_img(String medal_img) {
            this.medal_img = medal_img;
        }

        public int getMedal_number() {
            return medal_number;
        }

        public void setMedal_id(String medal_id) {
            this.medal_id = medal_id;
        }

        public int getMedal_time() {
            return medal_time;
        }

        public void setMedal_name(String medal_name) {
            this.medal_name = medal_name;
        }

        public String getMedal_id() {
            return medal_id;
        }

        public void setMedal_number(int medal_number) {
            this.medal_number = medal_number;
        }

        public String getMedal_name() {
            return medal_name;
        }

        public String getMedal_task() {
            return medal_task;
        }

        public void setMedal_task(String medal_task) {
            this.medal_task = medal_task;
        }

        public void setMedal_time(int medal_time) {
            this.medal_time = medal_time;
        }
    }
}
