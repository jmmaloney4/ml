/**
 * 
 */
package jmm.ml.arff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;
import java.util.regex.Pattern;

import jmm.ml.data.Attribute;
import jmm.ml.data.LinkedList;
import jmm.ml.data.NumericLinkedList;
import jmm.ml.data.RecordDataSet;
import jmm.ui.ArffReaderWindowManager;

/**
 * A reader for parsing text streams in the Attribute-Relation File Format (ARFF).
 * 
 * See http://www.cs.waikato.ac.nz/ml/weka/arff.html.
 * 
 * @author John Maloney
 *
 */
public class ArffFileReader {

	public static File file;

	public static void main(String[] args) throws IOException {

		if (args.length < 1) {
			System.out.println("Usage: java "+ ArffFileReader.class.getName() + " <filename>");
			System.exit(0);
		}
		file = new File(args[0]);
		ArffFileReader reader = new ArffFileReader(args[0]);
		reader.load();
	}

	// constants
	private final static Pattern COMMENT_PATTERN = Pattern.compile("%.*[\r\n|[\r\n]]");
	private final static Pattern EOL_PATTERN = Pattern.compile(".*[\r\n|[\r\n]]");
	private final static Pattern DECLARATION_PATTERN = Pattern.compile("@.*");
	private final static Pattern COMMA_PATTERN = Pattern.compile("\\s*,\\s*");
	public final static String RELATION_TAG = "@RELATION";
	public final static String ATTRIBUTE_TAG = "@ATTRIBUTE";
	public final static String DATA_TAG = "@DATA";
	public final static String NUMERIC_TYPE = "NUMERIC";
	public final static String NOMINAL_TYPE = "\\{.*";
	public final static String STRING_TYPE = "STRING";
	public final static String DATE_TYPE = "DATE";
	//public final static Pattern ATTR_TYPE_PATTERN = Pattern.compile(NUMERIC_TYPE + "|" + NOMINAL_TYPE + "|" + STRING_TYPE + "|" + DATE_TYPE);
	private final static Pattern NUMERIC_TYPE_PATTERN = Pattern.compile(NUMERIC_TYPE);
	private final static Pattern STRING_TYPE_PATTERN = Pattern.compile(STRING_TYPE);
	private final static Pattern NOMINAL_TYPE_PATTERN = Pattern.compile(NOMINAL_TYPE);
	private final static Pattern NOM_SPEC_DELIMITER_PATTERN = Pattern.compile("\\{\\s*|\\s*,\\s*|\\s*\\}\\s*");
	private static ArffReaderWindowManager WManager = new ArffReaderWindowManager(); 
	public static float[] BigArray;
	private int c;
	public static BufferedReader Br;


	// private variables
	private Scanner scanner;
	private RecordDataSet dataset;

	/**
	 * Construct an ARFF file reader using the specified file path.
	 * 
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public ArffFileReader(String filename) throws FileNotFoundException {
		this(new File(filename));
	}

	/**
	 * Construct an ARFF file reader using the specified file.
	 * @param file
	 * @throws FileNotFoundException
	 */
	public ArffFileReader(File file) throws FileNotFoundException {
		this((Br = new java.io.BufferedReader(new java.io.FileReader(file))));


	}

	/**
	 * Construct an ARFF reader on the specified @link{javo.io.Reader}.
	 * @param reader
	 */
	public ArffFileReader(Reader reader) {
		scanner = new Scanner(reader);

	}

	/**
	 * Load the data set from the stream.
	 * @throws IOException 
	 */
	public void load() throws IOException {

		c = countRecords();
		System.out.println(c);

		//start the UI
		WManager.CreateWindow();

		// list variable to hold attribute information
		int attributes = 0;

		// string to hold the data set name
		String dsName = null;

		// temporary storage for declaration tags and attribute names
		String tag, attrName;

		// temporary storage for literals for nominal attributes
		java.util.List<String> literals;

		/*
		 *  Skip any comments at the start of the file
		 */
		skipComments();

		long time1 = System.currentTimeMillis();

		/*
		 * Parse the file and read the RELATION and ATTRIBUTE declarations
		 */
		while (scanner.hasNext(DECLARATION_PATTERN)) {

			// read the next declaration tag from the stream
			tag = scanner.next(DECLARATION_PATTERN);

			// TEMP: replace this with debug/logging
			System.out.println(tag);
			System.out.println("scanner.hasNext(): " + scanner.hasNext());

			/*
			 * @RELATION
			 */
			if (tag.equals(RELATION_TAG)) {
				// get the relation name
				dsName = scanner.next();
				// TEMP: replace this with debug/logging
				System.out.println("Relation name: " + dsName);
			}
			/*
			 * @ATTRIBUTE
			 */
			else if (tag.equals(ATTRIBUTE_TAG)) {
				// get attribute name
				attrName = scanner.next();
				// TEMP: replace this with debug/logging
				System.out.println("Attribute name: " + attrName);

				// NUMERIC attributes
				if (scanner.hasNext(NUMERIC_TYPE_PATTERN)) {
					// TEMP: replace this with debug/logging
					System.out.println("Attribute type: " + scanner.next());

					//linked list
					attributes++;

				}
				// NOMINAL attributes
				else if (scanner.hasNext(NOMINAL_TYPE_PATTERN)) {
					// read the nominal specification from the stream
					scanner.reset();
					String nomSpec = scanner.next();
					skipRestOfLine();
					System.out.println(scanner.match().groupCount());
					nomSpec = nomSpec + scanner.match().group();

					// TEMP: replace this with debug/logging
					System.out.println("Attribute type: NOMINAL " + nomSpec);

					// use nomScanner to parse nominal specification
					Scanner nomScan = new Scanner(nomSpec);
					literals = new java.util.LinkedList<String>();
					nomScan.useDelimiter(NOM_SPEC_DELIMITER_PATTERN);
					while (nomScan.hasNext()) {
						literals.add(nomScan.next());
					}
					attributes++;

				}
				// STRING attributes - currently unsupported
				//else if (scanner.hasNext(STRING_TYPE_PATTERN)) {
				//	System.out.println("Attribute type: " + scanner.next());										
				//	attributes.add(new jmm.ml.data.StringAttribute(attrName));
				//}
				// DATE attributes - currently unsupported
				//else if (scanner.hasNext(DATE_TYPE_PATTERN)) {
				//	System.out.println("Attribute type: " + scanner.next());										
				//}
				else {
					// ERROR
					System.out.println("Please Make Sure That Your File Is Formatted Appropriately");
				}
			}
			/*
			 * @DATA
			 */
			else if (tag.equals(DATA_TAG)) { 
				// skip anything that comes after the @DATA tag
				skipRestOfLine();

				BigArray = new float[(c * attributes)];

				System.out.println("Reading Data");
				scanner.useDelimiter(COMMA_PATTERN);
				int w = 0;
				while (scanner.hasNext()) {
					BigArray[w] = (scanner.nextFloat());
					System.out.println("Token: " + BigArray[w]);
					System.out.println(BigArray[w]);
					w++;
				}



			}
			else {
				// ERROR
			}

			//scanner.skip(EOL_PATTERN);
			//skipComments();
		}


		/*
		 * Read the records from the file and add them to the data set
		 */
		//int numSamples = 0;
		//while (scanner.hasNext()) {
		//System.out.println(scanner.next());
		//scanner.next();
		//numSamples++;
		//}

		//System.out.println("Num attributes: " + dataset.getAttributeCount());
		//System.out.println("Num samples:    " + numSamples);
		//System.out.println(dataset);
		System.exit(0);


		long time2 = System.currentTimeMillis();

		System.out.println("Total time: " + (time2 - time1)/((float)1000) + " seconds");

	}

	/**
	 * Skip any comments that appear next in the input stream.
	 */
	private void skipComments() {
		int c = 0; 
		try {
			while (true) {
				scanner.skip(COMMENT_PATTERN);
				c++;
				java.util.regex.MatchResult result = scanner.match();
				System.out.print("COMMENT: " + result.group());
			}
		}
		catch (java.util.NoSuchElementException ex) {
			// done skipping comments
			System.err.println("Skipped " + c + " comments");
		}
	}

	private int countRecords() throws IOException {
		int count = 0;
		BufferedReader BReader = new BufferedReader(new FileReader(file));
		while (BReader.ready()) {
			if ((BReader.readLine()).startsWith("@DATA")) {
				while (BReader.readLine() != null) {
					count++;
				}
				System.out.println(count);
				BReader.close();
				return count;
			}
		}
		BReader.close();
		return count;
	}

	/**
	 * Skip any characters that remain on the current line.
	 */
	private void skipRestOfLine() {
		scanner.skip(EOL_PATTERN);		
	}

}
