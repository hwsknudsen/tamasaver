package com.hwsknudsen.tamasaver;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

public class Game {
	public Context context;
	public boolean firstrun;

	public Game(Context context) {
		this.context = context;
	}

	public static void myGame(Context context) {
		final CharSequence[] items = {"Walking", "Turning Down The Heating", "Turning Off a Light"};

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


}