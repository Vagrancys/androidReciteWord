package com.tramp.word.entity.book;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/06/09
 * version:1.0
 */
public class BookHintInfo {
    private int code;
    private ArrayList<Hint> hints;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Hint> getHints() {
        return hints;
    }

    public void setHints(ArrayList<Hint> hints) {
        this.hints = hints;
    }

    public class Hint{
        private String hint_name;

        public String getHint_name() {
            return hint_name;
        }

        public void setHint_name(String hint_name) {
            this.hint_name = hint_name;
        }
    }
}






