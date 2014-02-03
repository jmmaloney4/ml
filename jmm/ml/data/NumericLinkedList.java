package jmm.ml.data;

public class NumericLinkedList extends LinkedList {

	//instance varibles
	private NumericNode head;
	private NumericNode tail;

	/**
	 * creates a new linked list
	 */
	public NumericLinkedList() {

	}

	/**
	 * creates a node for the specified value and adds it to the end of the list
	 * @param value
	 * @return the node created in this process
	 */
	public void add(float val) {
		NumericNode n = new NumericNode(val);
		if (this.head == null) {
			this.head = n;
			this.tail = n;
		}
		this.tail.setNext(n);
		this.tail = n;
	}
	
	/**
	 * creates a node for the specified value and adds it to the front of the list
	 * @param value
	 * @return the node created in this process
	 */
	public void addFront(float val) {
		NumericNode n = new NumericNode(val);
		if (this.head == null) {
			this.tail = n;
			this.head = n;
		}
		n.setNext(this.head);
		this.head = n;
	}
	
	/**
	 * gets the value of the first node in the sequence
	 * @return the value of the first node in the sequence
	 */
	public float getFirstValue() {
		float v = this.head.getValue();
		return v;
	}
	/**
	 * gets the value of the second node in the sequence
	 * @return the value of the second node in the sequence
	 */
	public float getLastValue() {
		float v = this.tail.getValue();
		return v;
	}
	
	private static class NumericNode extends Node {

		//instance varibles
		/**
		 * the value that this node holds
		 */
		private float value;
		
		public NumericNode(float val) {
			value = val;
		}
		
		/**
		 * gets the value that this node contains
		 * @return the value
		 */
		public float getValue() {
			return value;
		}
	}

}
