package com.example.niems.alarm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //used to test the functionality of saving from the dialog
    //and
    private Dialog new_word_dialog;
    private String test_word;
    private String test_word_definition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.test_word = "";
        this.test_word_definition = "";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addWord( View view ){

        //brings up the dialog box to add a new word
        try{

            new_word_dialog = new Dialog(this);
            new_word_dialog.setContentView( R.layout.add_word_dialog );
            //new_word_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE );
            new_word_dialog.show();

        }catch(Exception e){
            Toast.makeText(this, "Error: addWord()", Toast.LENGTH_SHORT).show();
        }
    }


    public void dialogCancel( View view ){ //user cancelled adding a new word

        try{
            new_word_dialog.dismiss();

            LinearLayout layout = (LinearLayout) findViewById( R.id.words_layout );

            Button button = new Button(this);
            button.setBackgroundResource( R.drawable.button_bg_dialogbox );

            layout.addView( button );
        }catch(Exception e){
            Toast.makeText(this, "Error: dialogCancel()", Toast.LENGTH_SHORT);
        }

    }

    public void dialogClear( View view ){ //user clears the new word and definition

        try {
            EditText new_word = (EditText) new_word_dialog.findViewById(R.id.new_word);
            EditText new_word_definition = (EditText) new_word_dialog.findViewById(R.id.new_word_definition);

            //resets new word and definition
            new_word.setText(R.string.new_word);
            new_word_definition.setText(R.string.new_word_definition);

        }catch(Exception e){
            Toast.makeText(this, "Error: dialogClear()", Toast.LENGTH_SHORT).show();
        }


    }

    public void dialogSave( View view ){ //user saves the new word and definition
        try{
            String message = "Word added: ";
            this.test_word = new_word_dialog.findViewById( R.id.new_word ).toString();
            this.test_word_definition = new_word_dialog.findViewById( R.id.new_word_definition ).toString();
            new_word_dialog.dismiss();

            message += this.test_word; //used to output new word on the screen

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        }catch(Exception e){
            Toast.makeText(this, "Error: dialogSave()", Toast.LENGTH_SHORT).show();
        }
    }

}
