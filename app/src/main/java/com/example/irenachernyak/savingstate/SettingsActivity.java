package com.example.irenachernyak.savingstate;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by irenachernyak on 8/11/15.
 */
public class SettingsActivity extends PreferenceActivity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

    }
}
