package jmm.ml.data;

import java.util.List;
import java.util.Map;


public class NominalAttribute extends AbstractAttribute {
	
	/**
	 * Construct a nominal attribute with the specified name.
	 */
	public NominalAttribute(final String name, final List<String> nominalSpec) {
		super(name);
		
		valueMap = new java.util.Hashtable<String, Integer>();
		// populate values
		java.util.Iterator<String> iter = nominalSpec.iterator();
		int i = 0;
		while (iter.hasNext()) {
			i++;
			valueMap.put(iter.next(), i);
		}
	}
	
	// private instance variables
	private Map<String, Integer> valueMap;
}
