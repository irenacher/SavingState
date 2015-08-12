package com.example.irenachernyak.savingstate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText notesEditText;
    Button buttonSettings;
    private static final int SETTING_INFO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       notesEditText = (EditText)findViewById(R.id.notes_editText);

        if(savedInstanceState != null){
            String notes = savedInstanceState.getString("NOTES");
            notesEditText.setText(notes);
        }

        // get data that probably has been stored in SharedPreferences
        String spNotes = getPreferences(Context.MODE_PRIVATE).getString("NOTES", "EMPTY");
        if(!spNotes.equals("EMPTY")){
            notesEditText.setText(spNotes);
        }

        buttonSettings = (Button)findViewById(R.id.button_settings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent preferencesIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(preferencesIntent, SETTING_INFO);
            }
        });
    }

    /*
    this method is called by android whenever user terminates application
     */

    @Override
    protected void onStop() {
        saveSettings();
         super.onStop();
    }

    /*
    this method preserves data when
    device changes orientation and also when application is terminated for any reason by android (NOT by user)
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        if(!notesEditText.getText().toString().equals("")) {
            outState.putString("NOTES", notesEditText.getText().toString());
            super.onSaveInstanceState(outState, outPersistentState);
        }
    }

    /*
    this method preserves data when
    user terminates application    (using SharedPreferences)
     */

    private void saveSettings() {
        SharedPreferences.Editor spEditor = getPreferences(Context.MODE_PRIVATE).edit(); // MODE_PRIVATE means that only this app can access stored data;
        if(notesEditText != null) {                                                                                 // edit() means that app can change data
            spEditor.putString("NOTES", notesEditText.getText().toString());
            spEditor.commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SETTING_INFO){
            updateNoteText();
        }
    }

    private void updateNoteText(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(preferences.getBoolean("pref_text_bold", false)){
            notesEditText.setTypeface(null, Typeface.BOLD);
        } else {
            notesEditText.setTypeface(null, Typeface.NORMAL);
        }

        String textSizePref = preferences.getString("pref_text_size","16");
        float textsize = Float.parseFloat(textSizePref);
        notesEditText.setTextSize(textsize);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
