package com.tramp.word.entity;

/**
 * Created by Administrator on 2019/2/15.
 */

public class DefaultLetterEntity {
    private int id;
    private String title;
    private String select;
    private boolean status;

    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getSelect() {
        return select;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSelect(String select) {
        this.select = select;
    }
}
