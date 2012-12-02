package com.hwsknudsen.tamasaver;

import android.graphics.Point;

public class Actions{
	Point point;
	int character;
	long time;
	
	public Actions(Point point1, long t){
		this.point = point1;
		this.time = t;
	}
	
	public Actions(int drawable, long t){
		this.character = drawable;
		this.time = t;
	}
}
