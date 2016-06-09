package com.example.niems.alarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class WordCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_category);

        try{
            Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar_category );
            setSupportActionBar( toolbar );
            toolbar.setTitle("Words");
            Window window = getWindow();

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color
            window.setStatusBarColor( getResources().getColor( R.color.colorNotificationBar ) );

            TextView category = (TextView) findViewById( R.id.current_word_category );
            Bundle bundle = getIntent().getExtras();
            String selected_category = bundle.getString( MainActivity.current_category );

            category.setText( selected_category );

        }catch(Exception e){
            Toast.makeText(this, "Error: WordCategory - onCreate()", Toast.LENGTH_SHORT).show();
        }
    }

    public void onStop(){
        try{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent); //takes the user to the main activity

            super.onStop();
            finish(); //kills the activity so it's not running in the background

        }catch(Exception e){
            Toast.makeText(this, "Error: WordCategory - onStop()", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        super.onStop();
        finish(); //kills the current activity and takes the user to the main activity
    }
}
