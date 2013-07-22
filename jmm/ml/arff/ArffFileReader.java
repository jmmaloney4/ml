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
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import jmm.ml.data.Attribute;
import jmm.ml.data.Data;
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
	private final static Pattern DECLARATION_PATTERN = Pattern.compile("@.*");
	private final static Pattern COMMA_PATTERN = Pattern.compile("\\s*,\\s*");
	public final static String RELATION_TAG = "@RELATION";
	public final static String ATTRIBUTE_TAG = "@ATTRIBUTE";
	public final static String DATA_TAG = "@DATA";
	public final static String NUMERIC_TYPE = "NUMERIC";
	public final static String NOMINAL_TYPE = "\\{.*\\}";
	public final static String STRING_TYPE = "STRING";
	public final static String DATE_TYPE = "DATE";
	//public final static Pattern ATTR_TYPE_PATTERN = Pattern.compile(NUMERIC_TYPE + "|" + NOMINAL_TYPE + "|" + STRING_TYPE + "|" + DATE_TYPE);
	private final static Pattern NUMERIC_TYPE_PATTERN = Pattern.compile(NUMERIC_TYPE);
	//private final static Pattern STRING_TYPE_PATTERN = Pattern.compile(STRING_TYPE);
	private final static Pattern NOMINAL_TYPE_PATTERN = Pattern.compile(NOMINAL_TYPE);
	private final static Pattern NOM_SPEC_DELIMITER_PATTERN = Pattern.compile("\\{\\s*|\\s*,\\s*|\\s*\\}\\s*");
	private static ArffReaderWindowManager WManager;
	private int c;
	private static Data dataset;


	// private variables
	private Scanner scanner;

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
		this(new java.io.BufferedReader(new java.io.FileReader(file)));
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

		// count the records in the data set
		c = countRecords();
		WManager = new ArffReaderWindowManager(c); 

		//start the UI
		WManager.CreateWindow();

		// list variable to hold attribute information
		int attributes = 0;

		// string to hold the data set name
		String dsName = null;

		// temporary storage for declaration tags and attribute names
		String tag, attrName;

		// temporary storage for literals for nominal attributes
		List<String> literals;

		// temporary storage to hold nominal specification to be parsed
		String nomSpec;

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

			// TODO: replace this with debug/logging
			System.out.println(tag);

			/*
			 * @RELATION
			 */
			if (tag.equals(RELATION_TAG)) {
				// get the relation name
				// TODO: modify this to handle names that contain whitespace
				dsName = scanner.next();
				// TODO: replace this with debug/logging
				System.out.println("Relation name: " + dsName);
			}
			/*
			 * @ATTRIBUTE
			 */
			else if (tag.equals(ATTRIBUTE_TAG)) {
				// get attribute name
				attrName = scanner.next();
				// TODO: replace this with debug/logging
				System.out.println("Attribute name: " + attrName);
				// increment the number of attributes
				attributes++;

				// NUMERIC attributes
				if (scanner.hasNext(NUMERIC_TYPE_PATTERN)) {
					// TODO: replace this with debug/logging
					System.out.println("Attribute type: " + scanner.next());

					// TODO: create a numeric attribute


				}
				// NOMINAL attributes
				else if ((nomSpec = scanner.findInLine(NOMINAL_TYPE_PATTERN)) != null) {
					// TODO: replace this with debug/logging
					System.out.println("Attribute type: NOMINAL " + nomSpec);
					literals = parseNominalLiterals(nomSpec);
					// TODO: create a nominal attribute
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
				WManager.setLabel("Reading Data...");
				dataset = new Data(c, attributes);
				// skip anything that is on the same line as the @DATA tag
				scanner.nextLine();

				// TODO: replace this with debug/logging 
				System.out.println("Parse " + c + " data records");

				scanner.useDelimiter(COMMA_PATTERN);
				int a = 1;
				while (scanner.hasNext()) {
					WManager.setLabel("Reading Arrtibute Number " + ((Integer)a).toString());
					float d = scanner.nextFloat();
					dataset.add(a, d);
					System.out.println(dataset.get(a));
					a++;
					WManager.pBar.setValue(a / attributes);
				}
				System.out.println(a + ", " + dataset.getLength());
			}
			else {
				// ERROR
			}

			skipComments();
		}

		//System.out.println("Num attributes: " + dataset.getAttributeCount());
		System.exit(0);

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

	public int countRecords() throws FileNotFoundException, IOException {
		BufferedReader BReader = new BufferedReader(new FileReader(file));
		int count = 0;
		while (BReader.ready()) {
			if (BReader.readLine().startsWith(DATA_TAG)) {
				while (BReader.readLine() != null) {
					count++;
				}
			}
		}
		System.out.println(count);
		return count;
	}

	/**
	 * Parse the specification for a nominal attribute.
	 */
	private List<String> parseNominalLiterals(String nomSpec) {
		List<String> literals = new java.util.LinkedList<String>();
		// use nomScanner to parse nominal specification
		Scanner nomScan = new Scanner(nomSpec);
		nomScan.useDelimiter(NOM_SPEC_DELIMITER_PATTERN);
		while (nomScan.hasNext()) {
			literals.add(nomScan.next());
		}
		nomScan.close();

		return literals;
	}
	
	public Data getBigArrayObj() {
		return dataset;
	}
}
