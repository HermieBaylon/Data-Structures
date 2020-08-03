/*
 * TCSS 342 – Spring 2020
 * Assignment 4 – Compressed Literature 2
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * CodingTree represents a tree for a given message using
 * Huffman Algorithm.
 * @author Hermie Baylon
 * @version 23 May 2020
 */
public class CodingTree {
	
	/** Stores all unique words in the message and their corresponding Binary codes */
	public MyHashTable<String, String> codes;
	
	/** Stores all unique words in the message and their corresponding frequencies */
	public MyHashTable<String, Integer> frequency;
	
	/** Stores all nodes for all unique characters */
	public PriorityQueue<Node> q; 
	
	/** Stores the binary code representation of the message */
	public StringBuilder bits;
	
	/** Stores the root of the tree*/
	public Node root;
	
	/** Stores the binary code representation of the message in byte[] form */
	public byte[] bytes;
	
	/** Stores the separators in the message */
	public ArrayList<Character> separators;
	
	/** Represents the capacity of each MyHashTable created */
	public static final int CAPACITY = 32768;

	/** 
	 * Constructor that counts all frequencies of each unique 
	 * words, creates a Node for all unique words, and initializes
	 * all other fields.
	 * @param message - the String to encode and decode.
	 */
	public CodingTree(String fulltext) {
		codes = new MyHashTable<String, String>(CAPACITY);
		frequency = new MyHashTable<String, Integer>(CAPACITY);
		q = new PriorityQueue<Node>();
		bits = new StringBuilder();
		root = new Node();
		separators = new ArrayList<Character>();
		separators.add(' ');
		StringBuilder builder = new StringBuilder();
		int tracking = 0;

		for (int i = 0; i < fulltext.length(); i++) {
			tracking = i;
			String currentString = fulltext.charAt(i) + "";
			if (isSeparator(currentString)) {
				if (frequency.containsKey(builder.toString())) {
					String key = builder.toString();
					Integer value = frequency.get(key) + 1;
					frequency.put(key, value);
				} else {
					String key = builder.toString();
					Integer value = 1;
					frequency.put(key, value);
				}
				separators.add(fulltext.charAt(i));
				Node node = new Node(builder.toString(), frequency.get(builder.toString()));
				q.add(node);
				builder = new StringBuilder();
			} else {
				builder.append(currentString);
			}
			// add the last one
			if (tracking == fulltext.length() - 1) {
				if (frequency.containsKey(builder.toString())) {
					String key = builder.toString();
					Integer value = frequency.get(key) + 1;
					frequency.put(key, value);
				} else {
					frequency.put(builder.toString(), 1);
				}
				Node node = new Node(builder.toString(), frequency.get(builder.toString()));
				q.add(node);
			}
		}
		root = createTree();
		writeBinaryCode(root, "");
		encode(fulltext);
		createBits(bits.toString());
	}

	/**
	 * Creates a tree for all unique words using
	 * Huffman Algorithm
	 * @return the Root Node
	 */
	private Node createTree() {
		while (q.size() > 1) {
			Node min1 = new Node(q.remove());
			Node min2 = new Node(q.remove());

			Integer newCount = min1.count + min2.count;
			Node newNode = new Node(newCount, min1, min2);
			q.add(newNode);
		}
		Node theRoot = new Node(q.peek());
		return theRoot;
	}

	/**
	 * Writes the binary code for all unique words
	 * @param node - Represents the root
	 * @param code - Binary Representation of Character
	 */
	private void writeBinaryCode(Node node, String code) {
		if (node.left == null && node.right == null) {
			String word = node.word;
			codes.put(word, code);
		} else {
			writeBinaryCode(node.left, code+0);
			writeBinaryCode(node.right, code+1);
		}
	}

	/**
	 * Encodes the message into String of 0's and 1's
	 * @param message - the message to encode
	 */
	private void encode(String message) {
		StringBuilder builder = new StringBuilder();
		int tracker = 0;

		for(int i = 0; i < message.length(); i++) {
			tracker = i;
			String currentString = message.charAt(i)+ "";
			if (isSeparator(currentString)) {
				String code = codes.get(builder.toString());
				bits.append(code);
				builder = new StringBuilder();
			} else {
				builder.append(currentString);
			}
			if (tracker == message.length() - 1) {
				String code = codes.get(builder.toString());
				bits.append(code);
			}
		}
	}

	/**
	 * creates bits out of a given String
	 * @param s - the String to creates bits from
	 */
	private void createBits(String s) {
		bytes = new byte[s.length()/8];
		int j = 0;
		if (j < (s.length()/8)) {
			for (int i = 0; i < bytes.length; i = i+8) {
				String part = s.substring(i, i+8);
				int intBit = Integer.parseInt(part);
				bytes[j] = (byte) intBit;
				j++;
			}
		}
	}

	/**
	 * Decodes the encoded message to the original message
	 * @param encoded - the encoded binary string 
	 * @return the original message
	 * @throws FileNotFoundException 
	 */
	public StringBuilder decode(String encoded) {
		StringBuilder result = new StringBuilder();
		Node current = new Node(root);
		int separatorTracker = 0;
		for (int i = 0; i < encoded.length(); i++) {
			while (!(current.left == null && current.right == null)) {
				char bit = encoded.charAt(i);
				if (bit == '0') {
					current = current.left;
				} else if (bit == '1') {
					current = current.right;
				}
				i++;
			}
			i--;
			separatorTracker++;
			result.append(current.word);
			if (separatorTracker < separators.size()) {
				result.append(separators.get(separatorTracker));
			}
			current = new Node(root);
		}
		return result;
	}

	/**
	 * @param s - String from the message
	 * @return true if the String is a separator. False otherwise.
	 */
	private boolean isSeparator(String s) {
		String numbers = "0123456789";
		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		String others = "'-";

		if (numbers.contains(s) ||
				upperAlphabet.contains(s) ||
				lowerAlphabet.contains(s) ||
				others.contains(s)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Node class represents Node to create Trees
	 * @author Hermie Baylon
	 * @version 23 May 2020
	 */
	public class Node implements Comparable<Node> {
		
		/** Represents the word itself */
		public String word;
		
		/** Represents the frequency of a word */
		public int count;
		
		/** Represents the left Node */
		public Node left;
		
		/** Represents the right Node */
		public Node right;

		/** Initializes all fields to given data */
		public Node(String word, int count, Node left, Node right) {
			this.word = word;
			this.count = count;
			this.left = left;
			this.right = right;
		}

		/** Initializes word and count to some given data */
		public Node(int count, Node left, Node right) {
			this.count = count;
			this.left = left;
			this.right = right;
		}

		/** Clones a Node */
		public Node(Node node) {
			this(node.word, node.count, node.left, node.right);
		}

		/** Initializes word and count to some given data */
		public Node(String word, int count) {
			this(word, count, null, null);
		}

		/** Initializes word to empty space and count to 0 */
		public Node () {
			this("", 0, null, null);
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
			String s = "Word: "+word+ "	---	Frequency: " +count;
			return s;
		}
	}
}

