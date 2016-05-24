package com.example.niems.alarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

public class ViewDefinition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_definition);

        Toolbar toolbar = (Toolbar) findViewById( R.id.my_toolbar );
        setSupportActionBar( toolbar );
        toolbar.setTitle("Words");

        try{
            //modifies the current view
            TextView current_word = (TextView) findViewById( R.id.view_word );
            TextView current_word_def = (TextView) findViewById( R.id.view_word_def );
            String word = "";
            String word_def = "";
            int word_index = 0; //used to find the index of the word the user pressed

            for(; word_index < MainActivity.word_collection.size(); word_index++){

                if(MainActivity.word_collection.get( word_index ).getWord().equals( MainActivity.word_selected ) ){
                    word = MainActivity.word_collection.get( word_index ).getWord();
                    word_def = MainActivity.word_collection.get( word_index ).getWordDef();

                    break; //word found
                }
            }

            current_word.setText( word );
            current_word_def.setText( word_def );

        }catch(Exception e){
            Toast.makeText(this, "Error: ViewDefintion - onCreate()", Toast.LENGTH_SHORT).show();
        }

    }
}
