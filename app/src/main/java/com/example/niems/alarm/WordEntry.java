package com.example.niems.alarm;

import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Niems on 5/19/2016.
 */
public class WordEntry {

    private String word; //new word added
    private String word_def; //new word definition added
    private String category; //category of new word


    public WordEntry(){
        this.word = "";
        this.word_def = "";
        this.category = "";
    }

    public WordEntry(String word, String word_def, String category){
        this.word = word;
        this.word_def = word_def;
        this.category = category;
    }

    public void setWord(String word){
        this.word = word;
    }

    public void setWordDef(String word_def){
        this.word_def = word_def;
    }

    public void setCategory(String category) { this.category = category; }


    public String getWord(){
        return this.word;
    }

    public String getWordDef(){
        return this.word_def;
    }

    public void getCategory(String category) { this.category = category; }
}
