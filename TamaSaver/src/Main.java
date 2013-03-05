

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
//import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.analytics.tracking.android.EasyTracker;
import com.hwsknudsen.tamasaver.R;
import com.hwsknudsen.tamasaver.R.drawable;
import com.hwsknudsen.tamasaver.R.id;
import com.hwsknudsen.tamasaver.R.layout;
import com.hwsknudsen.tamasaver.R.menu;
import com.hwsknudsen.tamasaver.R.raw;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class Main extends Activity{

	Game data = new Game(this);
	public SharedPreferences settings;
	static SQLiteDatabase myDB;

	public static ImageView iv;

	public static ArrayList<Actions> goingto = new ArrayList<Actions>();
	public static int currentFace;
	public static int currentmood;


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
	public void onSaveInstanceState(Bundle savedInstanceState) {
	  super.onSaveInstanceState(savedInstanceState);

		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("mood", currentmood);

		// Commit the edits!
		editor.commit();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		settings = PreferenceManager.getDefaultSharedPreferences(this);

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

					String text = aNode.getChildNodes().item(1).getTextContent();
					String value = aNode.getChildNodes().item(3).getTextContent();

					insertValues.put("Field1",text);
					insertValues.put("Field2",Float.parseFloat(value));

					myDB.insert(ActionLIST, null, insertValues);


				} 

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

		boolean remind = settings.getBoolean("reminder", true);
		if (remind){
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
//		case R.id.menu_awards:
//			Intent intentaward = new Intent(Main.this,Awards.class);
//			startActivity(intentaward);	
//			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void bCLight(View v){
		animations.Light();
		changemoodby(50,data.context);

		update();

		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "light", null);

	}

	public void bCHeat(View v){
		animations.Heat();
		changemoodby(50,data.context);

		update();

		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "heat", null);

	}

	public void bCElectronic(View v){

		animations.Electornic();

		changemoodby(50,data.context);
		update();
		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "electronics", null);

	}

	public void bCMotd(View v){
		Game.motdrandom(data.context);
		changemoodby(50,data.context);
		update();
		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "motd", null);

	}

	public void bCAppliance(View v){
		animations.Appliance();
		changemoodby(50,data.context);
		update();
		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "appliance", null);

	}

	public void bCWater(View v){

		animations.Water();
		changemoodby(50,data.context);

		update();

		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "water", null);

	}

	public void bCWalking(View v){
		animations.Walk();
		changemoodby(50,data.context);

		update();

		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "walking", null);

	}

	public void bCGame(View v){
		Game.myGame(data.context);

		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "game", null);

	}

	public void bC1(View v){
		changemoodby(50,data.context);
		animations.jump();
		animations.Wink();
		if (currentmood>=600){
			animations.do360();
		}
		update();
	}

	public void bC2(View v){
		changemoodby(-50,data.context);
		animations.Wink();
		if (currentmood<=500){
			animations.Wink();
		}
		update();
	}


	static void update() {

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

	private static void changebasefaceduetomood() {

		Log.e("moodupdate", String.valueOf(currentmood));


		if(currentmood>=1000){
			animations.changeFacePermenant(R.drawable.gold);
		}else if(currentmood>800){
			animations.changeFacePermenant(R.drawable.ecowarrior);
		}else if(currentmood>700){
			animations.changeFacePermenant(R.drawable.ecostar);
		}else if(currentmood>450){
			animations.changeFacePermenant(R.drawable.path3890);
		}else if(currentmood>300){
			animations.changeFacePermenant(R.drawable.sad);
		}else if(currentmood>200){
			animations.changeFacePermenant(R.drawable.sick);
		}else if(currentmood<=0){
			// issue award rip tama
			animations.changeFacePermenant(R.drawable.rip);
			// issue award golden tama
		}
		update();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//Log.e("hi","hi");

		animations.GoTO(new PointF(event.getX()-(iv.getWidth()/2),event.getY()-(iv.getHeight()/2)));
		update();

		return true;
	}



	public static void changemoodby(int d, Context context) {


		if (currentmood<1000 && currentmood>0){
			currentmood = currentmood + d;
			changebasefaceduetomood();
		} else if(currentmood>=1000){
			new AlertDialog.Builder(context)
		    .setTitle("Gretness !")
		    .setMessage("You have achieved energy saving enlightenment? Would you like to start again?")
		    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	currentmood=500;
					changebasefaceduetomood();

		        	}
		     })
		    .setNegativeButton("No", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // do nothing
		        }
		     })
		     .show();
		} else if(currentmood<=0){
			new AlertDialog.Builder(context)
		    .setTitle("Sad News !")
		    .setMessage("You need more time to achieve greatness in energy saving? Would you like to start again?")
		    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	currentmood=500;
					changebasefaceduetomood();
		        }
		     })
		    .setNegativeButton("No", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // do nothing
		        }
		     })
		     .show();
		}
		EasyTracker.getTracker().sendEvent("backgroundaction", "moodchange", "newmood", (long) currentmood);
		Log.e("moodupdate", String.valueOf(currentmood));

	}

	public static void exitgame() {
		// TODO Auto-generated method stub		
		Log.e("hi","hi");

		animations.Wink();
		animations.jump();
		update();
	}








}

