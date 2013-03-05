

import android.graphics.PointF;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;

public class Actions{
	PointF point;
	float rotation;
	int typeflag;
	int character;
	AnimationSet as;

	RotateAnimation ra;
	boolean permeant;
	
	public Actions(int drawable,boolean permeant){
		this.character = drawable;
		this.permeant = permeant;
		this.typeflag = 1;
	}
	public Actions(RotateAnimation v){
		this.ra = v;
		this.typeflag = 2;
	}

	public Actions(AnimationSet objectAnimator, PointF point) {
		this.as = objectAnimator;
		this.point = point;
		this.typeflag = 0;
		}
	

}
