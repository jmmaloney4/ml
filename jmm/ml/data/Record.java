package jmm.ml.data;

import jmm.ml.arff.ArffFileReader;

public class Record {

	private int StartIndex;
	private int EndIndex;
	private Data BigArray;

	public Record(int start, int end, Data BigArrayObj) {
		BigArray = BigArrayObj;
		StartIndex = start;
		EndIndex = end;
	}
	
	private float get(int index) {
		if ((StartIndex + index) > EndIndex) {
			return BigArray.get(StartIndex + index);
		}
		else {
			return Float.MAX_VALUE;
		}
	}

}
