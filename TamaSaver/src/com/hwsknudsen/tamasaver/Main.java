package com.hwsknudsen.tamasaver;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Main extends Activity{


	public static final String PREFS_NAME = "MyPrefsFile";
	private boolean firstrun;
	public static ImageView iv;
	public static long animationtime = 100;
	private static int animationinprogress = 0 ;
	
	public static ArrayList<Actions> goingto = new ArrayList<Actions>();


	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if(animationinprogress == 0){
				update();
			}
		}

	};

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
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		Thread myThread=new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						handler.sendMessage(handler.obtainMessage());
						Thread.sleep(animationtime);
					}
					catch (Throwable t) {
					}
				}
			}
		});

		myThread.start();
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
		animations.GoTO(new PointF(100,100));
	}

	public void buttonClicked2(View v) {
		animations.GoTO(new PointF(200,200));
	}


	private void update() {
		
		if(!goingto.isEmpty()&animationinprogress==0){
			animationinprogress = 1;
			Actions thisanimation = goingto.get(0);
			goingto.remove(0);
			if (thisanimation.typeflag==0){
				getAnimation(thisanimation).setAnimationListener(new AnimationListener(){
					@Override
					public void onAnimationEnd(Animation arg0) {
						animationinprogress = 0;
						update();
					}
					@Override
					public void onAnimationRepeat(Animation arg0) {
					}
					@Override
					public void onAnimationStart(Animation arg0) {
					}
					
				});
				iv.startAnimation(getAnimation(thisanimation));
			}
			else if (thisanimation.typeflag==2){
				iv.setX(thisanimation.point.x);
				iv.setY(thisanimation.point.y);
				animationinprogress = 0;
				update();
			}
			else if (thisanimation.typeflag==3){
				//thisanimation.obA.start();
			}
		//animationtime = thisanimation.time;
		}
	}



	private Animation getAnimation(Actions thisanimation) {
		if(thisanimation.an!=null){
			return thisanimation.an;
		}
		else if(thisanimation.as!=null){
			return thisanimation.as;
		}
		else if(thisanimation.ra!=null){
			return thisanimation.ra;
		}
		else if(thisanimation.sa!=null){
			return thisanimation.sa;
		}
		else if(thisanimation.ta!=null){
			return thisanimation.ta;
		}
		return null;
	}
	


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//Log.e("hi","hi");
		animations.GoTO(new PointF(event.getX(),event.getY()));
	    return true;
	}
	
	






	//iv.setImageResource(R.drawable.path3891);
}


