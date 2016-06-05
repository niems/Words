package com.example.niems.alarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor( getResources().getColor( R.color.colorNotificationBar ) );

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

    public void onStop(){
        super.onStop();
        finish(); //ends activity
    }

    public boolean onCreateOptionsMenu(Menu menu){
        //inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate( R.menu.toolbar_menu_viewdef, menu );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        //handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long as
        //you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if(id == R.id.toolbar_home_viewDef){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        else if(id == R.id.toolbar_help_viewDef){
            Toast.makeText(this, "Press the 'toggle' button for a flashcard style view", Toast.LENGTH_LONG).show();
        }

        else if(id == R.id.toolbar_eraseWord_viewDef){
            //erase out of database
            //erase out of word collection
            //return home


            try{
                Intent intent = new Intent(this, MainActivity.class); //goes to the main activity when started
                //MainActivity.database.delete(MainActivity.database_table_name, MainActivity.database_table_field1 + "=" + MainActivity.word_selected, null); //deletes the current word from the database
                MainActivity.database.execSQL("DELETE FROM" + MainActivity.database_table_name + "WHERE " + MainActivity.database_table_field1 + "='" + MainActivity.word_selected + "'");
                MainActivity.word_collection.remove( this.word_index ); //deletes the current word from the word collection

                startActivity(intent); //returns to main
            }catch(Exception e){
                Toast.makeText(this, "Error: ViewDefinition - onOptionsItemsSelected()", Toast.LENGTH_SHORT).show();
            }


        }

        return super.onOptionsItemSelected(item);
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
