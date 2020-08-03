/*
 * TCSS 342 – Spring 2020
 * Assignment 4 – Compressed Literature 2
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main Class runs the entire program
 * @author Hermie Baylon
 * @version 23 May 2020
 */
public class Main {

	public static void main(String[] args) throws IOException {
		File file = new File("WarAndPeace.txt");
		Scanner s = new Scanner(file);
		
		long start = System.nanoTime();

		StringBuilder result = new StringBuilder();
		while (s.hasNextLine()) {
			String line = s.nextLine();
			result.append(line + "\n");
		}

		CodingTree tree = new CodingTree(result.toString());

		File compressedFile = new File("compressed.txt");
		FileOutputStream fileOut = new FileOutputStream(compressedFile);
		fileOut.write(tree.bytes);
		fileOut.close();
		
		tree.frequency.stats();

		long end = System.nanoTime();
		double time = (double)(end - start)/1000000000; // in seconds
		long compressedSize = compressedFile.length();
		long originalSize = file.length();
		double reduced = (((originalSize - compressedSize)*(100))/originalSize);

		System.out.println();
		System.out.println("Original Size: "+originalSize + " bytes");
		System.out.println("Compressed Size: "+compressedSize + " bytes");
		System.out.println("Reduced by: " +reduced+ "%");
		System.out.println("Running Time: " +time+ " Seconds");
		
		System.out.println();
		System.out.println("============================================");
		System.out.println();
		
		testCodingTree();
		
		System.out.println("============================================");
		System.out.println();
		
		testMyHashTable();
	}
	
	/**
	 * tests the CodingTree class and its methods.
	 * Prints out original phrase.
	 * Prints out encoded message of the original phrase.
	 * Prints out decoded message of the encoded message (Original phrase).
	 */
	public static void testCodingTree() {
		String s = "Hello Hello Hello My name is Hermie Baylon bye-bye";
		CodingTree tree = new CodingTree(s);
		StringBuilder compressed = new StringBuilder();
		for (int i = 0; i < tree.bytes.length; i++) {
			compressed.append(""+tree.bytes[i]);
		}
		
		System.out.println("***** CodingTree TESTING *****\n");
		System.out.println("Phrase: \n" +s+"\n");
		System.out.println("Encoded String: \n" +tree.bits.toString() +"\n");
		System.out.println("Decoded String (EXTRA CREDIT): ");
		System.out.println(tree.decode(tree.bits.toString()) + "\n");
		System.out.println("Compressed form (Printed bytes):\n"+compressed+"\n");
	}
	
	/**
	 * Tests the MyHashTable class and its methods.
	 * Prints out frequency table.
	 * Prints out codes table.
	 * Prints our stats() method.
	 */
	public static void testMyHashTable() {
		String s = "Hello Hello Hello My name is Hermie Baylon bye-bye";
		CodingTree tree = new CodingTree(s);
		
		System.out.println("***** MyHashTable TESTING *****\n");
		System.out.println("Frequency Table");
		System.out.println(tree.frequency);
		System.out.println("Codes Table");
		System.out.println(tree.codes);
		tree.codes.stats();
	}
}