package com.tramp.word.entity.book;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/04
 * version:1.0
 */
public class DefaultWordInfo {
    private int word_id;
    private String word_name;
    private String word_music;
    private String word_music_url;
    private String word_meaning;
    private String word_sentence;
    private String word_sentence_meaning;
    private String word_sentence_url;
    private String word_root;
    private int word_error;
    private int word_gate;
    private int word_time;
    private int word_life;
    private boolean word_start;
    private String word_error_text;

    public String getWord_error_text() {
        return word_error_text;
    }

    public void setWord_error_text(String word_error_text) {
        this.word_error_text = word_error_text;
    }

    public int getWord_error() {
        return word_error;
    }

    public void setWord_error(int word_error) {
        this.word_error = word_error;
    }

    public void setWord_start(boolean word_start) {
        this.word_start = word_start;
    }

    public boolean isWord_start() {
        return word_start;
    }

    public String getWord_music() {
        return word_music;
    }

    public void setWord_music(String word_music) {
        this.word_music = word_music;
    }

    public void setWord_sentence_url(String word_sentence_url) {
        this.word_sentence_url = word_sentence_url;
    }

    public String getWord_sentence_url() {
        return word_sentence_url;
    }

    public void setWord_sentence_meaning(String word_sentence_meaning) {
        this.word_sentence_meaning = word_sentence_meaning;
    }

    public String getWord_sentence_meaning() {
        return word_sentence_meaning;
    }

    public void setWord_sentence(String word_sentence) {
        this.word_sentence = word_sentence;
    }

    public String getWord_sentence() {
        return word_sentence;
    }

    public void setWord_root(String word_root) {
        this.word_root = word_root;
    }

    public String getWord_root() {
        return word_root;
    }

    public void setWord_music_url(String word_music_url) {
        this.word_music_url = word_music_url;
    }

    public String getWord_music_url() {
        return word_music_url;
    }

    public void setWord_life(int word_life) {
        this.word_life = word_life;
    }

    public int getWord_life() {
        return word_life;
    }

    public int getWord_time() {
        return word_time;
    }

    public void setWord_time(int word_time) {
        this.word_time = word_time;
    }

    public int getWord_gate() {
        return word_gate;
    }

    public int getWord_id() {
        return word_id;
    }

    public void setWord_gate(int word_gate) {
        this.word_gate = word_gate;
    }

    public void setWord_id(int word_id) {
        this.word_id = word_id;
    }

    public String getWord_meaning() {
        return word_meaning;
    }

    public void setWord_meaning(String word_meaning) {
        this.word_meaning = word_meaning;
    }

    public void setWord_name(String word_name) {
        this.word_name = word_name;
    }

    public String getWord_name() {
        return word_name;
    }

}
