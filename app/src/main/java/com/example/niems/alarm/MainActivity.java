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
    public static String database_table_name = "WordList";
    public static String database_table_field1 = "Word";
    public static String database_table_field2 = "Definition";

    public static String current_category = "CURRENT_CATEGORY";

    public static ArrayList<WordEntry> word_collection = new ArrayList(); //collection of the words added
    public static SQLiteDatabase database = null;
    private View.OnClickListener listener;
    private Dialog new_word_dialog;


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

    public void onStop(){
        super.onStop();
        finish(); //kills the current activity
    }

    public void onBackPressed(){
        finish();
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

        try{
            int id = item.getItemId();

            if(id == R.id.toolbar_help_main){
                Toast.makeText(this, "Press 'add word' to add a new word to the list!", Toast.LENGTH_LONG).show();
                return true;
            }

            else if(id == R.id.toolbar_trash_main){
                LinearLayout layout = (LinearLayout) findViewById( R.id.words_layout );
                Button delete_button;
                Intent intent = new Intent(this, MainActivity.class);

                finish();
                database.delete( database_table_name, null, null ); //deletes everything from the table


                /*
                //layout.removeAllViews();
                for(int i = 0; i < word_collection.size(); i++){ //loops through and removes all words by id
                    delete_button = (Button) findViewById( word_collection.get(i).getButton().getId() );
                    layout.removeView( delete_button );
                }
                */

                this.deleteDatabase(database_name); //deletes the database
                word_collection.clear(); //removes all elements from list
                openDatabase(); //creates the new database

                startActivity(intent);
            }

        }catch(Exception e){
            Toast.makeText(this, "Error: MainActivity - onOptionsItemSelected", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDatabase(){ //opens database if it exists, otherwise creates it
        try{
            //Toast.makeText(this, "openDatabase() called", Toast.LENGTH_SHORT).show();
            File file_dir = getFilesDir();
            String path = file_dir.getPath();

            database = openOrCreateDatabase(database_name, Context.MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS " + database_table_name + "(" + database_table_field1 + " VARCHAR," + database_table_field2 + " VARCHAR);");

            LinearLayout layout = (LinearLayout) findViewById( R.id.words_layout );
            Cursor cursor = database.rawQuery("Select * FROM WordList", null);


            if( cursor.getCount() == 0 ){ //user doesn't have any words
                Toast.makeText(this, "You have no words! Use the Add Word button to add a new word :D", Toast.LENGTH_LONG).show();
            }

            else{
                //Toast.makeText(this, "Word count: " + Integer.toString( cursor.getCount() ), Toast.LENGTH_SHORT).show();
                word_collection.clear();

                while( cursor.moveToNext() ){ //loops while the next entry exists
                    WordEntry current_word = new WordEntry();
                    current_word.setWord( cursor.getString(0) ); //gets word from current row
                    current_word.setWordDef( cursor.getString(1) ); //gets word definition from current row

                    Button b = new Button(this); //used to add to the layout when a new word is entered
                    b.setId( View.generateViewId() );

                    b.setTextColor( Color.parseColor("#DD000000") ); //FIGURE OUT HOW TO ASSOCIATE THIS WITH THE COLORS.XML FILE
                    b.setTextSize(18);
                    b.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) );

                    b.setText( current_word.getWord() ); //creates a button with the text of the new word
                    b.setBackgroundResource( R.drawable.word_collection_main ); //sets the format of the button
                    b.setOnClickListener(this.listener);
                    current_word.setButton( b ); //stores the current buttom to use for deleting from view if user chooses
                    word_collection.add( current_word );

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

            b.setText( new_word.getText().toString() ); //creates a button with the text of the new word
            b.setBackgroundResource( R.drawable.word_collection_main ); //sets the format of the button
            b.setOnClickListener(this.listener);

            current_word.setWord( new_word.getText().toString() );
            current_word.setWordDef( new_word_def.getText().toString() );
            current_word.setButton( b );
            //current_word.setWordId( Integer.toString( b.getId() ) );
            word_collection.add( current_word );

            layout.addView(b); //adds the button to the layout

            //add to database
            //this.database.execSQL("INSERT INTO WordList VALUES('word1', 'def1');");
            database.execSQL("INSERT INTO WordList VALUES('" + current_word.getWord() + "', '" + current_word.getWordDef() + "');" );

            String message = "Word added: " + word_collection.get( (word_collection.size() - 1) ).getWord(); //new_word.getText().toString();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            this.new_word_dialog.dismiss();


        }catch(Exception e){
            Toast.makeText(this, "Error: dialogSave()", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewDefinition( View view ){

        try{
            finish(); //ends the current view since a new one is starting
            Button b = (Button) findViewById( view.getId() );
            word_selected = b.getText().toString(); //used to display word and definition in view definition activity

            Intent intent = new Intent( this, ViewDefinition.class );
            //intent.putExtra(MainActivity.current_category, )
            startActivity(intent);
        }catch(Exception e){
            Toast.makeText(this, "Error: viewDefinition()", Toast.LENGTH_SHORT).show();
        }

    }

    public void viewCategory( View view ){ //change the listener so it calls this ( in onCreate() )
        try{
            Intent intent = new Intent(this, WordCategory.class);
            Button category_button = (Button) findViewById( view.getId() );
            intent.putExtra( MainActivity.current_category, category_button.getText().toString() );
            startActivity(intent);

        }catch(Exception e){
            Toast.makeText(this, "Error: MainActivity - viewCategory()", Toast.LENGTH_SHORT).show();
        }
    }

}
