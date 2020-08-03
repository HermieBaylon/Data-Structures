/*
 * TCSS 342 – Spring 2020
 * Assignment 1 – Baron Burger  
 */

import java.util.NoSuchElementException;

/**
 * MyStack is a class (Linked list) that represents a stack of 
 * <Type> datas. 
 * 
 * @author Hermie Baylon
 * @version 12 April 2020
 */
public class MyStack<Type> {
	
	/**
	 * Node is a class that represents a node for the MyStack to use
	 * to function like a linked list
	 */
	private class Node <Type> {
		
		/** data represents the data of Node */
		 public Type data;
		 
		 /** next represents the next Node of this node*/
		 public Node next;
		 
		 /**
		  * Node is a constructor that initializes data to null and
		  * next to null.
		  */
		 public Node() {
			 this(null, null);
		 }
		 
		 /**
		  * Node is a constructor that initializes data to a passed in data
		  * of type <Type> and next to null
		  * 
		  * @param data is the data that the user initializes data to
		  */
		 public Node(Type data) {
			 this(data, null);
		 }
		 
		 /**
		  * Node is a constructor that initializes data to a passed in data
		  * and next to a passed in Node
		  * 
		  * @param data is the data that the user initializes data to
		  * @param next is the Node that the user initializes next to
		  */
		 public Node(Type data, Node next) {
			 this.data = data;
			 this.next = next;
		 }
	}
	
	/** front represents the front Node of the MyStack */
	private Node front;

	/**
	 * MyStack is a constructor that initializes front to null
	 */
	public MyStack() {
		front = null;
	}

	/**
	 * isEmpty tells user if the MyStack is empty
	 * 
	 * @return true if front is null
	 */
	public boolean isEmpty() {
		return front == null;
	}

	/**
	 * push adds an item in MyStack in front
	 * 
	 * @param item is the item the user wants to add
	 */
	public void push(Type item) {
		Node newNode = new Node(item, front);
		front = newNode;
	}

	/**
	 * pop removes an item in front of MyStack
	 * 
	 * @return the item that is being removed
	 */
	public Type pop() {
		Node temp = new Node();
		temp = front.next;
		Type t = (Type) front.data;
		front = temp;
		return t;
	}

	/**
	 * peek lets user have access to the front of MyStack
	 * 
	 * @return front of MyStack
	 */
	public Type peek() {
		return (Type) front.data;
	}

	/**
	 * size calculates the size of MyStack
	 * 
	 * @return how many elements in MyStack
	 */
	public int size() {
		int count = 0;
		Node node = front;

		if (front == null) {
			count = 0;
		} else if (front != null && front.next == null) {
			count = 1;
		} else {
			while (node.next != null) {
				count++;
				node = node.next;
			}
			count++;
		}
		return count;
	}

	/**
	 * toString represents MyStack in String form
	 */
	public String toString() {
		String result = "";
		String b1 = "[";
		String b2 = "]";

		Node node = front;
		if (front == null) {
			result = "nothing";
		} else if (front != null && front.next == null) {
			result = result + front.data;
		} else {
			while (node.next != null) {
				result = result + node.data + ", ";
				node = node.next;
			}
			result = result + node.data;
		}
		return b1 + result + b2;
	}
}



