package com.tramp.word.entity;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/11
 * version:1.0
 */
public class DefaultBookInfo {
    private int book_id;
    private int book_user;
    private String book_name;
    private String book_img;
    public String book_url;
    private int total_num;
    private int new_num;
    private int level_star;
    private int star;
    private int all_star;
    private int started_at;
    private int finished_at;
    private int last_finished_at;
    private int finished;
    private int now_gate;
    private int finish_gate;
    private int good;
    private int number;
    private String summary;

    public int getBook_user() {
        return book_user;
    }

    public void setBook_user(int book_user) {
        this.book_user = book_user;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public String getBook_url() {
        return book_url;
    }

    public void setBook_url(String book_url) {
        this.book_url = book_url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setAll_star(int all_star) {
        this.all_star = all_star;
    }

    public int getAll_star() {
        return all_star;
    }

    public void setBook_img(String book_img) {
        this.book_img = book_img;
    }

    public int getFinish_gate() {
        return finish_gate;
    }

    public void setFinish_gate(int finish_gate) {
        this.finish_gate = finish_gate;
    }

    public int getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(int finished_at) {
        this.finished_at = finished_at;
    }


    public int getLast_finished_at() {
        return last_finished_at;
    }

    public void setLast_finished_at(int last_finished_at) {
        this.last_finished_at = last_finished_at;
    }

    public int getLevel_star() {
        return level_star;
    }

    public void setLevel_star(int level_star) {
        this.level_star = level_star;
    }

    public void setNew_num(int new_num) {
        this.new_num = new_num;
    }

    public int getNew_num() {
        return new_num;
    }

    public void setNow_gate(int now_gate) {
        this.now_gate = now_gate;
    }

    public int getNow_gate() {
        return now_gate;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getStar() {
        return star;
    }

    public void setStarted_at(int started_at) {
        this.started_at = started_at;
    }

    public int getStarted_at() {
        return started_at;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public int getTotal_num() {
        return total_num;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getBook_img() {
        return book_img;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }
}
