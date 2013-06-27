/**
 * 
 */
package jmm.ml.data;

import java.util.List;

/**
 * A default implementation of the @link{jmm.ml.data.RecordDataSet} interface.
 * 
 * @author john
 *
 */
public class DefaultRecordDataSet implements RecordDataSet {
	
	// private instance variables
	private String name;
	private List<Attribute> attributes = new java.util.ArrayList<Attribute>();

	/**
	 * Create a new record data set with the specified set of attributes.  The order of
	 * the attributes in the list must match the order that the attributes appear in the 
	 * data records.
	 * 
	 * The attributes are copied from the provided list into an internal data structure.
	 * 
	 * @param name the name of the data set (optional)
	 * @param attributes the list of the data set's attributes
	 */
	public DefaultRecordDataSet(final String name, final List<Attribute> attributes) {
		
		// initialize the data set's name
		this.name = name;		
		
		// initialize attributes for this data set
		java.util.Iterator<Attribute> iter = attributes.iterator();
		while (iter.hasNext()) {
			this.attributes.add(iter.next());
		}
	}

	/**
	 * Print out the name of the data set and its attributes.
	 */
	public String toString() {
		String rval = this.getClass().getName() + ": " + name + " [";
		java.util.Iterator<Attribute> iter = attributes.iterator();
		while (iter.hasNext()) {
			rval += "\n  " + iter.next().toString();
		}
		rval += "]";
		return rval;
	}
	
	/* (non-Javadoc)
	 * @see jmm.ml.data.DataSet#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see jmm.ml.data.RecordDataSet#getAttributeCount()
	 */
	@Override
	public int getAttributeCount() {
		return attributes.size();
	}

	/* (non-Javadoc)
	 * @see jmm.ml.data.RecordDataSet#getAttributes()
	 */
	@Override
	public List<Attribute> getAttributes() {
		return attributes;
	}

	/* (non-Javadoc)
	 * @see jmm.ml.data.RecordDataSet#getAttribute(int)
	 */
	@Override
	public Attribute getAttribute(int num) {
		return attributes.get(num);
	}

	/* (non-Javadoc)
	 * @see jmm.ml.data.RecordDataSet#getAttributeName(int)
	 */
	@Override
	public String getAttributeName(int num) {
		return attributes.get(num).getName();
	}

	/* (non-Javadoc)
	 * @see jmm.ml.data.RecordDataSet#getAttributeType(int)
	 */
	@Override
	public AttributeType getAttributeType(int num) {
		return attributes.get(num).getType();
	}

}
