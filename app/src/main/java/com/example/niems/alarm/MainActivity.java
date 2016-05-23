package com.example.niems.alarm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //used to test the functionality of saving from the dialog
    //and
    public ArrayList<WordEntry> word_collection = new ArrayList(); //collection of the words added
    private View.OnClickListener listener;
    private Dialog new_word_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDefinition(v); //used to call the activity to display the definition of the button pushed
            }
        };

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //brings up the dialog box to add a new word
    public void addWord( View view ){

        try{

            new_word_dialog = new Dialog(this);
            new_word_dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
            new_word_dialog.setContentView( R.layout.add_word_dialog );

            new_word_dialog.show();

        }catch(Exception e){
            Toast.makeText(this, "Error: addWord()", Toast.LENGTH_SHORT).show();
        }
    }


    public void dialogCancel( View view ){ //user cancelled adding a new word

        try{
            new_word_dialog.dismiss();

        }catch(Exception e){
            Toast.makeText(this, "Error: dialogCancel()", Toast.LENGTH_SHORT).show();
        }
    }

    public void dialogClear( View view ){ //user clears the new word and definition

        try {


            EditText new_word = (EditText) new_word_dialog.findViewById(R.id.new_word);
            EditText new_word_definition = (EditText) new_word_dialog.findViewById(R.id.new_word_definition);

            //resets new word and definition
            new_word.setText("");
            new_word_definition.setText("");


            /*
            this.new_word_dialog.dismiss(); //destroys current dialog

            this.new_word_dialog = new Dialog(this);
            this.new_word_dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
            this.new_word_dialog.setContentView( R.layout.add_word_dialog );

            this.new_word_dialog.show();
            */



        }catch(Exception e){
            Toast.makeText(this, "Error: dialogClear()", Toast.LENGTH_SHORT).show();
        }
    }

    public void dialogSave( View view ){ //user saves the new word and definition
        try{
            LinearLayout layout = (LinearLayout) findViewById( R.id.words_layout );
            WordEntry current_word = new WordEntry();

            Button b = new Button(this); //used to add to the layout when a new word is entered
            b.setId( View.generateViewId() );
            //use color.colorPrimaryText for this
            //b.setTextColor( TextViewStyles.getResources( getColor(R.color.colorPrimaryText) ) ); //FIGURE OUT HOW TO ASSOCIATE THIS WITH THE COLORS.XML FILE
            b.setTextColor( Color.parseColor("#DD000000") ); //FIGURE OUT HOW TO ASSOCIATE THIS WITH THE COLORS.XML FILE
            b.setTextSize(18);
            b.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) );

            EditText new_word = (EditText) this.new_word_dialog.findViewById( R.id.new_word );
            EditText new_word_def = (EditText) this.new_word_dialog.findViewById( R.id.new_word_definition );

            current_word.setWord( new_word.getText().toString() );
            current_word.setWordDef( new_word_def.getText().toString() );
            word_collection.add( current_word );

            b.setText( new_word.getText().toString() ); //creates a button with the text of the new word
            b.setBackgroundResource( R.drawable.word_collection_main ); //sets the format of the button
            b.setOnClickListener(this.listener);
            layout.addView(b); //adds the button to the layout


            String message = "Word added: " + word_collection.get( (word_collection.size() - 1) ).getWord(); //new_word.getText().toString();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            this.new_word_dialog.dismiss();

        }catch(Exception e){
            Toast.makeText(this, "Error: dialogSave()", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewDefinition( View view ){

        try{
            Button b = (Button) findViewById( view.getId() );
            String word = b.getText().toString();

            //Intent intent = new Intent( this, ViewDefinition.class );
            Toast.makeText(this, word, Toast.LENGTH_SHORT ).show(); //delete this after everything is working
        }catch(Exception e){
            Toast.makeText(this, "Error: viewDefinition()", Toast.LENGTH_SHORT).show();
        }

    }

}
