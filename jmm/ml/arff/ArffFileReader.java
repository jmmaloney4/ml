/**
 * 
 */
package jmm.ml.arff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.Scanner;
import java.util.regex.Pattern;

import jmm.ml.data.Attribute;
import jmm.ml.data.RecordDataSet;

/**
 * A reader for parsing text streams in the Attribute-Relation File Format (ARFF).
 * 
 * See http://www.cs.waikato.ac.nz/ml/weka/arff.html.
 * 
 * @author John Maloney
 *
 */
public class ArffFileReader {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		if (args.length < 1) {
			System.out.println("Usage: java "+ ArffFileReader.class.getName() + " <filename>");
			System.exit(0);
		}
		
		ArffFileReader reader = new ArffFileReader(args[0]);
		reader.preprocess();
	}
	
	// constants
	private final static Pattern COMMENT_PATTERN = Pattern.compile("%.*[\r\n|[\r\n]]");
	private final static Pattern EOL_PATTERN = Pattern.compile(".*[\r\n|[\r\n]]");
	private final static Pattern DECLARATION_PATTERN = Pattern.compile("@.*");
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
	 * Pre-process the file to gather metadata.
	 */
	public void preprocess() {
		// skip any comments at the start of the file
		skipComments();
		
		// list variable to hold attribute information
		java.util.List<Attribute> attributes = new java.util.LinkedList<Attribute>();
		
		// string to hold the data set name
		String dsName = null;
		
		// parse declarations
		String tag, attrName;
		java.util.List<String> literals; 
		while (scanner.hasNext(DECLARATION_PATTERN)) {
			tag = scanner.next();
			System.out.println(tag);
			if (tag.equals(RELATION_TAG)) {
				dsName = scanner.next();
				System.out.println("Relation name: " + dsName);
			}
			else if (tag.equals(ATTRIBUTE_TAG)) {
				// get attribute name
				attrName = scanner.next();
				System.out.println("Attribute name: " + attrName);
				
				// create an attribute of the appropriate type
				if (scanner.hasNext(NUMERIC_TYPE_PATTERN)) {
					System.out.println("Attribute type: " + scanner.next());
					
					// add a ratio attribute to the list of attributes
					attributes.add(new jmm.ml.data.RatioAttribute(attrName));
				}
				else if (scanner.hasNext(NOMINAL_TYPE_PATTERN)) {
					String nomSpec = scanner.next();
					skipRestOfLine();
					nomSpec = nomSpec + scanner.match().group();
					System.out.println("Attribute type: NOMINAL " + nomSpec);
					
					// use nomScanner to parse nominal specification
					Scanner nomScanner = new Scanner(nomSpec);
					literals = new java.util.LinkedList<String>();
					nomScanner.useDelimiter(NOM_SPEC_DELIMITER_PATTERN);
					while (nomScanner.hasNext()) {
						literals.add(nomScanner.next());
					}
					
					// add a nominal attribute to the list of attributes
					attributes.add(new jmm.ml.data.NominalAttribute(attrName, literals));
				}
				//else if (scanner.hasNext(STRING_TYPE_PATTERN)) {
				//	System.out.println("Attribute type: " + scanner.next());										
				//	attributes.add(new jmm.ml.data.StringAttribute(attrName));
				//}
				//else if (scanner.hasNext(DATE_TYPE_PATTERN)) {
				//	System.out.println("Attribute type: " + scanner.next());										
				//}
				else {
					// ERROR
				}
			}
			else if (tag.equals(DATA_TAG)) {
				
			}
			else {
				
			}
			scanner.skip(EOL_PATTERN);
			skipComments();
		}
		
		// create data set
		dataset = new jmm.ml.data.DefaultRecordDataSet(dsName, attributes);
			
		// count data lines
		int numSamples = 0;
		while (scanner.hasNext()) {
			System.out.println(scanner.next());
			numSamples++;
		}
		
		System.out.println("Num attributes: " + dataset.getAttributeCount());
		System.out.println("Num samples:    " + numSamples);
		System.out.println(dataset);
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
	
	/**
	 * Skip any characters that remain on the current line.
	 */
	private void skipRestOfLine() {
		scanner.skip(EOL_PATTERN);		
	}

}
