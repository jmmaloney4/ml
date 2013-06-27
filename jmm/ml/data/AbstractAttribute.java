package jmm.ml.data;


public abstract class AbstractAttribute implements Attribute {
	
	// private instance variables
	private String name;

	// constructors
	/**
	 * Construct an attribute with the specified name.
	 */
	public AbstractAttribute(final String name) {
		this.name = name;
	}
	/**
	 * Get the name of this attribute.
	 */
	public String getName() {
		return name;
	}
	
	public String toString() {
		return this.getClass().getName() + ": " + getName();
	}
}
