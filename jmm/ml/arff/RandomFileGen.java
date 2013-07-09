package jmm.ml.arff;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;

import jmm.ui.WindowManager;

public abstract class RandomFileGen {

	private static ArrayList<Attribute> attrs = new ArrayList<Attribute>(10);
	static Random rGen = new Random();
	public static final int NUMERIC_TYPE = 0;
	public static final int NOMINAL_TYPE = 1;
	public static int fileSize = (rGen.nextInt(10000000) + 1000000);
	public static WindowManager WManager = new WindowManager((fileSize + 11380));
	public static JLabel label = WManager.getLabel();
	static Double SysTimeStart;
	static Double SysTimeEnd;
	static Writer writer;
	static String w;

	public static class Attribute {
		int Type;
		int Nom;
		public Attribute(int t) {
			Type = t;
		}

		public void setNom(int n) {
			Nom = n;
		}

		public int getNom() {
			return Nom;
		}

		public int GetType() {
			return Type;
		}
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		SysTimeStart = (double)(System.currentTimeMillis());
		WManager.CreateWindow();
		if (args.length == 0) {
			writer = new BufferedWriter(new PrintWriter(System.out));
			label.setText("Detected File, Writing To Standard Output");
			Thread.sleep(300);
			w = "Standard Output";
		}
		else {
			File file = new File(args[0]);
			writer = new BufferedWriter(new PrintWriter(file));
			label.setText("Detected File, Writing To " + file.getAbsolutePath());
			Thread.sleep(300);
			w = "File, " + file.getAbsolutePath();
		}
		int numAttr;
		numAttr = (rGen.nextInt(10) + 12);
		Integer i = 0;
		writer.write("@RELATION RandomFileGen \n\n\n");
		label.setText("Writing @RELATION tag...");
		for (int numAttr2 = numAttr; numAttr2 > 0; numAttr2--) {
			int AttrType = rGen.nextInt(2);
			i++;
			label.setText("Writing Attributes...");
			if (AttrType == 0) {
				writer.write("@ATTRIBUTE " + "Attribute" + (i.toString()) + " NUMERIC\n");
				attrs.add(new Attribute(0));
				label.setText("Wrote Numeric Attribute...");
			}
			else if (AttrType == 1) {
				int numNomAttrs = (rGen.nextInt(10) + 5);
				String str = " {1";
				for (int k = 1; k < numNomAttrs; k++) {
					Integer n = k + 1;
					str = str + (", " + n.toString());
				}
				writer.write("@ATTRIBUTE " + "Attribute" + (i.toString()) + str + "}\n");
				label.setText("Wrote Nominal Attribute...");
				Attribute a = new Attribute(1);
				a.setNom(numNomAttrs);
				attrs.add(a);
			}

		}
		WManager.addProgress(76);
		Thread.sleep(500);
		writer.write("\n\n@DATA\n");

		for (float o = 0; o < fileSize; o++) {
			String h = RandomFileGen.genRow();
			float z = (Float)o;
			label.setText("Adding Record Number " + ((Integer)(int)z).toString() + ", At: " + ((Float)(((float)o / (float)fileSize) * 100)).toString() + "%");
			writer.write(h);
			WManager.pBar.setValue((int) o);
		}
		label.setText("Flushing Writer");
		Thread.sleep(500);

		label.setText("Writing To " + w);
		Thread.sleep(500);
		WManager.addProgress(11300);
		Thread.sleep(500);


		SysTimeEnd = (double)System.currentTimeMillis();
		Double u = (SysTimeEnd - SysTimeStart) / 1000;
		label.setText(u.toString());
		Thread.sleep(1000);
		System.exit(0);

	}

	public static String genRow() {
		String row = "";
		for (int w = 1; w < (attrs.size()); w++) {
			if (attrs.get(w).GetType() == NUMERIC_TYPE) {
				Double l = ((float)(rGen.nextInt(100)) / 2.0);
				row = row + (l.toString() + ", ");
			}
			if (attrs.get(w).GetType() == NOMINAL_TYPE) {
				Attribute a = attrs.get(w);
				Integer p = (rGen.nextInt((a.getNom() - 1)) + 1);
				row = (row + p.toString() + ", ");
			}
		}
		row = row + "\n";
		return row;
	}
}

