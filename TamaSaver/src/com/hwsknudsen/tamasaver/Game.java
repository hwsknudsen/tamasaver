package com.hwsknudsen.tamasaver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.google.analytics.tracking.android.EasyTracker;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class Game {
	public static Context context;
	public boolean firstrun;
	static float currentchoice = -1;
	static boolean win;

	//static float max = -1;

	public Game(Context context) {
		this.context = context;
	}

	public static void myGame(final Context context) {

		Cursor mythree = Main.myDB.rawQuery("SELECT * FROM ActionLIST ORDER BY RANDOM() LIMIT 3", null);


		mythree.moveToFirst();

		final float onedata = Float.parseFloat(mythree.getString(mythree.getColumnIndex("Field2")));
		final String one = mythree.getString(mythree.getColumnIndex("Field1"));//+":"+onedata;

		mythree.moveToNext();
		final float twodata = Float.parseFloat(mythree.getString(mythree.getColumnIndex("Field2")));
		final String two  = mythree.getString(mythree.getColumnIndex("Field1"));//+":"+twodata;

		mythree.moveToNext();
		final float threedata = Float.parseFloat(mythree.getString(mythree.getColumnIndex("Field2")));
		final String three = mythree.getString(mythree.getColumnIndex("Field1"));//+":"+threedata;

		final CharSequence[] items = {one, two, three};

		win = false;

		final float max = Math.max(threedata, Math.max(onedata, twodata));

		Log.e("game",String.valueOf(max));
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				context);
		builder.setTitle("Which Saves The most Energy?");

		builder.setPositiveButton("Submit",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				if (currentchoice==-1){
					EasyTracker.getTracker().sendEvent("game", "action", "nochoice", null);
					Toast.makeText(Game.context, "You Need to make a choice or skip no cheating", Toast.LENGTH_SHORT).show();

					Game.myGame(Game.context);

					
				}else if (currentchoice == max){

					EasyTracker.getTracker().sendEvent("game", "action", "win", null);


					Toast.makeText(Game.context, "You Win! Keep Playing :)", Toast.LENGTH_SHORT).show();
					Main.changemoodby((int) (100*Math.random()),context);
					Game.myGame(Game.context);

				}else{

					EasyTracker.getTracker().sendEvent("game", "action", "lose", null);


					Toast.makeText(Game.context, "You Lose You're Making Me :(! Please Try Again", Toast.LENGTH_SHORT).show();
					Main.changemoodby((int) (-50*Math.random()),context);
					SharedPreferences settings2 = PreferenceManager.getDefaultSharedPreferences(Game.context);

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

				Main.exitgame();

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
				Log.e("game",String.valueOf(item));
				if (item==0) {
				currentchoice = onedata;
				}else if (item==1) {
					currentchoice = twodata;
				}else if (item==2) {
					currentchoice = threedata;
				}
				Log.e("game",String.valueOf(currentchoice));

			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}

	protected static void hintDialog(Context context2, String one,
			float onedata, String two, float twodata, String three,
			float threedata) {

		final List<sortabletype> numbers = new ArrayList<sortabletype>();
		
		numbers.add(new sortabletype(onedata, one));
		numbers.add(new sortabletype(twodata, two));
		numbers.add(new sortabletype(threedata, three));
		
        Collections.sort(numbers);
		
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

		final CharSequence[] items = {1+". "+numbers.get(2).text, twoequal+". "+numbers.get(1).text, threequal+". "+numbers.get(0).text};

		AlertDialog.Builder builder = new AlertDialog.Builder(
				context);
		builder.setTitle("Can I suggest a bettter choice?");


		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
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