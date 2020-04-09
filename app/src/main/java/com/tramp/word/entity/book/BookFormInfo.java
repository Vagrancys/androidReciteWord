package com.tramp.word.entity.book;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/06/11
 * version:1.0
 */
public class BookFormInfo {
    private int form_language;
    private String form_name;
    private String form_group;
    private String form_note;
    private String form_link;

    public int getForm_language() {
        return form_language;
    }

    public void setForm_group(String form_group) {
        this.form_group = form_group;
    }

    public String getForm_group() {
        return form_group;
    }

    public void setForm_language(int form_language) {
        this.form_language = form_language;
    }

    public String getForm_link() {
        return form_link;
    }

    public void setForm_link(String form_link) {
        this.form_link = form_link;
    }

    public String getForm_name() {
        return form_name;
    }

    public void setForm_name(String form_name) {
        this.form_name = form_name;
    }

    public String getForm_note() {
        return form_note;
    }

    public void setForm_note(String form_note) {
        this.form_note = form_note;
    }
}
