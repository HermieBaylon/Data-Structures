/*
 * TCSS 342 – Spring 2020
 * Assignment 2 – Evolved Names  
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Population Class represents a population of Genomes
 * @author Hermie Baylon (Worked with partner: Calvin Nguyen)
 * @version 12 April 2020
 */
public class Population {
	
	/** Represents the most fit Genome in the population */
	private Genome mostFit;
	
	/** Represents the entire population */
	private List<Genome> population;

	/**
	 * @param numGenomes is the number of Genomes the user wants in the population
	 * @param mutationRate is the rate the the user wants for each genome
	 */
	public Population(Integer numGenomes, Double mutationRate) {
		population = new ArrayList<Genome>(numGenomes);
		for (int i = 0; i < numGenomes; i++) {
			Genome g = new Genome(mutationRate);
			population.add(g);
		}
		mostFit = population.get(0);
	}

	/**
	 * day() method represents a generation cycle of the population.
	 * Each day() the population evolves
	 */
	public void day() {
		mostFit = population.get(0);
		int totalPop = population.size();
		int half = totalPop / 2;
		int toBeAdded = totalPop - half;
		
		for (int i = 0; i < toBeAdded; i++) {
			population.remove(population.size()-1);
		}
		
		int additions = 0;
		int flip = (int) (Math.random() * 2);
		int randomIndex = (int) (Math.random() * half);
		int randomIndex2 = (int) (Math.random() * half);
		Genome newGenome = new Genome(population.get(randomIndex));
		Genome newGenome2 = new Genome(population.get(randomIndex2));
		
		while (additions != toBeAdded) {
			if (flip == 1) {
				// then option 2, otherwise option 1
				newGenome.crossover(newGenome2);
			}
			newGenome.mutate();
			population.add(newGenome);
			additions++;
			randomIndex = (int) (Math.random() * half);
			randomIndex2 = (int) (Math.random() * half);
			newGenome = new Genome(population.get(randomIndex));
			newGenome2 = new Genome(population.get(randomIndex2));
			flip = (int) (Math.random() * 2);
		}
		Collections.sort(population, new SortFitness());
	}

	/**
	 * toString represents the String representation of this class
	 */
	public String toString() {
		String result = "";
		for (Genome g : population) {
			result = result + g.toString() + " - " +g.fitness()+ "\n";
		}
		return result;
	}
	
	/**
	 * @return the most fit in the population
	 */
	public Genome getMostFit() {
		return mostFit;
	}

}

/**
 * SortFitness is a class that implements Comparator for sorting purposes
 * @author Hermie Baylon
 * @version 23 April 2020
 */
class SortFitness implements Comparator<Genome>{

	/**
	 * compare() compares Genome a and b's fitness
	 */
	@Override
	public int compare(Genome a, Genome b) {
		return a.fitness() - b.fitness();
	}
}
