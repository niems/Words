package com.example.niems.alarm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addWord( View view ){
        /*
        LinearLayout layout = (LinearLayout) findViewById( R.id.words_layout );
        Button word_button = new Button(this);
        String string_button = "Word 1";
        word_button.setText( string_button );

        layout.addView( word_button );


        */

        Dialog new_word = new Dialog(this);
        new_word.setContentView( R.layout.add_word_dialog );
        new_word.show();


    }

    public void dialogCancel( View view ){ //user cancelled adding a new word

    }

    public void dialogClear( View view ){ //user clears the new word and definition
        
    }

    public void dialogSave( View view ){ //user saves the new word and definition

    }
}
