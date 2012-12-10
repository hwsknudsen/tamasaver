package com.hwsknudsen.tamasaver;

import android.graphics.PointF;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;

public class Actions{
	PointF point;
	float rotation;
	int typeflag;
	int character;
//	long time;
	//AlphaAnimation an;
	AnimationSet as;

	RotateAnimation ra;
	//ScaleAnimation sa;
	//TranslateAnimation ta;
	//AnimatorSet obA;
//	float Rotation;
	boolean permeant;
	
//	public Actions(float Rotation, long t){
//		this.Rotation = Rotation;
//		this.time = t;
//		this.typeflag = 4;
//	}
//	
//	public Actions(PointF pointF){
//		this.point = pointF;
//		this.typeflag = 2;
//	}
	
//	
	
	public Actions(int drawable,boolean permeant){
		this.character = drawable;
		this.permeant = permeant;
		this.typeflag = 1;
	}

	
//	public Actions(AlphaAnimation v){
//		this.an = v;
//		//this.time = v.getDuration();
//		this.typeflag = 0;
//
//	}	
//	public Actions(AnimationSet v){
//		this.as = v;
//		//this.time = v.getDuration();
//		this.typeflag = 0;
//
//	}
	public Actions(RotateAnimation v){
		this.ra = v;
		//this.time = v.getDuration();
		this.typeflag = 2;
	}
//	}
//	public Actions(ScaleAnimation v){
//		this.sa = v;
//		//this.time = v.getDuration();
//		this.typeflag = 0;
//
//	}
//	public Actions(TranslateAnimation v){
//		this.ta = v;
//		//this.time = v.getDuration();
//		this.typeflag = 0;
//
//	}

	public Actions(AnimationSet objectAnimator, PointF point) {
		this.as = objectAnimator;
		this.point = point;
		//this.time = objectAnimator.getDuration();
		this.typeflag = 0;
		}
	
	//AlphaAnimation, AnimationSet, RotateAnimation, ScaleAnimation, TranslateAnimation 

}
