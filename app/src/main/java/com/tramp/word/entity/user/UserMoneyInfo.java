package com.tramp.word.entity.user;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/23
 * version:1.0
 */
public class UserMoneyInfo {
    private int code;
    private Money money;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public class Money{
        private int money_number;

        public int getMoney_number() {
            return money_number;
        }

        public void setMoney_number(int money_number) {
            this.money_number = money_number;
        }

    }
}
