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

import jmm.ui.RandomFileWindowManager;

public abstract class RandomFileGen {

	private static ArrayList<Attribute> attrs = new ArrayList<Attribute>(10);
	static Random rGen = new Random();
	public static final int NUMERIC_TYPE = 0;
	public static final int NOMINAL_TYPE = 1;
	public static int fileSize = (rGen.nextInt(1000000) + 100000);
	public static RandomFileWindowManager WManager;
	static Double SysTimeStart;
	static Double SysTimeEnd;
	static Writer writer;
	static String w;
	public static final String USAGE_STATEMENT = "Usage: ArffFileGen <File Name> <Number Of Records (Optional)> <Num Of Attributes (Optional)>";
	public static int numAttr = (rGen.nextInt(20) + 15);

	public static class Attribute {
		int Type;
		int Nom;
		int t = rGen.nextInt(3);
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

		public int GetT() {
			return t;
		}
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		SysTimeStart = (double)(System.currentTimeMillis());
		if (args.length > 3) {
			System.out.println(USAGE_STATEMENT);
		}
		else if (args.length == 0) {
			WManager = new RandomFileWindowManager((fileSize));
			JLabel label = WManager.getLabel();
			writer = new BufferedWriter(new PrintWriter(System.out));
			label.setText("Detected File, Writing To Standard Output");
			Thread.sleep(300);
			w = "Standard Output";
		}
		else  if (args.length == 1) {
			WManager = new RandomFileWindowManager((fileSize));
			JLabel label = WManager.getLabel();
			File file = new File(args[0]);
			writer = new BufferedWriter(new PrintWriter(file));
			label.setText("Detected File, Writing To " + file.getAbsolutePath());
			Thread.sleep(300);
			w = "File, " + file.getAbsolutePath();

		}
		else if (args.length == 2) {
			File file = new File(args[0]);
			writer = new BufferedWriter(new PrintWriter(file));
			fileSize = Integer.parseInt(args[1]);
			w = "File, " + file.getAbsolutePath();
			WManager = new RandomFileWindowManager((fileSize));
		}
		else if (args.length == 3) {
			File file = new File(args[0]);
			writer = new BufferedWriter(new PrintWriter(file));
			fileSize = Integer.parseInt(args[1]);
			numAttr = Integer.parseInt(args[2]);
			w = "File, " + file.getAbsolutePath();
			WManager = new RandomFileWindowManager((fileSize));
		}


		if (fileSize > 10000) {
			WManager.CreateWindow();
			Integer i = 0;
			JLabel label = WManager.getLabel();
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
			Thread.sleep(500);
			writer.write("\n\n@DATA\n");
			for (Integer o = 0; o < fileSize; o++) {
				String h = RandomFileGen.genRow();
				label.setText("Writing Record Number " + o.toString() + ", At: " + ((Float)(((float)o / (float)fileSize) * 100)).toString() + " %");
				writer.write(h);
				WManager.pBar.setValue((int) o);
			}
			SysTimeEnd = (double)System.currentTimeMillis();
			label.setText("Flushing Writer");
			Thread.sleep(500);
			label.setText("Writing To " + w);
			Thread.sleep(500);
			Thread.sleep(500);
			Double u = (SysTimeEnd - SysTimeStart) / 1000;
			label.setText(u.toString());
			Thread.sleep(1000);
			System.exit(0);
		}
		else {
			System.err.println("Error: FileSize Not Large Enough");
		}

	}

	public static String genRow() {
		String row = "";
		int t = rGen.nextInt(3);
		for (int w = 1; w < (attrs.size()); w++) {
			Attribute s = attrs.get(w);
			if (s.GetType() == NUMERIC_TYPE) {
				if (s.GetT() == 0) {
					Float l = (float) ((float)(rGen.nextInt(100)) / 2.0);
					l = l + 10;
					row = row + (l.toString() + ", ");
				}
				else if (s.GetT() == 1) {
					Float l = (float)(rGen.nextInt(50));
					row = row + (l.toString() + ", ");
				}
				else {
					Float l = ((float)(rGen.nextInt(50000000)) / 1000000);
					row = row + (l.toString() + ", ");
				}
			}
			else if (attrs.get(w).GetType() == NOMINAL_TYPE) {
				Attribute a = attrs.get(w);
				Integer p = (rGen.nextInt((a.getNom() - 1)) + 1);
				row = (row + p.toString() + ", ");
			}
		}
		row = row + "\n";
		return row;
	}
}

