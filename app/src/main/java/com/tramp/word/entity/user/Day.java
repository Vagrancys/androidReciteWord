package com.tramp.word.entity.user;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/21
 * version:1.0
 */
public class Day {
    private int day_status;
    private int day_number;
    private String day_time;

    public int getDay_number() {
        return day_number;
    }

    public int getDay_status() {
        return day_status;
    }

    public void setDay_number(int day_number) {
        this.day_number = day_number;
    }

    public String getDay_time() {
        return day_time;
    }

    public void setDay_status(int day_status) {
        this.day_status = day_status;
    }

    public void setDay_time(String day_time) {
        this.day_time = day_time;
    }
}
