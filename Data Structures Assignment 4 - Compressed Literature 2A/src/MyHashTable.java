/*
 * TCSS 342 – Spring 2020
 * Assignment 4 – Compressed Literature 2
 */

import java.util.Arrays;

/**
 * MyHashTable stores a Key and Value of any type.
 * @author Hermie Baylon
 * @version 23 May 2020
 */
public class MyHashTable<K, V> {

	/** Stores all the keys in MyHashTable */
	public K[] keys;

	/** Stores all the values in MyHashTable */
	public V[] values;

	/** Represents the Capacity that MyHashTable can hold (keys or values) */
	public int size;

	/** Represents how many keys or values in MyHashTable */
	public int entries;

	/** Represents how many entries require n (index) probes */
	public int[] probes;

	/** Tracks the numbers of probes */
	private int probeTracker;

	/** Represents the maximum probe */
	public int farthestIndex;

	/**
	 * Constructor that initializes all Integer fields to 0, 
	 * arrays keys and values to size capacity, and probes 
	 * to size 2000.
	 * @param capacity - desired capacity for MyHashTable
	 */
	public MyHashTable(int capacity) {
		keys = (K[])   new Object[capacity];
		values = (V[]) new Object[capacity];
		size = capacity;
		entries = 0;
		probes = new int[2000];
		probeTracker = 0;
		farthestIndex = 0;
		Arrays.fill(values, null);
	}

	/**
	 * Puts a desired key and value to MyHashTable. If key
	 * is already in MyHashTable, replace its old value with
	 * the new value.
	 * @param searchKey - desired key
	 * @param newValue - desired value
	 */
	public void put(K searchKey, V newValue) {
		int index = hash(searchKey);
		int probeIndex = 0;

		while (values[index] != null) {
			if (this.containsKey(searchKey)) {
				values[index] = newValue;
				return;
			}
			index++;
			probeIndex++;
			if (probeIndex > farthestIndex) {
				farthestIndex = probeIndex;
			}
			if (index >= size) {
				index = 0;
			}
		}
		values[index] = newValue;
		keys[index] = searchKey;
		probes[probeIndex]++;
	}

	/**
	 * @return the corresponding value of a given key. 
	 * @return null if can't find key.
	 * @param searchKey - given key.
	 */
	public V get(K searchKey) {
		int index = hash(searchKey);

		while (keys[index] != null) {
			if (keys[index].equals(searchKey)) {
				return values[index];
			}
			index++;
			if (index >= size) {
				index = 0;
				return null;
			}
		}
		return null;
	}

	/**
	 * @return true if given key is in MyHashMap.
	 * @return false otherwise.
	 * @param searchKey - given key.
	 */
	public boolean containsKey(K searchKey) {
		int index = hash(searchKey);

		while (keys[index] != null) {
			if (keys[index].equals(searchKey)) {
				return true;
			}
			index++;
			if (index >= size) {
				index = 0;
				return false;
			}
		}
		return false;
	}

	/**
	 * Prints out useful statistics about MyHashMaps.
	 */
	public void stats() {
		StringBuilder probesResult = new StringBuilder();
		int every15 = 0;
		for (K keys : keys) {
			if (keys != null) {
				entries++;
			}
		}
		System.out.println("Hash Table Stats");
		System.out.println("================");
		System.out.println("Number of Entries: "+entries);
		System.out.println("Number of Buckets: "+size);
		System.out.println("Histogram of Probes: ");

		probesResult.append("[");

		if (farthestIndex > 0) {

			for (int i : probes) {
				if (probeTracker == farthestIndex - 1) {
					break;
				}
				if (every15 == 15) {
					probesResult.append("\n");
					every15 = 0;
				}
				probeTracker++;
				every15++;
				probesResult.append(i+", ");
			}
		}
		probesResult.append(probes[probeTracker]);
		probesResult.append("]");
		System.out.println(probesResult.toString());
		float fill = (float) 100 * entries / size;
		String fillPercentage = String.format("%.2f", fill);
		System.out.println("Fill Percentage: "+fillPercentage+ "%");
		System.out.println("Max Linear Probe: "+farthestIndex);
		float total = 0;
		for (int i = 0; i <= farthestIndex; i++) {
			total = total + probes[i]*i;
		}
		float average = (float) total / entries;
		System.out.println("Average Linear Probe: "+average);
	}

	/**
	 * Produces an index for a given key.
	 * @param key - given key.
	 * @return the index.
	 */
	private int hash(K key) {
		int code = key.hashCode();
		int index = Math.abs(code % size);
		return index;
	}

	/**
	 * @return the String representation of MyHashTable
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] != null) {
				result.append("Key: "+keys[i]+ " ----------- Value: "+values[i]+ "\n");
			}
		}
		return result.toString();
	}
}
