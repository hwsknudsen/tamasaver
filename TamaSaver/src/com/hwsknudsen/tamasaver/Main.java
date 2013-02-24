package com.hwsknudsen.tamasaver;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;



import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class Main extends Activity{

	public static final String PREFS_NAME = "main_prefs";
	public static final String CONFIG_Prefs = "config_prefs";

	public static final String Awards_pref = "award_prefs";

	Game data = new Game(this);
	private String ActionLIST;
	public SharedPreferences settings;
	static SQLiteDatabase myDB;

	public static ImageView iv;

	public static ArrayList<Actions> goingto = new ArrayList<Actions>();
	public static int currentFace;
	public static int currentmood;


	public static SharedPreferences settings2;
	//public static String dbname;
	//public static SharedPreferences userconfig;
	public static String dbname;

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this); // Add this method.
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this); // Add this method.
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		settings = getSharedPreferences(PREFS_NAME, 0);
		//settings2 = getSharedPreferences(CONFIG_Prefs, 0);

		//userconfig = getSharedPreferences(Main.CONFIG_Prefs, 0);

		setContentView(R.layout.activity_home);

		data.firstrun = settings.getBoolean("firstRun", true);
		this.dbname = settings.getString("dbname", "mydb");

		currentmood = settings.getInt("mood", 500);


		// If first run load settings 


		String ActionLIST = "ActionLIST";
		myDB = this.openOrCreateDatabase(dbname, MODE_PRIVATE, null);


		if(data.firstrun ){

			/* Create a Table in the Database. */
			myDB.execSQL("CREATE TABLE IF NOT EXISTS "
					+ ActionLIST 
					+ " (Field1 TEXT, Field2 FLOAT(10));");


			InputStream is = getResources().openRawResource(R.raw.thecarbondata);
			try {

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = factory.newDocumentBuilder();
				Document dom = db.parse(is); 
				NodeList mynodes = dom.getElementsByTagName("Row");

				ContentValues insertValues = new ContentValues();

				for (int i = 0; i < mynodes.getLength(); i++) { 
					Node aNode = mynodes.item(i); 
					Element element = (Element) aNode;

					String text = aNode.getChildNodes().item(1).getTextContent();
					String value = aNode.getChildNodes().item(3).getTextContent();

					insertValues.put("Field1",text);
					insertValues.put("Field2",Float.parseFloat(value));




					myDB.insert(ActionLIST, null, insertValues);


				} 

				//myDB.close();

				//ContentValues insertValues = populatedbfrom(is);

			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			Intent intent = new Intent(Main.this,Settings.class);
			startActivity(intent);	


		}else{
			Game.motdrandom(data.context);

		}
		iv = (ImageView) findViewById(R.id.imageView1);

		currentFace = R.drawable.path3890;
		iv.setImageResource(currentFace);


		Calendar dateCal = Calendar.getInstance();
		// make it now
		//dateCal.setTime(new Date());
		// make it tomorrow
		dateCal.add(Calendar.DAY_OF_YEAR, 1);
		// Now set it to the time you want
		dateCal.set(Calendar.HOUR_OF_DAY, 10);
		dateCal.set(Calendar.MINUTE, 0);
		dateCal.set(Calendar.SECOND, 0);
		dateCal.set(Calendar.MILLISECOND, 0);

		
		String alarm = Context.ALARM_SERVICE;
		AlarmManager am = ( AlarmManager ) getSystemService( alarm );
		 
		Intent intent = new Intent( "REFRESH_THIS" );
		PendingIntent pi = PendingIntent.getBroadcast( this, 0, intent, 0 );
		 
		int type = AlarmManager.RTC_WAKEUP;

		am.set(type, dateCal.getTimeInMillis(), pi);
		

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
		case R.id.menu_awards:
			Intent intentaward = new Intent(Main.this,Awards.class);
			startActivity(intentaward);	
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	//	public void buttonClicked(View v) {
	//
	//		//		animations.GoTO(new PointF(100,100));
	//		//		update();
	//		//motdrandom();
	//		//startGame();
	//		Game.myGame(data.context);
	//	}
	//
	//
	//
	//	public void buttonClicked2(View v) {
	//		animations.do360();
	//		//animations.GoTO(new PointF(200,200));
	//		update();
	//
	//	}


	public void bCLight(View v){
		animations.do360();
		//animations.GoTO(new PointF(200,200));
		update();

		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "light", null);

	}

	public void bCHeat(View v){
		animations.Heat();
		update();

		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "heat", null);

	}

	public void bCElectronic(View v){
//
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.SECOND, 5);


	
		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "electronics", null);

	}

	public void bCMotd(View v){
		Game.motdrandom(data.context);

		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "motd", null);

	}

	public void bCAppliance(View v){

		Intent intent = new Intent(this, Main.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
		@SuppressWarnings("deprecation")
		Notification noti = new Notification.Builder(this)
		.setContentTitle("New mail from ")
		.setContentText("HI")
		.setSmallIcon(R.drawable.happy)
		.setContentIntent(pIntent)
		.getNotification();

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Hide the notification after its selected
		noti.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, noti);

		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "appliance", null);

	}

	public void bCWater(View v){

		animations.Water();
		update();

		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "water", null);

	}

	public void bCWalking(View v){
		//Game.motdrandom(data.context);

		animations.Walk();
		//animations.GoTO(new PointF(200,200));
		update();

		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "walking", null);

	}

	public void bCGame(View v){
		Game.myGame(data.context);

		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "game", null);

	}

	public void bC1(View v){
		changemoodby(50);
		animations.jump();
		animations.Wink();
		if (currentmood>=600){
			animations.do360();
		}
		//animations.GoTO(new PointF(200,200));
		update();
	}

	public void bC2(View v){
		changemoodby(-50);
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


		changebasefaceduetomood();


	}

	private void changebasefaceduetomood() {
		int moodrounder = currentmood/10;
		if(currentmood<=0){
			// issue award rip tama
			animations.changeFacePermenant(R.drawable.rip);
		}else if(moodrounder>0){

		}else if(moodrounder>1){

		}else if(moodrounder>2){

		}else if(moodrounder>3){

		}else if(moodrounder>5){

		}else if(moodrounder>6){

		}else if(moodrounder>7){

		}else if(moodrounder>8){

		}else if(moodrounder>9){

		}else if(moodrounder>10){
			// issue award golden tama

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



	public static void changemoodby(int d) {
		// TODO Auto-generated method stub
		currentmood = currentmood + d;
		//Awards mai = Awards.getInstance();

		//Awards.scoreUpdateAward(currentmood);

		Log.e("moodupdate", String.valueOf(currentmood));

	}






}

