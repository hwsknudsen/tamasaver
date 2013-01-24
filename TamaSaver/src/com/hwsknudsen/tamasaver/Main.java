package com.hwsknudsen.tamasaver;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.CheckBox;
import android.widget.ImageView;

public class Main extends Activity{

	public static final String PREFS_NAME = "main_prefs";
	public static final String CONFIG_Prefs = "config_prefs";

	Game data = new Game(this);

	public static ImageView iv;

	public static ArrayList<Actions> goingto = new ArrayList<Actions>();
	public static int currentFace;
	public static int currentmood;

	public static SharedPreferences settings2;

	//public static SharedPreferences userconfig;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences settings2 = getSharedPreferences(CONFIG_Prefs, 0);

		//userconfig = getSharedPreferences(Main.CONFIG_Prefs, 0);

		setContentView(R.layout.activity_home);

		data.firstrun = settings.getBoolean("firstRun", true);
		String dbname = settings.getString("dbname", "mydb");

		currentmood = settings.getInt("mood", 500);

		
		// If first run load settings 

		SQLiteDatabase myDB = this.openOrCreateDatabase(dbname, MODE_PRIVATE, null);

		if(data.firstrun ){
			// & setup DB 
			
			String ActionLIST = "ActionLIST";
			/* Create a Table in the Database. */
			myDB.execSQL("CREATE TABLE IF NOT EXISTS "
					+ ActionLIST 
					+ " (Field1 VARCHAR, Field2 FLOAT(10));");

			/* Insert data to a Table*/
			myDB.execSQL("INSERT INTO "
					+ ActionLIST
					+ " (Field1, Field2)"
					+ " VALUES ('GET AN ENERGY MONITOR', 2.512);");


			data.firstrun = false;
			Intent intent = new Intent(Main.this,Settings.class);
			startActivity(intent);
		}
		iv = (ImageView) findViewById(R.id.imageView1);

		currentFace = R.drawable.path3890;
		iv.setImageResource(currentFace);



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

		//		animations.GoTO(new PointF(100,100));
		//		update();
		//motdrandom();
		
		Game.myGame(data.context);
	}



	public void motdrandom() {
		
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				data.context);

		// set title
		alertDialogBuilder.setTitle("Did You Know?");

		// set dialog message
		alertDialogBuilder
		.setMessage("Energy Usage IS BAD!")
		.setCancelable(false)
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		})

		;

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	public void buttonClicked2(View v) {
		animations.do360();
		//animations.GoTO(new PointF(200,200));
		update();

	}


	public void bC1(View v){
		currentmood = currentmood + 50;
		animations.jump();
		animations.Wink();
		if (currentmood>=600){
			animations.do360();
		}
		//animations.GoTO(new PointF(200,200));
		update();
	}
	
	public void bC2(View v){
		currentmood = currentmood - 50;
		animations.Wink();
		if (currentmood<=500){
			animations.Wink();
			//animations.jump();
		}
		//animations.GoTO(new PointF(200,200));
		update();
	}
	
	
	private void update() {

		if(!goingto.isEmpty()){
			Actions thisanimation = goingto.get(0);
			goingto.remove(0);
			if (thisanimation.typeflag==0){
				iv.setX(thisanimation.point.x);
				iv.setY(thisanimation.point.y);
				thisanimation.as.setAnimationListener(new AnimationListener(){
					@Override
					public void onAnimationEnd(Animation arg0) {
						update();
					}
					@Override
					public void onAnimationRepeat(Animation arg0) {
					}
					@Override
					public void onAnimationStart(Animation arg0) {

					}

				});
				iv.startAnimation(thisanimation.as);
			} else if (thisanimation.typeflag==1){
				iv.setImageResource(thisanimation.character);
				if (thisanimation.permeant==true){
					currentFace=thisanimation.character;
				}
				update();
			}
		}	
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//Log.e("hi","hi");
		animations.GoTO(new PointF(event.getX()-(iv.getWidth()/2),event.getY()-(iv.getHeight()/2)));
		Main.goingto.add(new Actions(R.drawable.path3890,true));

		//iv.setImageResource(R.drawable.path3890);
		update();
		return true;
	}
}


