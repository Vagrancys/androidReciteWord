package com.tramp.word.entity.user;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/20
 * version:1.0
 */

public class UserShareInfo {
    private int code;
    private Share share;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public class Share{
        private String share_date;
        private String share_year;
        private String share_month;
        private ArrayList<Day> days;

        public ArrayList<Day> getDays() {
            return days;
        }

        public void setDays(ArrayList<Day> days) {
            this.days = days;
        }

        public String getShare_date() {
            return share_date;
        }

        public void setShare_date(String share_date) {
            this.share_date = share_date;
        }

        public String getShare_month() {
            return share_month;
        }

        public void setShare_month(String share_month) {
            this.share_month = share_month;
        }

        public String getShare_year() {
            return share_year;
        }

        public void setShare_year(String share_year) {
            this.share_year = share_year;
        }

        public class Day{
            private int day_status;
            private String day_number;

            public String getDay_number() {
                return day_number;
            }

            public void setDay_number(String day_number) {
                this.day_number = day_number;
            }

            public int getDay_status() {
                return day_status;
            }

            public void setDay_status(int day_status) {
                this.day_status = day_status;
            }
        }
    }
}
