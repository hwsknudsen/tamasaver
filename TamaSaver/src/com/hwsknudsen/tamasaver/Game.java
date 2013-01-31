package com.hwsknudsen.tamasaver;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class Game {
	public Context context;
	public boolean firstrun;

	public Game(Context context) {
		this.context = context;
	}

	public static void myGame(Context context) {
		
		Log.e("12", Main.dbname);
		//context.myDB
		
		//SQLiteDatabase myDB = openOrCreateDatabase(Main.dbname, Main.MODE_PRIVATE, null);
				
//		Cursor mythree = myDB.rawQuery("SELECT * FROM ActionLIST ORDER BY RANDOM() LIMIT 3", null);
//		
//		String one = mythree.getColumnName(0).toString();
//		mythree.moveToNext();
//		String two  = mythree.getColumnName(0).toString();
//		mythree.moveToNext();
//		String three =mythree.getColumnName(0).toString();
//
//		
		final CharSequence[] items = {"one", "two", "three"};

		AlertDialog.Builder builder = new AlertDialog.Builder(
				context);
		builder.setTitle("Wich Saves The most Energy");
		
		builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
		
		builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
		
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	//dialog.cancel();
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
		
//		// TODO Auto-generated method stub
//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//				context);
//
//		// set title
//		alertDialogBuilder.setTitle("GAME?");
//
//		final CharSequence[] items = {"Red", "Green", "Blue"};
//
//		// set dialog message
//		alertDialogBuilder
//		//.setMessage("Energy Usage IS BAD!")
//		
//		//.setMultiChoiceItems(null, null, null)
//		.setCancelable(false)
//		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog,int id) {
//				dialog.cancel();
//			}
//		})
//
//		;
//
//		// create alert dialog
//		AlertDialog alertDialog = alertDialogBuilder.create();
//
//		// show it
//		alertDialog.show();
	}

	public static void motdrandom(Context context) {
		
		
		Builder alertDialogBuilder = new Builder(
				context);
	
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


}