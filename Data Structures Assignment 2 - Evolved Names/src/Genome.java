/*
 * TCSS 342 – Spring 2020
 * Assignment 2 – Evolved Names  
 */

/**
 * Genome is a class that represents the Genome object
 * @author Hermie Baylon (Worked with partner: Calvin Nguyen)
 * @version 23 April 2020
 */
public class Genome {

	/** Represents the target genes are evolving to */
	public static final String target = "PAULO SERGIO LICCIARDI MESSEDER BARRETO";

	/** Represents the actual Genome in String form */
	private String genome;

	/** Represents the rate at which the Genome mutates */
	private double rate;

	/** Represents all possible genes that the Genome can have */
	public static final char[] world = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 
			'H','I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '-', '\''};

	/**
	 * Genome is a constructor that initializes the genome to "A" and rate to a given 
	 * double parameter.
	 * @param mutationRate is the rate that the user wants the genome to mutate.
	 * rate must be between 0 and 1. Otherwise throws exception
	 */
	public Genome(double mutationRate) {
		if (mutationRate > 1 || mutationRate < 0) {
			throw new IllegalArgumentException("The rate must be between 0 and 1");
		}
		genome = "A";
		rate = mutationRate;
	}

	/**
	 * Genome is constructor that clones another genome
	 * @param gene is the genome to clone
	 */
	public Genome(Genome gene) {
		this.genome = gene.toString();
		this.rate = gene.getRate();
	}

	/**
	 * mutate() method mutates a genome by adding, removing, or changing a 
	 * random gene from a random location.
	 */
	public void mutate() {
		double addChance = Math.random();
		double removeChance = Math.random();
		double changeChance = Math.random();

		if (addChance <= rate) {
			// add a random char in a random position
			int randomIndex = (int) (Math.random() * 29);
			char randomChar = world[randomIndex];
			int randomPosition = (int) (Math.random() * genome.length() + 1);

			String a = genome.substring(0, randomPosition);
			String b = randomChar + "";
			String c = genome.substring(randomPosition, genome.length());

			genome = a + b + c;
		}

		if (removeChance <= rate && genome.length() >= 2) {
			// remove a char from a random position
			int randomPosition = (int) (Math.random() * (genome.length()));

			String a = genome.substring(0, randomPosition);
			String b = genome.substring(randomPosition + 1, genome.length());

			genome = a + b;

		}

		for (int i = 0; i < genome.length(); i++) {
			int randomIndex = (int) (Math.random() * 29);
			char randomChar = world[randomIndex];

			if (changeChance <= rate) {
				// change that particular char to something random
				String a = genome.substring(0, i);
				String b = randomChar + "";
				String c = genome.substring(i + 1, genome.length());

				genome = a + b + c;

			}
			randomIndex = (int) (Math.random() * 29);
			randomChar = world[randomIndex];
			changeChance = Math.random();
		}
	}

	/**
	 * crossover method combines the genes of this genome and another genome
	 * @param other is the other genome that that user wants to combine genes
	 * with this genome.
	 */
	public void crossover(Genome other) {

		String child = "";
		char[] parent1 = genome.toCharArray();
		char[] parent2 = other.toString().toCharArray();
		int max = findMax(genome, other.toString());
		int flipParent = (int) (Math.random() * 2);

		for (int i = 0; i < max; i++) {
			if (flipParent == 0) {
				if (i >= parent1.length) {
					break;
				}
				child = child + parent1[i];
			} else if (flipParent == 1) {
				if (i >= parent2.length) {
					break;
				}
				child = child + parent2[i];
			}
			flipParent = (int) (Math.random() * 2);
		}
		genome = child;
	}

	/**
	 * fitness calculates how different this genome is from the target genome
	 * @return the calculated fitness
	 */
		public Integer fitness() {
			
			char[] gArray = genome.toCharArray();
			char[] tArray = target.toCharArray();
			
			int n = genome.length();
			int m = target.length();
			int l = findMax(genome, target);
			Integer f = Math.abs(m-n);
			
			for (int i = 0; i < l; i++) {
				if (i < genome.length() && i < target.length()) {
					if (gArray[i] != tArray[i]) {
						f++;
					}
				} else {
					f++;
				}
			}
			return f;
		}

	/**
	 * toString returns the string representation of this class
	 */
	public String toString() {
		return genome;
	}

	/**
	 * @return rate of mutation
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @param s is the String that the user wants to change the genome to
	 * (for testing purposes)
	 */
	public void setGenome(String s) {
		genome = s;
	}

	/**
	 * @param s1 - A String argument
	 * @param s2 - A String argument
	 * @return the max length of both string arguments
	 */
	private int findMax(String s1, String s2) {
		int max = 0;
		if (s1.length() > s2.length()) {
			max = s1.length();
		} else {
			max = s2.length();
		}
		return max;
	}
}