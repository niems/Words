package com.example.niems.alarm;

import android.widget.Toast;

/**
 * Created by Niems on 5/19/2016.
 */
public class WordEntry {

    private String word; //new word added
    private String word_def; //new word definition added

    public WordEntry(){
        this.word = "";
        this.word_def = "";
    }

    public WordEntry(String word, String word_def){
        this.word = word;
        this.word_def = word_def;
    }

    /*
    public void setWord(String word){
        try{
            this.word = word;
        }catch(Exception e){
            Toast.makeText(this, "Error: WordEntry - setWord", Toast.LENGTH_SHORT).show();
        }
    }
    */
}
