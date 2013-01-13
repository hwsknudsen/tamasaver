package com.hwsknudsen.tamasaver;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;


public class animations {
	public static void jump(){
		AnimationSet as = new AnimationSet(true);
		as.setFillEnabled(true);
		as.setInterpolator(new BounceInterpolator());

		TranslateAnimation ta = new TranslateAnimation(-300, 100, 0, 0); 
		ta.setDuration(2000);
		as.addAnimation(ta);

		TranslateAnimation ta2 = new TranslateAnimation(100, 0, 0, 0); 
		ta2.setDuration(2000);
		ta2.setStartOffset(2000); // allowing 2000 milliseconds for ta to finish
		as.addAnimation(ta2);
		Main.goingto.add(new Actions(as,null));
	}

	public static void do360() {
		AnimationSet as = new AnimationSet(true);
		//RotateAnimation rotate = new RotateAnimation(0.0f, 360.0f,
        //        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
        //        0.5f);
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.ABSOLUTE, getLastLocation().x-(Main.iv.getWidth()/2), Animation.ABSOLUTE, getLastLocation().y-(Main.iv.getHeight()/2));
		rotate.setDuration(500);
		as.addAnimation(rotate);
		PointF last = getLastLocation();
		Main.goingto.add(new Actions(as,last));
	}

	//	public static void rotateto(float rotateby) {
	//		RotateAnimation rotate = new RotateAnimation(getLastRotation(), rotateby);
	//		rotate.setDuration(500);
	////		int x = 0;
	////		while (x < Math.abs(rotateby)) 
	////		{
	////			if (rotateby>=0){
	////				Main.goingto.add(new Actions(-x+getLastRotation(),120));
	////			}else{
	////				Main.goingto.add(new Actions(x+getLastRotation(),120));
	////			}
	////			x++;
	////		}
	//		Main.goingto.add(new Actions(rotateby+getLastRotation(),0));
	//		//Main.goingto.add(new Actions(rotate));
	//	}


	public static void GoTO(PointF point){		
		AnimationSet as = new AnimationSet(true);
		PointF last = getLastLocation();
		float x = point.x-last.x;
		float y = point.y-last.y;
		TranslateAnimation gotoxy = new TranslateAnimation(-x,0,-y,0);
		gotoxy.setDuration(500);
		as.addAnimation(gotoxy);
		Main.goingto.add(new Actions(as,point));
	}
	
	public static void Wink() {
		Main.goingto.add(new Actions(R.drawable.wink1,false));
		Wait(500);
		Main.goingto.add(new Actions(R.drawable.wink2,false));
		Wait(500);
		Main.goingto.add(new Actions(R.drawable.wink3,false));
		Wait(500);
		Main.goingto.add(new Actions(Main.currentFace,true));

	}
	
	public static void changeFacePermenant(int face) {
		Main.goingto.add(new Actions(face,true));
	}

	public static void Wait(long time) {
		AnimationSet as = new AnimationSet(true);
		PointF last = getLastLocation();
		TranslateAnimation gotoxy = new TranslateAnimation(0,0,0,0);
		gotoxy.setDuration(time);
		as.addAnimation(gotoxy);
		Main.goingto.add(new Actions(as,last));

	}

	private static PointF getLastLocation() {
		PointF last = new PointF(Main.iv.getX(),Main.iv.getY());
		for (Actions action : Main.goingto){
			if (action.point!=null){
				last=action.point;
			}
		}
		return last;
	}

	private static float getLastRotation() {
		float last = Main.iv.getRotation();
		for (Actions action : Main.goingto){
			if (action.point!=null){
				last=action.rotation;
			}
		}
		return last;
	}




}
