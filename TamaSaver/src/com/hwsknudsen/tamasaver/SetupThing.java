package com.hwsknudsen.tamasaver;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SetupThing extends PreferenceFragment{
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.layout.prefrence);
    }

}
