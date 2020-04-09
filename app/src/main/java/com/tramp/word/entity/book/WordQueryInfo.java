package com.tramp.word.entity.book;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/01
 * version:1.0
 */
public class WordQueryInfo {
    private int code;
    private ArrayList<Word> word;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Word> getWord() {
        return word;
    }

    public void setWord(ArrayList<Word> word) {
        this.word = word;
    }

    public class Word{
        //单词本所属 属于1023词书
        private int word_book;

        //单词关卡 属于第23关
        private int word_gate;

        //单词名称 列子:form
        private String word_name;

        //单词音标路径 列如: www.yiyuzhou.com/music/1023.mp3
        private String word_music_url;

        //单词音标 列如:[dada]
        private String word_music;

        //单词释义 列如:来自
        private String word_meaning;

        //单词列句 列如:who a you
        private String word_sentence;

        //单词列句音标 列如:www.yiyuzhou.com/music/102354.mp3
        private String word_sentence_url;

        //单词列句释义 列如 你好啊!
        private String word_sentence_meaning;

        //单词词根 列如 come+form(dadad)
        private String word_root;

        //单词错误状态 列如 0无状态 1错误 2正确
        private int word_error;

        //单词错误文本 列如 释义 音标
        private String word_error_text;

        //生词本状态 0没有加入 1加入
        private int word_life;

        //单词字母排序 a b c
        private int word_letter;

        //学习状态 0没有学习 1复习 2认识
        private int word_study;

        //复习时间 201904056
        private int word_time;

        public String getWord_name() {
            return word_name;
        }

        public void setWord_name(String word_name) {
            this.word_name = word_name;
        }

        public int getWord_book() {
            return word_book;
        }

        public void setWord_book(int word_book) {
            this.word_book = word_book;
        }

        public int getWord_error() {
            return word_error;
        }

        public void setWord_error(int word_error) {
            this.word_error = word_error;
        }

        public int getWord_gate() {
            return word_gate;
        }

        public void setWord_error_text(String word_error_text) {
            this.word_error_text = word_error_text;
        }

        public int getWord_letter() {
            return word_letter;
        }

        public void setWord_gate(int word_gate) {
            this.word_gate = word_gate;
        }

        public int getWord_life() {
            return word_life;
        }

        public void setWord_letter(int word_letter) {
            this.word_letter = word_letter;
        }

        public int getWord_study() {
            return word_study;
        }

        public void setWord_life(int word_life) {
            this.word_life = word_life;
        }

        public int getWord_time() {
            return word_time;
        }

        public void setWord_meaning(String word_meaning) {
            this.word_meaning = word_meaning;
        }

        public String getWord_error_text() {
            return word_error_text;
        }

        public void setWord_music(String word_music) {
            this.word_music = word_music;
        }

        public String getWord_meaning() {
            return word_meaning;
        }

        public void setWord_music_url(String word_music_url) {
            this.word_music_url = word_music_url;
        }

        public String getWord_music() {
            return word_music;
        }

        public void setWord_root(String word_root) {
            this.word_root = word_root;
        }

        public String getWord_music_url() {
            return word_music_url;
        }

        public void setWord_sentence(String word_sentence) {
            this.word_sentence = word_sentence;
        }

        public String getWord_root() {
            return word_root;
        }

        public void setWord_sentence_meaning(String word_sentence_meaning) {
            this.word_sentence_meaning = word_sentence_meaning;
        }

        public String getWord_sentence() {
            return word_sentence;
        }

        public void setWord_sentence_url(String word_sentence_url) {
            this.word_sentence_url = word_sentence_url;
        }

        public String getWord_sentence_meaning() {
            return word_sentence_meaning;
        }

        public void setWord_study(int word_study) {
            this.word_study = word_study;
        }

        public String getWord_sentence_url() {
            return word_sentence_url;
        }

        public void setWord_time(int word_time) {
            this.word_time = word_time;
        }
    }
}
