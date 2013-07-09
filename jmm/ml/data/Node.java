package jmm.ml.data;
/**
 * an abstract class to become the superclass of all the different node types
 * @author jack
 *
 */
public abstract class Node {

	//instance variables
	private Node next;
	
	/**
	 * gets the next node in the linked list
	 * @return the next node in the linked list
	 */
	public Node getNext() {
		return next;
	}
	
	/**
	 * sets the node that is next in the linked list
	 * @return the node that was passed in 
	 */
	public Node setNext(Node n) {
		next = n;
		return n;
	}
	
}
