package com.tramp.word.entity.group;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/08
 * version:1.0
 */

public class GroupMedalInfo {
    private int code;
    private Medal medal;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Medal getMedal() {
        return medal;
    }

    public void setMedal(Medal medal) {
        this.medal = medal;
    }

    public class Medal{
        private int medal_number;
        private String medal_rank;
        private ArrayList<Item> ones;
        private ArrayList<Item> twos;
        private ArrayList<Item> threes;

        public ArrayList<Item> getOnes() {
            return ones;
        }

        public void setMedal_number(int medal_number) {
            this.medal_number = medal_number;
        }

        public void setOnes(ArrayList<Item> ones) {
            this.ones = ones;
        }

        public ArrayList<Item> getThrees() {
            return threes;
        }

        public void setThrees(ArrayList<Item> threes) {
            this.threes = threes;
        }

        public ArrayList<Item> getTwos() {
            return twos;
        }

        public void setTwos(ArrayList<Item> twos) {
            this.twos = twos;
        }

        public int getMedal_number() {
            return medal_number;
        }

        public String getMedal_rank() {
            return medal_rank;
        }

        public void setMedal_rank(String medal_rank) {
            this.medal_rank = medal_rank;
        }

        public class Item{
            private int medal_id;
            private String medal_name;
            private String medal_img;

            public int getMedal_id() {
                return medal_id;
            }

            public void setMedal_id(int medal_id) {
                this.medal_id = medal_id;
            }

            public String getMedal_img() {
                return medal_img;
            }

            public void setMedal_img(String medal_img) {
                this.medal_img = medal_img;
            }

            public String getMedal_name() {
                return medal_name;
            }

            public void setMedal_name(String medal_name) {
                this.medal_name = medal_name;
            }
        }
    }
}
