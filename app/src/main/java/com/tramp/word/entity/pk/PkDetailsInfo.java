package com.tramp.word.entity.pk;

import java.util.List;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/17
 * version:1.0
 */
public class PkDetailsInfo {
    private int code;
    private Details details;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public Details getDetails() {
        return details;
    }

    public class Details{
        private String details_one_avatar;
        private String details_two_avatar;
        private String details_one_name;
        private String details_two_name;
        private int details_two_id;
        private String details_two_number;
        private String details_one_number;
        private int details_win;
        private List<Item> items;

        public String getDetails_one_name() {
            return details_one_name;
        }

        public void setDetails_one_name(String details_one_name) {
            this.details_one_name = details_one_name;
        }

        public void setDetails_two_name(String details_two_name) {
            this.details_two_name = details_two_name;
        }

        public String getDetails_two_name() {
            return details_two_name;
        }

        public int getDetails_win() {
            return details_win;
        }

        public void setDetails_one_avatar(String details_one_avatar) {
            this.details_one_avatar = details_one_avatar;
        }

        public void setDetails_one_number(String details_one_number) {
            this.details_one_number = details_one_number;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setDetails_two_avatar(String details_two_avatar) {
            this.details_two_avatar = details_two_avatar;
        }

        public String getDetails_one_avatar() {
            return details_one_avatar;
        }

        public String getDetails_one_number() {
            return details_one_number;
        }

        public void setDetails_two_number(String details_two_number) {
            this.details_two_number = details_two_number;
        }

        public String getDetails_two_avatar() {
            return details_two_avatar;
        }

        public void setDetails_win(int details_win) {
            this.details_win = details_win;
        }

        public int getDetails_two_id() {
            return details_two_id;
        }

        public void setDetails_two_id(int details_two_id) {
            this.details_two_id = details_two_id;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public String getDetails_two_number() {
            return details_two_number;
        }

        public class Item{
            private String item_word;
            private int item_one_number;
            private int item_two_number;
            private String one_now_number;
            private String one_total_number;
            private String two_now_number;
            private String two_total_number;

            public int getItem_one_number() {
                return item_one_number;
            }

            public void setItem_one_number(int item_one_number) {
                this.item_one_number = item_one_number;
            }

            public int getItem_two_number() {
                return item_two_number;
            }

            public void setItem_two_number(int item_two_number) {
                this.item_two_number = item_two_number;
            }

            public String getItem_word() {
                return item_word;
            }

            public void setItem_word(String item_word) {
                this.item_word = item_word;
            }

            public void setOne_now_number(String one_now_number) {
                this.one_now_number = one_now_number;
            }

            public String getOne_now_number() {
                return one_now_number;
            }

            public void setOne_total_number(String one_total_number) {
                this.one_total_number = one_total_number;
            }

            public String getOne_total_number() {
                return one_total_number;
            }

            public void setTwo_now_number(String two_now_number) {
                this.two_now_number = two_now_number;
            }

            public String getTwo_now_number() {
                return two_now_number;
            }

            public void setTwo_total_number(String two_total_number) {
                this.two_total_number = two_total_number;
            }

            public String getTwo_total_number() {
                return two_total_number;
            }
        }
    }
}




