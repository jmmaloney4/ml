package jmm.ml.data;

public class Data {
	
	protected float[] BigArray;
	int records;
	int attrs;
	
	public Data(int records, int attributes) {
		int size = records * attributes;
		this.records = records;
		attrs = attributes;
		BigArray = new float[size];
	}
	
	public void add(int loc, float f) {
		BigArray[loc] = f;
	}
	
	public float get(int loc) {
		return BigArray[loc];
	}
	
	public float get(int record, int attribute) {
		int loc = (attrs * (records - 1)) + attribute;
		return BigArray[loc];
	}
	
	public void add(int record, int attribute, float f) {
		int loc = (attrs * (records - 1)) + attribute;
		BigArray[loc] = f;
	}
	
	public int getLength() {
		return BigArray.length;
	}
	
}
