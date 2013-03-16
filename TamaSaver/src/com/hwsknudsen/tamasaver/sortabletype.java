package com.hwsknudsen.tamasaver;

public class sortabletype implements Comparable<sortabletype>{
	String text;
	Float value;
	public sortabletype(Float value, String text) {
		this.text = text;
		this.value =  value;
	}
	@Override
	public int compareTo(sortabletype o) {
		if (value> o.value) {
			return 1;
		}else if (value<o.value) {
			return -1;
		}
		else {
			return 0;
		}


	}
}

