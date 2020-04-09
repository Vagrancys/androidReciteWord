package com.tramp.word.entity.task;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/04
 * version:1.0
 */

public class TaskListInfo {
    private int code;
    private ArrayList<List> lists;

    public ArrayList<List> getLists() {
        return lists;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setLists(ArrayList<List> lists) {
        this.lists = lists;
    }

    public class List{
        private int task_class;
        private int task_day;
        private int task_status;
        private String task_time;
        private String task_star;
        private String task_money;

        public String getTask_star() {
            return task_star;
        }

        public void setTask_star(String task_star) {
            this.task_star = task_star;
        }

        public String getTask_money() {
            return task_money;
        }

        public void setTask_money(String task_money) {
            this.task_money = task_money;
        }

        public int getTask_day() {
            return task_day;
        }

        public void setTask_day(int task_day) {
            this.task_day = task_day;
        }

        public int getTask_status() {
            return task_status;
        }

        public void setTask_status(int task_status) {
            this.task_status = task_status;
        }

        public String getTask_time() {
            return task_time;
        }

        public void setTask_time(String task_time) {
            this.task_time = task_time;
        }

        public int getTask_class() {
            return task_class;
        }

        public void setTask_class(int task_class) {
            this.task_class = task_class;
        }
    }
}
