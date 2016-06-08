package com.example.niems.alarm;

import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Niems on 5/19/2016.
 */
public class WordEntry {

    private String word; //new word added
    private String word_def; //new word definition added
    private Button word_button;

    public WordEntry(){
        this.word = "";
        this.word_def = "";
        this.word_button = null;
    }

    public WordEntry(String word, String word_def, String word_id){
        this.word = word;
        this.word_def = word_def;
    }

    public WordEntry(String word, String word_def, Button button){
        this.word = word;
        this.word_def = word_def;
        this.word_button = button;
    }

    public void setWord(String word){
        this.word = word;
    }

    public void setWordDef(String word_def){
        this.word_def = word_def;
    }

    public void setButton(Button button){ this.word_button = button; }


    public String getWord(){
        return this.word;
    }

    public String getWordDef(){
        return this.word_def;
    }

    public Button getButton() { return this.word_button; }
}
