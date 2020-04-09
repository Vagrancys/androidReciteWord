package com.tramp.word.entity.user;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/23
 * version:1.0
 */
public class SettingAccountInfo {
    private int code;
    private Account account;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public class Account{
        private String user_avatar;
        private String user_name;
        private String user_title;
        private String user_phone;
        private String user_qq;
        private String user_weibo;

        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_qq() {
            return user_qq;
        }

        public void setUser_qq(String user_qq) {
            this.user_qq = user_qq;
        }

        public String getUser_title() {
            return user_title;
        }

        public void setUser_title(String user_title) {
            this.user_title = user_title;
        }

        public String getUser_weibo() {
            return user_weibo;
        }

        public void setUser_weibo(String user_weibo) {
            this.user_weibo = user_weibo;
        }
    }
}
