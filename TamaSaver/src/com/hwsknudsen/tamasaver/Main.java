package com.hwsknudsen.tamasaver;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class Main extends Activity{


	public static final String PREFS_NAME = "MyPrefsFile";
	private boolean firstrun;
	public static ImageView iv;

	public static ArrayList<Actions> goingto = new ArrayList<Actions>();
	public static int currentFace;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		setContentView(R.layout.activity_home);

		firstrun = settings.getBoolean("firstRun", true);
		// If first run load settings 
		if(firstrun ){
			firstrun = false;
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
		animations.GoTO(new PointF(100,100));
		update();
	}

	public void buttonClicked2(View v) {
		animations.do360();
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








	//iv.setImageResource(R.drawable.path3891);
}


