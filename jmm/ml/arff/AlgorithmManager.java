package jmm.ml.arff;

import java.io.IOException;

import jmm.ml.data.Data;

public class AlgorithmManager {

	public static Data BigArrayObj;
	static int attrs;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BigArrayObj = ArffFileReader.fileReader.load(); 
		attrs = ArffFileReader.fileReader.getNumAttrs();

	}

	private float[] getColumn(int c) {
		float[] ra = new float[BigArrayObj.getLength() / attrs];
		BigArrayObj.get(loc)
		return ra;
	}

}
