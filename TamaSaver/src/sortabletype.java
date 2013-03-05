

public class sortabletype implements Comparable<sortabletype>{
	String text;
	Float value;
	public sortabletype(String text, Float value) {
		this.text = text;
		this.value =  value;
	}
	@Override
	public int compareTo(sortabletype another) {
		return (int) (value-another.value);
	}

}
