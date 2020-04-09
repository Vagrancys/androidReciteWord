package com.tramp.word.entity.revise;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/09
 * version:1.0
 */
public class DefaultPinyinInfo {
    private String word_title;
    private String word_select;
    private int word_id;
    private String content_letter;
    private String content_select;
    private int select_status;

    public int getWord_id() {
        return word_id;
    }

    public void setWord_id(int word_id) {
        this.word_id = word_id;
    }

    public int getSelect_status() {
        return select_status;
    }

    public void setSelect_status(int select_status) {
        this.select_status = select_status;
    }

    public String getContent_letter() {
        return content_letter;
    }

    public void setContent_letter(String content_letter) {
        this.content_letter = content_letter;
    }

    public void setContent_select(String content_select) {
        this.content_select = content_select;
    }

    public String getContent_select() {
        return content_select;
    }

    public void setWord_select(String word_select) {
        this.word_select = word_select;
    }

    public String getWord_select() {
        return word_select;
    }

    public String getWord_title() {
        return word_title;
    }

    public void setWord_title(String word_title) {
        this.word_title = word_title;
    }
}
