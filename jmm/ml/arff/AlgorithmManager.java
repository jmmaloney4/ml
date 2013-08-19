package jmm.ml.arff;

import java.io.IOException;

import jmm.ml.data.Data;

public class AlgorithmManager {
	
	public static Data BigArrayObj;
	static int attrs;
	static float[] attribute;
	static float[] attributeRanges;
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BigArrayObj = ArffFileReader.fileReader.load(); 
		attrs = ArffFileReader.fileReader.getNumAttrs();
		attributeRanges = new float[attrs * 2];
		attribute = new float[BigArrayObj.getLength() / attrs];
		
		for (int k = 0; k <= (attribute.length); k++) {
			
		}
		
	}

}
