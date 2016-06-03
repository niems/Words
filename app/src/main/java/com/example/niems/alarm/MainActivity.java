package com.example.niems.alarm;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //used to test the functionality of saving from the dialog
    //and
    //**WRITE CODE TO REARRANGE WORDS AT RUN-TIME BASED ON ALPHABETICAL ORDER WHEN THE USER SAVES A NEW WORD
    public static String word_selected = ""; //saved word the user clicked
    public static String database_name = "WordsDB";
    public static ArrayList<WordEntry> word_collection = new ArrayList(); //collection of the words added
    private View.OnClickListener listener;
    private Dialog new_word_dialog;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDefinition(v); //used to call the activity to display the definition of the button pushed
            }
        };

        try{
            Toolbar toolbar = (Toolbar) findViewById( R.id.my_toolbar );
            setSupportActionBar( toolbar );
            toolbar.setTitle("Words");
            toolbar.setLogo( R.drawable.mind_map );

            Window window = getWindow();

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color
            window.setStatusBarColor( getResources().getColor( R.color.colorNotificationBar ) );

            openDatabase();

        }catch(Exception e){
            Toast.makeText(this, "Error: MainActivity - onCreate()", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        //inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate( R.menu.toolbar_menu_main, menu );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        //handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long as
        //you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if(id == R.id.toolbar_help_main){
            Toast.makeText(this, "Press 'add word' to add a new word to the list!", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDatabase(){ //opens database if it exists, otherwise creates it
        try{

            File file_dir = getFilesDir();
            String path = file_dir.getPath();

            //this.deleteDatabase( database_name );
            this.database = openOrCreateDatabase(database_name, Context.MODE_PRIVATE, null);
            this.database.execSQL("CREATE TABLE IF NOT EXISTS WordList(Word VARCHAR,Definition VARCHAR);");

            LinearLayout layout = (LinearLayout) findViewById( R.id.words_layout );
            Cursor cursor = this.database.rawQuery("Select * FROM WordList", null);

            if( cursor.getCount() == 0 ){ //user doesn't have any words
                Toast.makeText(this, "You have no words! Use the Add Word button to add a new word :D", Toast.LENGTH_LONG).show();
            }

            else{

                Toast.makeText(this, "Word count: " + Integer.toString( cursor.getCount() ), Toast.LENGTH_SHORT).show();

                while( cursor.moveToNext() ){ //loops while the next entry exists
                    WordEntry current_word = new WordEntry();

                    current_word.setWord( cursor.getString(0) ); //gets word from current row
                    current_word.setWordDef( cursor.getString(1) ); //gets word definition from current row
                    word_collection.add( current_word );

                    Button b = new Button(this); //used to add to the layout when a new word is entered
                    b.setId( View.generateViewId() );

                    b.setTextColor( Color.parseColor("#DD000000") ); //FIGURE OUT HOW TO ASSOCIATE THIS WITH THE COLORS.XML FILE
                    b.setTextSize(18);
                    b.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) );

                    b.setText( current_word.getWord() ); //creates a button with the text of the new word
                    b.setBackgroundResource( R.drawable.word_collection_main ); //sets the format of the button
                    b.setOnClickListener(this.listener);
                    layout.addView(b); //adds the button to the layout
                }
            }

            cursor.close();
        }catch(Exception e){
            Toast.makeText(this, "Error: MainActivity - openDatabase()", Toast.LENGTH_SHORT).show();
        }
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

            //add to database
            //this.database.execSQL("INSERT INTO WordList VALUES('word1', 'def1');");
            this.database.execSQL("INSERT INTO WordList VALUES('" + current_word.getWord() + "', '" + current_word.getWordDef() + "');" );

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
            word_selected = word; //used to display word and definition in view definition activity

            Intent intent = new Intent( this, ViewDefinition.class );
            startActivity(intent);
        }catch(Exception e){
            Toast.makeText(this, "Error: viewDefinition()", Toast.LENGTH_SHORT).show();
        }

    }

}
