package com.music.clocklive.wallpaper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.music.clocklive.wallpaper.R;


public class SettingsActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent=new Intent(getApplicationContext(),MySettingsActivity.class);
		intent.putExtra("ID","SettingsActivity");
		startActivity(intent);
		finish();
		addPreferencesFromResource(R.xml.prefs);
	}

}
