/**
 * 
 */
package jmm.ml.data;

import java.util.List;

/**
 * A data set that is a collection of records with a fixed set of attributes.
 * 
 * @author John Maloney
 *
 */
public interface RecordDataSet extends DataSet {
	
	/**
	 * Returns the number of attributes in the @link{jmm.ml.data.RecordDataSet} described by this
	 * meta data object.
	 */
	public int getAttributeCount();
	
	/**
	 * Get the designated data set's attribute meta data objects.
	 */
	public List<Attribute> getAttributes();
	
	/**
	 * Get the designated attribute's meta data object.
	 */
	public Attribute getAttribute(final int num);
	
	/**
	 * Get the designated attribute's name.
	 */
	public String getAttributeName(final int num);
	
	
	/**
	 * Get the designated attribute's type.
	 */
	public AttributeType getAttributeType(final int num);

}
