package com.example.niems.alarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewDefinition extends AppCompatActivity {
    //public static final String view_word = "display word";
    //public static final String view_def = "display definition";

    private int word_index; //index of user clicked word

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_definition);

        Toolbar toolbar = (Toolbar) findViewById( R.id.my_toolbar );
        setSupportActionBar( toolbar );
        toolbar.setTitle("Words");
        toolbar.setLogo( R.drawable.mind_map );

        try{
            //modifies the current view
            TextView current_word = (TextView) findViewById( R.id.view_word );
            TextView current_word_def = (TextView) findViewById( R.id.view_word_def );
            String word;      //temporary storage for current word
            String word_def; //temporary storage for current word definition

            if( findWordIndex() ){ //if the current word is found
                word = MainActivity.word_collection.get( this.word_index ).getWord();
                word_def = MainActivity.word_collection.get( this.word_index ).getWordDef();

                current_word.setText( word );
                current_word_def.setText( word_def );
            }
            else{
                Toast.makeText(this, "Word not found", Toast.LENGTH_SHORT).show();
            }


        }catch(Exception e){
            Toast.makeText(this, "Error: ViewDefintion - onCreate()", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean findWordIndex(){ //returns the current word index if found, or -1
        boolean found_index = false;

        for(int i = 0; i < MainActivity.word_collection.size(); i++) {

            if (MainActivity.word_collection.get(i).getWord().equals(MainActivity.word_selected)) {
                found_index = true; //the word has been found
                this.word_index = i; //index for the current word
                break; //word found
            }
        }

        return found_index;
    }

    public void toggleView( View view ){

        try{
            TextView current_word = (TextView) findViewById( R.id.view_word );
            TextView current_word_def = (TextView) findViewById( R.id.view_word_def );


            if( current_word_def.getText().toString().equals("") ){ //needs to display the definition
                current_word_def.setText( MainActivity.word_collection.get( this.word_index ).getWordDef() );
            }

            else if( current_word_def.getText().toString().equals( MainActivity.word_collection.get( this.word_index ).getWordDef() ) ){ //needs to only display the word
                current_word_def.setText("");
            }

            else{
                Toast.makeText(this, "Error: View Defintion - toggleView()", Toast.LENGTH_SHORT).show();
            }

        }catch(Exception e){
            Toast.makeText(this, "Error: ViewDefinition - toggleView()", Toast.LENGTH_SHORT).show();
        }
    }


}
