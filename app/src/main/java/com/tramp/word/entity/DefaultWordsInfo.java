package com.tramp.word.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/13
 * version:1.0
 */
public class DefaultWordsInfo implements Parcelable {
    private List<Word> words;

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public static class Word implements Parcelable{
        private int word_id;
        private String word_name;
        private String word_meaning;
        private String word_music;
        private String word_music_url;
        private String word_sentence;
        private String word_sentence_meaning;
        private String word_sentence_url;
        private String word_root;
        private int word_error;
        private String word_error_text;
        private int word_gate;
        private int word_life;
        private int word_start;

        public String getWord_error_text() {
            return word_error_text;
        }

        public void setWord_error_text(String word_error_text) {
            this.word_error_text = word_error_text;
        }

        public String getWord_root() {
            return word_root;
        }

        public void setWord_root(String word_root) {
            this.word_root = word_root;
        }

        public String getWord_music() {
            return word_music;
        }

        public void setWord_music(String word_music) {
            this.word_music = word_music;
        }

        public String getWord_sentence_url() {
            return word_sentence_url;
        }

        public void setWord_sentence_url(String word_sentence_url) {
            this.word_sentence_url = word_sentence_url;
        }

        public int getWord_life() {
            return word_life;
        }

        public void setWord_life(int word_life) {
            this.word_life = word_life;
        }

        public String getWord_music_url() {
            return word_music_url;
        }

        public void setWord_music_url(String word_music_url) {
            this.word_music_url = word_music_url;
        }

        public int getWord_id() {
            return word_id;
        }

        public void setWord_gate(int word_gate) {
            this.word_gate = word_gate;
        }

        public void setWord_error(int word_error) {
            this.word_error = word_error;
        }

        public int getWord_gate() {
            return word_gate;
        }

        public void setWord_start(int word_start) {
            this.word_start = word_start;
        }

        public void setWord_id(int word_id) {
            this.word_id = word_id;
        }

        public int getWord_error() {
            return word_error;
        }

        public void setWord_meaning(String word_meaning) {
            this.word_meaning = word_meaning;
        }

        public int getWord_start() {
            return word_start;
        }

        public void setWord_name(String word_name) {
            this.word_name = word_name;
        }

        public String getWord_meaning() {
            return word_meaning;
        }

        public String getWord_name() {
            return word_name;
        }

        public void setWord_sentence_meaning(String word_sentence_meaning) {
            this.word_sentence_meaning = word_sentence_meaning;
        }

        public String getWord_sentence_meaning() {
            return word_sentence_meaning;
        }

        public String getWord_sentence() {
            return word_sentence;
        }

        public void setWord_sentence(String word_sentence) {
            this.word_sentence = word_sentence;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.word_id);
            dest.writeString(this.word_name);
            dest.writeString(this.word_meaning);
            dest.writeString(this.word_music);
            dest.writeString(this.word_music_url);
            dest.writeString(this.word_sentence);
            dest.writeString(this.word_sentence_meaning);
            dest.writeString(this.word_sentence_url);
            dest.writeString(this.word_root);
            dest.writeInt(this.word_error);
            dest.writeInt(this.word_gate);
            dest.writeInt(this.word_life);
            dest.writeInt(this.word_start);
            dest.writeString(this.word_error_text);
        }
        public Word(){

        }

        private Word(Parcel in){
            this.word_id=in.readInt();
            this.word_name=in.readString();
            this.word_meaning=in.readString();
            this.word_music=in.readString();
            this.word_music_url=in.readString();
            this.word_sentence=in.readString();
            this.word_sentence_meaning=in.readString();
            this.word_sentence_url=in.readString();
            this.word_root=in.readString();
            this.word_life=in.readInt();
            this.word_error=in.readInt();
            this.word_gate=in.readInt();
            this.word_start =in.readInt();
            this.word_error_text=in.readString();
        }

        public static final Creator<Word> CREATOR = new Creator<Word>() {
            @Override
            public Word createFromParcel(Parcel source) {
                return new Word(source);
            }

            @Override
            public Word[] newArray(int size) {
                return new Word[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.words);
    }

    public DefaultWordsInfo(){

    }

    protected DefaultWordsInfo(Parcel in){
        this.words=new ArrayList<>();
        in.readList(this.words,Word.class.getClassLoader());
    }

    public static final Creator<DefaultWordsInfo> CREATOR=new Creator<DefaultWordsInfo>() {
        @Override
        public DefaultWordsInfo createFromParcel(Parcel source) {
            return new DefaultWordsInfo(source);
        }

        @Override
        public DefaultWordsInfo[] newArray(int size) {
            return new DefaultWordsInfo[size];
        }
    };
}




















