package com.hwsknudsen.tamasaver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class myTimeAlarm extends BroadcastReceiver  {

	
	@Override
	public void onReceive(Context context, Intent arg1) {
//		   Toast.makeText(context, "Don't panik but your time is up!!!!.",
//			        Toast.LENGTH_LONG).show();
		showNotification(context);
	}

	private void showNotification(Context context) {
		
		Main.changemoodby(-100,context); // lower mood daily 
		
	    PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
	            new Intent(context, Main.class), 0);

	    NotificationCompat.Builder mBuilder =
	            new NotificationCompat.Builder(context)
	    		.setSmallIcon(R.drawable.happy)
	            .setContentTitle("TamaSaver")
	            .setContentText("Remember To Tell TamaSaver when you save energy!");
	    mBuilder.setContentIntent(contentIntent);
	    mBuilder.setDefaults(Notification.DEFAULT_SOUND);
	    mBuilder.setAutoCancel(true);
	    NotificationManager mNotificationManager =
	        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	    mNotificationManager.notify(1, mBuilder.build());

	}  

} 