package com.tramp.word.entity.task;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/02
 * version:1.0
 */

public class TaskInfo {
    private int code;
    private ArrayList<Task> accept;
    private ArrayList<NoTask> noaccept;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<NoTask> getNoaccept() {
        return noaccept;
    }

    public void setAccept(ArrayList<Task> accept) {
        this.accept = accept;
    }

    public ArrayList<Task> getAccept() {
        return accept;
    }

    public void setNoaccept(ArrayList<NoTask> noaccept) {
        this.noaccept = noaccept;
    }

    public static class Task{
        private int task_id;
        private int task_class;
        private int task_status;
        private int task_star;
        private int task_money;
        private int task_day;
        private int task_number;
        private int task_no_number;
        private int task_no_star;
        private int task_no_money;
        private String task_summary;

        public int getTask_money() {
            return task_money;
        }

        public void setTask_money(int task_money) {
            this.task_money = task_money;
        }

        public int getTask_no_money() {
            return task_no_money;
        }

        public int getTask_no_star() {
            return task_no_star;
        }

        public void setTask_no_money(int task_no_money) {
            this.task_no_money = task_no_money;
        }

        public void setTask_no_star(int task_no_star) {
            this.task_no_star = task_no_star;
        }

        public int getTask_class() {
            return task_class;
        }

        public void setTask_class(int task_class) {
            this.task_class = task_class;
        }

        public int getTask_day() {
            return task_day;
        }

        public void setTask_day(int task_day) {
            this.task_day = task_day;
        }

        public int getTask_id() {
            return task_id;
        }

        public void setTask_id(int task_id) {
            this.task_id = task_id;
        }

        public int getTask_no_number() {
            return task_no_number;
        }

        public int getTask_number() {
            return task_number;
        }

        public void setTask_no_number(int task_no_number) {
            this.task_no_number = task_no_number;
        }

        public int getTask_star() {
            return task_star;
        }

        public int getTask_status() {
            return task_status;
        }

        public void setTask_number(int task_number) {
            this.task_number = task_number;
        }

        public void setTask_star(int task_star) {
            this.task_star = task_star;
        }

        public void setTask_status(int task_status) {
            this.task_status = task_status;
        }
    }

    public static class NoTask{
        private int task_id;
        private String task_day;
        private int task_class;
        private int task_star;
        private int task_money;
        private String task_summary;

        public String getTask_day() {
            return task_day;
        }

        public void setTask_day(String task_day) {
            this.task_day = task_day;
        }

        public int getTask_id() {
            return task_id;
        }

        public void setTask_id(int task_id) {
            this.task_id = task_id;
        }

        public void setTask_summary(String task_summary) {
            this.task_summary = task_summary;
        }

        public String getTask_summary() {
            return task_summary;
        }

        public int getTask_money() {
            return task_money;
        }

        public void setTask_money(int task_money) {
            this.task_money = task_money;
        }

        public int getTask_star() {
            return task_star;
        }

        public void setTask_star(int task_star) {
            this.task_star = task_star;
        }

        public int getTask_class() {
            return task_class;
        }

        public void setTask_class(int task_class) {
            this.task_class = task_class;
        }
    }
}
