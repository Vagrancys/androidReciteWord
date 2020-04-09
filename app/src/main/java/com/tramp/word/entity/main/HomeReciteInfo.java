package com.tramp.word.entity.main;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/15
 * version:1.0
 */

public class HomeReciteInfo {
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
        private int group_status;
        private int task_number;
        private int group_news;
        private int pk_number;
        private int group_id;

        public int getGroup_news() {
            return group_news;
        }

        public void setGroup_news(int group_news) {
            this.group_news = group_news;
        }

        public int getPk_number() {
            return pk_number;
        }

        public void setPk_number(int pk_number) {
            this.pk_number = pk_number;
        }

        public int getGroup_status() {
            return group_status;
        }

        public void setGroup_status(int group_status) {
            this.group_status = group_status;
        }

        public int getTask_number() {
            return task_number;
        }

        public void setTask_number(int task_number) {
            this.task_number = task_number;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }
    }
}
