/**
 * 
 */
package jmm.ml.data;

/**
 * @author john
 *
 */
public interface Attribute {
	
	/**
	 * Get the name of this attribute.
	 */
	public String getName();
	
	/**
	 * Get the type of this attribute.
	 */
	public AttributeType getType();
}
