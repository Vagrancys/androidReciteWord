package com.tramp.word.entity.recite;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/13
 * version:1.0
 */
public class DefaultAnimInfo {
    private int anim_number;
    private int word_number;
    private String word_name;
    private String word_meaning;
    private String word_music_url;
    private String word_sentence;
    private String word_sentence_meaning;
    private SelectAnimInfo select;
    private SpellAnimInfo spell;
    private AudioAnimInfo audio;
    private FillAnimInfo fill;

    public AudioAnimInfo getAudio() {
        return audio;
    }

    public void setSpell(SpellAnimInfo spell) {
        this.spell = spell;
    }

    public FillAnimInfo getFill() {
        return fill;
    }

    public void setFill(FillAnimInfo fill) {
        this.fill = fill;
    }

    public SpellAnimInfo getSpell() {
        return spell;
    }

    public void setAudio(AudioAnimInfo audio) {
        this.audio = audio;
    }

    public String getWord_music_url() {
        return word_music_url;
    }

    public void setWord_music_url(String word_music_url) {
        this.word_music_url = word_music_url;
    }

    public String getWord_sentence() {
        return word_sentence;
    }

    public void setWord_sentence(String word_sentence) {
        this.word_sentence = word_sentence;
    }

    public String getWord_name() {
        return word_name;
    }

    public void setWord_name(String word_name) {
        this.word_name = word_name;
    }

    public String getWord_meaning() {
        return word_meaning;
    }

    public void setWord_meaning(String word_meaning) {
        this.word_meaning = word_meaning;
    }

    public void setAnim_number(int anim_number) {
        this.anim_number = anim_number;
    }

    public int getAnim_number() {
        return anim_number;
    }

    public int getWord_number() {
        return word_number;
    }

    public SelectAnimInfo getSelect() {
        return select;
    }

    public void setSelect(SelectAnimInfo select) {
        this.select = select;
    }

    public void setWord_number(int word_number) {
        this.word_number = word_number;
    }

    public String getWord_sentence_meaning() {
        return word_sentence_meaning;
    }

    public void setWord_sentence_meaning(String word_sentence_meaning) {
        this.word_sentence_meaning = word_sentence_meaning;
    }
}
