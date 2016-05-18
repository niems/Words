package com.example.niems.alarm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addWord( View view ){

        //brings up the dialog box to add a new word
        Dialog new_word = new Dialog(this);
        new_word.setContentView( R.layout.add_word_dialog );
        new_word.show();

    }

    public void dialogCancel( View view ){ //user cancelled adding a new word

    }

    public void dialogClear( View view ){ //user clears the new word and definition

        if( view.isShown() ){
            EditText new_word = (EditText) findViewById( R.id.new_word );
            EditText new_word_definition = (EditText) findViewById( R.id.new_word_definition );

            //resets new word and definition
            new_word.setText( R.string.new_word );
            new_word_definition.setText( R.string.new_word_definition );
        }

    }

    public void dialogSave( View view ){ //user saves the new word and definition

    }
}
