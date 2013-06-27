package jmm.ml.data;


public class RatioAttribute extends AbstractAttribute {
	
	/**
	 * Construct a ratio attribute with the specified name.
	 */
	public RatioAttribute(final String name) {
		super(name);
	}
	
	/**
	 * Get the type of this attribute.
	 */
	public AttributeType getType() {
		return AttributeType.RATIO;
	}	
}
