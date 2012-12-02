package com.hwsknudsen.tamasaver;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Main extends Activity {

	public static final String PREFS_NAME = "MyPrefsFile";
	private boolean firstrun;
	ImageView iv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		//boolean silent = settings.getBoolean("silentMode", false);
		//setSilent(silent);

		setContentView(R.layout.activity_home);

		firstrun = settings.getBoolean("firstRun", true);
		// If first run load settings 
		if(firstrun ){
			firstrun = false;
			Intent intent = new Intent(Main.this,Settings.class);
			startActivity(intent);
		}
		iv = (ImageView) findViewById(R.id.imageView1);
	   
		Thread thread1 = new Thread(){
	    	 public void run(){
	             try {
	                 sleep(1001); 
	                 
	                 
	                 
	             } catch (Exception e) {
	                 e.printStackTrace();
	             }
	         }
	     };
	     thread1.start();
	   

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent intent = new Intent(Main.this,Settings.class);
			startActivity(intent);	
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void buttonClicked(View v) {
//		RotateAnimation rotate = new RotateAnimation(0, 360);
//		rotate.setDuration(500);
//		iv.startAnimation(rotate);
//		
//		TranslateAnimation trans = new TranslateAnimation(0,100,0,100);
//		trans.setDuration(500);
//		iv.startAnimation(trans);
//		
		iv.setX(100);
		iv.setY(100);
		
		//AlphaAnimation, AnimationSet, RotateAnimation, ScaleAnimation, TranslateAnimation 


	}


}
