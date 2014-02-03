package jmm.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Test {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		long SysTimeS = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new FileReader(new File(args[0])));
		
		int count = 0;
		while (reader.readLine() != null) {
			count++;
		}
		
		long SysTimeE = System.currentTimeMillis();
		System.out.println((SysTimeE - SysTimeS) + "\n" + count);
	}
}
