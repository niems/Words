package com.example.niems.alarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ViewDefinition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            TextView current_word = (TextView) findViewById( R.id.word );
            TextView current_word_def = (TextView) findViewById( R.id.word_def );

            int word_index = 0;

            for(; word_index < MainActivity.word_collection.size(); word_index++){

                if(MainActivity.word_collection.get( word_index ).getWord().equals( MainActivity.word_selected ) ){
                    break; //word found
                }
            }

            current_word.setText( MainActivity.word_collection.get( word_index ).getWord() );
            current_word_def.setText( MainActivity.word_collection.get( word_index ).getWordDef() );
        }catch(Exception e){
            Toast.makeText(this, "Error: ViewDefintion - onCreate()", Toast.LENGTH_SHORT).show();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_definition);
    }
}
