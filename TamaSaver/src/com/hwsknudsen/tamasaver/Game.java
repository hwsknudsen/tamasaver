package com.hwsknudsen.tamasaver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.analytics.tracking.android.EasyTracker;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class Game {
	public static Context context;
	public boolean firstrun;
	static float currentchoice = -1;
	static boolean win;


	public Game(Context context) {
		this.context = context;
	}

	public static void myGame(final Context context) {

		//EasyTracker.getInstance().setContext(Game.context);

		//context.myDB
		//SQLiteDatabase myDB = openOrCreateDatabase(Main.dbname, Main.MODE_PRIVATE, null);

		Cursor mythree = Main.myDB.rawQuery("SELECT * FROM ActionLIST ORDER BY RANDOM() LIMIT 3", null);
		//mythree.moveToNext();
		//getColumnIndex


		mythree.moveToFirst();

		final String one = mythree.getString(mythree.getColumnIndex("Field1"));
		final float onedata = Float.parseFloat(mythree.getString(mythree.getColumnIndex("Field2")));

		mythree.moveToNext();
		final String two  = mythree.getString(mythree.getColumnIndex("Field1"));
		final float twodata = Float.parseFloat(mythree.getString(mythree.getColumnIndex("Field2")));

		mythree.moveToNext();
		final String three = mythree.getString(mythree.getColumnIndex("Field1"));
		final float threedata = Float.parseFloat(mythree.getString(mythree.getColumnIndex("Field2")));


		//		if (twodata>onedata && twodata>threedata){
		//			winnershouldbe = 2;
		//		}else 
		//			if (threedata>onedata && threedata>twodata){
		//			winnershouldbe = 3;
		//		}
		//Log.e("12", mythree.getString(mythree.getColumnIndex("Field1")));

		//		
		final CharSequence[] items = {one, two, three};


		win = false;

		AlertDialog.Builder builder = new AlertDialog.Builder(
				context);
		builder.setTitle("Wich Saves The most Energy?");

		builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				if (currentchoice==-1){
					EasyTracker.getTracker().sendEvent("game", "action", "nochoice", null);
					Toast.makeText(Game.context, "You Need to make a choice or skip no cheating", Toast.LENGTH_SHORT).show();

					Game.myGame(Game.context);

					
				}else if (currentchoice >= onedata && currentchoice >= twodata && currentchoice >= threedata){
					//Main.gameWin(Main.this);

					EasyTracker.getTracker().sendEvent("game", "action", "win", null);


					Toast.makeText(Game.context, "You Win! Keep Playing :)", Toast.LENGTH_SHORT).show();
					Main.changemoodby((int) (100*Math.random()),context);
					//Game.myGame(Game.context);
					Game.myGame(Game.context);

				}else{

					EasyTracker.getTracker().sendEvent("game", "action", "lose", null);


					Toast.makeText(Game.context, "You Lose You're Making Me :(! Please Try Again", Toast.LENGTH_SHORT).show();
					Main.changemoodby((int) (-50*Math.random()),context);
					SharedPreferences settings2 = PreferenceManager.getDefaultSharedPreferences(Game.context);

					//SharedPreferences settings2 = Game.context.getSharedPreferences(Main.CONFIG_Prefs, 0);
					boolean hinteasygame = settings2.getBoolean("easygame", true);
					Log.e("123", String.valueOf(settings2.contains("easygame")));
					if(hinteasygame==true){
						Game.hintDialog(Game.context,one,onedata,two,twodata,three,threedata);
					}else{
						Game.myGame(Game.context);
					}
					
				}

				dialog.cancel();
			}
		});

		builder.setNeutralButton("Exit Game",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				EasyTracker.getTracker().sendEvent("game", "action", "exit", null);


				Toast.makeText(Game.context, "Thanks for playing please Try Again Soon", Toast.LENGTH_LONG).show();
				dialog.cancel();

			}
		});

		builder.setNegativeButton("Skip",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {

				EasyTracker.getTracker().sendEvent("game", "action", "skip", null);


				dialog.cancel();
				Main.changemoodby((int) (-10*Math.random()),context);
				Game.myGame(Game.context);
			}
		});

		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				switch (item) {
				case 0: currentchoice = onedata;
				case 1: currentchoice = twodata;
				case 2: currentchoice = threedata;
				}

			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}

	protected static void hintDialog(Context context2, String one,
			float onedata, String two, float twodata, String three,
			float threedata) {

		final List<sortabletype> numbers = new ArrayList<sortabletype>();
		numbers.add(new sortabletype(one, onedata));
		numbers.add(new sortabletype(two, twodata));
		numbers.add(new sortabletype(three, threedata));
		
		Collections.sort(numbers);
		
		//numbers.get(0).text;
		int twoequal = 2;
		if (numbers.get(0).value==numbers.get(1).value){
			twoequal = 1;
		}
		
		int threequal = 3;
		if (numbers.get(0).value==numbers.get(1).value){
			twoequal = 1;
		}
		if (numbers.get(1).value==numbers.get(2).value){
			twoequal = 2;
		}

		final CharSequence[] items = {1+". "+numbers.get(0).text, twoequal+". "+numbers.get(1).text, threequal+". "+numbers.get(2).text};

		AlertDialog.Builder builder = new AlertDialog.Builder(
				context);
		builder.setTitle("Can I suggest a bettter choice?");


		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
//				switch (item) {
//				case 0: currentchoice = numbers.get(0).value;
//				case 1: currentchoice = numbers.get(1).value;
//				case 2: currentchoice = numbers.get(2).value;
//				}
				Game.myGame(Game.context);

			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}



	public static void motdrandom(final Context context) {


		Cursor mythree = Main.myDB.rawQuery("SELECT * FROM ActionLIST ORDER BY RANDOM() LIMIT 1", null);
		mythree.moveToFirst();

		String one = mythree.getString(mythree.getColumnIndex("Field1"));
		final float onedata = Float.parseFloat(mythree.getString(mythree.getColumnIndex("Field2")));
		Builder alertDialogBuilder = new Builder(
				context);

		// set title
		alertDialogBuilder.setTitle("Did You Know?");

		// set dialog message
		alertDialogBuilder
		.setMessage(one+"Saves Approximately: "+onedata+" Grams of Carbon")
		.setCancelable(false) 
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {

				//Toast.makeText(Game.context, "You Lose You're Making Me :(! Please Try Again", Toast.LENGTH_LONG).show();
				Main.changemoodby((int) (+10*Math.random()),context);
				dialog.cancel();
			}
		})

		;

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}


}