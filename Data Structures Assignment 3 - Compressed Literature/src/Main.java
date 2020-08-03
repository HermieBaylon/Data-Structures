/*
 * TCSS 342 – Spring 2020
 * Assignment 3 – Compressed Literature
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Main Class runs the entire program
 * @author Hermie Baylon
 * @version 9 May 2020
 */
public class Main {
	
	/** Represents the bytes for the encoded message */
	public static byte[] bytes;

	/** 
	 * Driver method that runs the entire program.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		File file = new File("WarAndPeace.txt");
		Scanner s = new Scanner(file);

		long start = System.nanoTime();
		
		StringBuilder result = new StringBuilder();
		while (s.hasNextLine()) {
			String line = s.nextLine();
			result.append(line + "\n");
		}

		System.out.println("Compression process for WarAndPeace.txt");
		System.out.println();
		CodingTree tree = new CodingTree(result.toString());

		PrintWriter writer = new PrintWriter("codes.txt", "UTF-8");
		for (Character c : tree.codes.keySet()) {
			String code = c+ "=" +tree.codes.get(c);
			writer.println(code);
		}
		writer.close();

		File compressedFile = new File("compressed.txt");
		compress(tree, compressedFile);
		
		long end = System.nanoTime();
		double time = (double)(end - start)/1000000000; // in seconds
		long compressedSize = compressedFile.length();
		long originalSize = file.length();
		double reduced = (((originalSize - compressedSize)*(100))/originalSize);
		
		System.out.println("Original Size: "+originalSize + " bytes");
		System.out.println("Compressed Size: "+compressedSize + " bytes");
		System.out.println();
		System.out.println("Reduced by: " +reduced+ "%");
		System.out.println("Running Time: " +time+ " Seconds");
		
		System.out.println("----------------------------------");
		System.out.println();
		
		testCodingTree("Hermie Pecaso Baylon");
	}
	
	/**
	 * Tests all methods of CodingTree
	 * @param s is the message to be used for testing
	 * @throws IOException 
	 */
	public static void testCodingTree(String s) {
		CodingTree tree = new CodingTree(s);
		
		System.out.println("TESTING AND BONUS");
		System.out.println("Original: " +s);
		System.out.println("Encoded form: " +tree.encodedMessage2);
		System.out.println("Decoded form: " +tree.decodedMessage2+ " (BONUS)");
		
		System.out.println();
		System.out.println("Root Node weight: " +tree.root.count);
		
		System.out.println();
		System.out.println("Frequency Table ");
		for (Character c : tree.charFrequency.keySet()) {
			System.out.println(c+ " -- " +tree.charFrequency.get(c));
		}
		
		System.out.println();
		System.out.println("Binary Table");
		for (Character c : tree.codes.keySet()) {
			System.out.println(c+ " -- " +tree.codes.get(c));
		}
	}

	/**
	 * Initializes the bytes[] needed to run the program
	 * @param s is the encoded message
	 */
	private static void createBits(String s) {
		bytes = new byte[s.length()/8];
		int j = 0;
		if (j < (s.length()/8)) {
			for (int i = 0; i < bytes.length;i = i+8) {
				String part = s.substring(i, i+8);
				int intBit = Integer.parseInt(part);
				bytes[j] = (byte) intBit;
				j++;
			}
		}
	}
	
	/**
	 * Compresses the encoded message from a given tree and store it in
	 * a File
	 * @param t is the given tree
	 * @param f is the file to store the compressed message
	 * @throws IOException
	 */
	private static void compress(CodingTree t, File f) throws IOException {
		createBits(t.encodedMessage2.toString());
		
		FileOutputStream fileOut = new FileOutputStream(f);
		fileOut.write(bytes);
		fileOut.close();
	}
}