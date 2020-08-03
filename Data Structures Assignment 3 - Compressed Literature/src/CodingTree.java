/*
 * TCSS 342 – Spring 2020
 * Assignment 3 – Compressed Literature
 */

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * CodingTree class represents a tree for a given message using
 * Huffman Algorithm.
 * @author Hermie Baylon
 * @version 9 May 2020
 */
public class CodingTree {

	/** Stores all unique characters in the message and their corresponding binary code */
	public Map<Character, String> codes;

	/** Stores all unique characters in the message and their corresponding frequency */
	public Map<Character, Integer> charFrequency; 

	/** Stores all nodes for all unique characters */
	public PriorityQueue<Node> q;

	/** Stores the root of the tree*/
	public Node root;

	/** Stores the binary code representation of the message */
	public StringBuilder encodedMessage2;

	/** Stores the decoded message of encodedMessage2 */
	public StringBuilder decodedMessage2;

	/**
	 * Constructor that counts all frequencies of each unique
	 * character, creates a Node for all unique characters, and
	 * initializes all other public fields.
	 * @param message - the String to encode and decode
	 */
	public CodingTree(String message) {
		codes = new HashMap<Character, String>();
		charFrequency = new HashMap<Character, Integer>();
		q = new PriorityQueue<Node>();
		root = new Node();
		encodedMessage2 = new StringBuilder();
		decodedMessage2 = new StringBuilder();

		for (int i = 0; i < message.length(); i++) {
			char c = message.charAt(i);
			if (charFrequency.containsKey(c)) {
				charFrequency.put(c, charFrequency.get(c) + 1);
			} else {
				charFrequency.put(c, 1);
			}
		}

		for (char key : charFrequency.keySet()) {
			char character = key;
			int frequency = charFrequency.get(key);
			Node n = new Node(character, frequency);
			q.add(n);
		}
		root = new Node(createTree());
		writeBinaryCode(root, "");
		encode(message);
		decodedMessage2 = decode(encodedMessage2);
	}

	/**
	 * Creates a tree for all unique characters using
	 * Huffman Algorithm
	 * @return the Root Node
	 */
	private Node createTree() {
		while (q.size() > 1) {
			Node min1 = new Node(q.remove());
			Node min2 = new Node(q.remove());

			int newCount = min1.count + min2.count;
			Node newNode = new Node(newCount, min1, min2);
			q.add(newNode);
		}
		Node theRoot = new Node(q.peek());
		return theRoot;
	}

	/**
	 * Writes the binary code for all unique characters
	 * @param node - Represents the root
	 * @param binaryCode - Binary Representation of Character
	 */
	private void writeBinaryCode(Node node, String binaryCode) {

		if (node.left == null && node.right == null) {
			char character = node.character;
			codes.put(character, binaryCode);
		} else {
			writeBinaryCode(node.left, binaryCode+0);
			writeBinaryCode(node.right, binaryCode+1);
		}
	}

	/**
	 * Encodes the message into String of 0's and 1's
	 * @param message - the message to encode
	 */
	private void encode(String message) {
		for (char c : message.toCharArray()) {
			encodedMessage2.append(codes.get(c));
		}
	}

	/**
	 * Decodes the encoded message to the original message
	 * @param encoded - the encoded binary string 
	 * @return the original message
	 * @throws FileNotFoundException 
	 */
	private StringBuilder decode(StringBuilder encoded) {
		StringBuilder result = new StringBuilder();
		Node current = new Node(root);
		for (int i = 0; i < encoded.length(); i++) {
			while (!(current.left == null && current.right == null)) {
				char bit = encoded.charAt(i);
				if (bit == '0') {
					current = current.left;
				} 
				else if (bit == '1') {
					current = current.right;
				}
				i++;
			}
			i--;
			result.append(current.character);
			current = new Node(root);
		}
		return result;
	}

	/**
	 * Node class represents Node to create Trees
	 * @author Hermie Baylon
	 * @version 9 May 2020
	 */
	public class Node implements Comparable<Node> {

		/** Represents the frequency of a character */
		public int count;

		/** Represents the Character itself */
		public char character;

		/** Represents the left Node */
		public Node left;

		/** Represents the right Node */
		public Node right;

		/** Initializes Character to white space and count to 0 */
		public Node () {
			this(' ', 0, null, null);
		}

		/** Initializes Character and count to some given data */
		public Node(char character, int count) {
			this(character, count, null, null);
		}

		/** Clones a Node */
		public Node(Node node) {
			this(node.character, node.count, node.left, node.right);
		}

		/** Initializes the count, left, right Node to given data */
		public Node(int count, Node left, Node right) {
			this.count = count;
			this.left = left;
			this.right = right;
		}

		/** Initializes all fields to given data */
		public Node(char character, int count, Node left, Node right) {
			this.count = count;
			this.character = character;
			this.left = left;
			this.right = right;
		}

		/** Compares all Nodes by their count */
		@Override
		public int compareTo(Node other) {
			if (this.equals(other)) {
				return 0;
			} else if (count > other.count) {
				return 1;
			} else {
				return -1;
			}
		}

		/** String representation of Node class */
		public String toString() {
			String s = "Character: "+character+ " Frequency: " +count;
			return s;
		}
	}
}