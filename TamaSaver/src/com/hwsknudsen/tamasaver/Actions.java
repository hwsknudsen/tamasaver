package com.hwsknudsen.tamasaver;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class Actions{
	PointF point;
	int typeflag;
	int character;
	long time;
	AlphaAnimation an;
	AnimationSet as;
	RotateAnimation ra;
	ScaleAnimation sa;
	TranslateAnimation ta;
	AnimatorSet obA;
	float Rotation;
	
	
	public Actions(float Rotation, long t){
		this.Rotation = Rotation;
		this.time = t;
		this.typeflag = 4;
	}
	
	public Actions(PointF pointF, long t){
		this.point = pointF;
		this.time = t;
		this.typeflag = 2;
	}
	
	public Actions(int drawable, long t){
		this.character = drawable;
		this.time = t;
		this.typeflag = 1;
	}

	
	public Actions(AlphaAnimation v){
		this.an = v;
		this.time = v.getDuration();
		this.typeflag = 0;

	}	
	public Actions(AnimationSet v){
		this.as = v;
		this.time = v.getDuration();
		this.typeflag = 0;

	}
	public Actions(RotateAnimation v){
		this.ra = v;
		this.time = v.getDuration();
		this.typeflag = 0;

	}
	public Actions(ScaleAnimation v){
		this.sa = v;
		this.time = v.getDuration();
		this.typeflag = 0;

	}
	public Actions(TranslateAnimation v){
		this.ta = v;
		this.time = v.getDuration();
		this.typeflag = 0;

	}

	public Actions(AnimatorSet objectAnimator) {
		this.obA = objectAnimator;
		this.time = objectAnimator.getDuration();
		this.typeflag = 3;
		}
	
	//AlphaAnimation, AnimationSet, RotateAnimation, ScaleAnimation, TranslateAnimation 

}
