/*
 * TCSS 342 – Spring 2020
 * Assignment 2 – Evolved Names  
 */

/**
 * Main is the class that runs the entire program
 * @author Hermie Baylon (Worked with partner: Calvin Nguyen)
 * @version 12 April 2020
 */
public class Main {

	/**
	 * main is the method that runs the entire program
	 * @param args an argument to run main method
	 */
	public static void main(String[] args) {
		long start = System.nanoTime();
		Population p = new Population(100, 0.05);

		int gen = 0;
		while (p.getMostFit().fitness() != 0) {
			p.day();
			System.out.println("("+p.getMostFit().toString()+", "+p.getMostFit().fitness()+")");
			gen++;
		}
		gen--;

		long end = System.nanoTime();
		long duration = (end - start)/1000000;
		System.out.println("Generations: " +gen);
		System.out.println("Running Time: " +duration+ " milliseconds");
		
		testGenome();
		testPopulation();
	}

	/**
	 * testGenome tests all the methods of the Genome class
	 */
	private static void testGenome() {
		System.out.println("-------------------------------------------------");
		System.out.println();
		System.out.println("TESTING GENOME CLASS METHODS");
		System.out.println();
		System.out.println();
		testGenomeHelper1();
		testGenomeHelper2();
		testGenomeHelper3();
		testGenomeHelper4();
	}

	/**
	 * private method helper for testGenome.
	 */
	private static void testGenomeHelper1() {
		System.out.println("TESTING - mutate()");
		System.out.println("First, lets create a Genome that cantains Professor Barreto's name");
		System.out.println("then lets mutate that Genome at 0.0, 0.25, 0.5, 0.75 and 1.0");
		System.out.println("mutation rates. Professor's name should change more as rates go up");
		System.out.println();
		
		double test = 0.0;

		for (int i = 0; i < 5; i++) {
			Genome g = new Genome(test);
			String target = g.target;
			g.setGenome(target);
			System.out.println("	With " +test+ " mutation rate");
			System.out.println("	Pre Mutate:  "+g);
			g.mutate();
			System.out.println("	Post Mutate: "+g);
			System.out.println();
			test = test + 0.25;
		}
	}
	
	/**
	 * private method helper for testGenome.
	 */
	private static void testGenomeHelper2() {
		System.out.println("TESTING - crossover()");
		System.out.println("Second, lets create a Genome that has Professor's name on it,");
		System.out.println("called genP and another Genome that has my name on it, called");
		System.out.println("genH. genP crossed with genH should combine both the genomes");
		System.out.println("according to what it says on the Spec. We will keep mutation");
		System.out.println("at 0.05.");
		Genome genP = new Genome(0.05);
		Genome genH = new Genome(0.05);
		genP.setGenome(genP.target);
		genH.setGenome("Hermie Baylon");
		System.out.println();
		System.out.println("	Paulo's Genome:      "+genP);
		System.out.println("	Hermie's Genome:     "+genH);
		genP.crossover(genH);
		genH.crossover(genP);
		System.out.println("	Result(GenP x GenH): " +genP);
		System.out.println("	Result(GenH x GenP): " +genH);
		System.out.println();
	}
	
	/**
	 * private method helper for testGenome.
	 */
	private static void testGenomeHelper3() {
		System.out.println("TESTING - fitness()");
		System.out.println("Third, lets create Genomes that contains Professor's name,");
		System.out.println("but with different levels of completion. We will then see");
		System.out.println("how each level compares to his full by using the fitness()");
		Genome a = new Genome(0.05);
		
		System.out.println();
		a.setGenome("PAULO ");
		System.out.println("	"+a);
		System.out.println("	Expected Fitness: 66");
		System.out.println("	Actual Fitness:   "+a.fitness());

		System.out.println();
		a.setGenome("PAULO SERGIO ");
		System.out.println("	"+a);
		System.out.println("	Expected Fitness: 52");
		System.out.println("	Actual Fitness:   "+a.fitness());
		
		System.out.println();
		a.setGenome("PAULO SERGIO LICCIARDI ");
		System.out.println("	"+a);
		System.out.println("	Expected Fitness: 32");
		System.out.println("	Actual Fitness:   "+a.fitness());
		
		System.out.println();
		a.setGenome("PAULO SERGIO LICCIARDI MESSEDER ");
		System.out.println("	"+a);
		System.out.println("	Expected Fitness: 14");
		System.out.println("	Actual Fitness:   "+a.fitness());
		
		System.out.println();
		a.setGenome("PAULO SERGIO LICCIARDI MESSEDER BARRETO");
		System.out.println("	"+a);
		System.out.println("	Expected Fitness: 0");
		System.out.println("	Actual Fitness:   "+a.fitness());
	}
	
	/**
	 * private method helper for testGenome.
	 */
	private static void testGenomeHelper4() {
		System.out.println();
		System.out.println("TESTING - toString()");
		System.out.println("Let's create a Genomes and change it by mutation/crossover");
		System.out.println("Then using toString method, we will print those genes");
		
		Genome a = new Genome(0.05);
		Genome b = new Genome(0.05);
		Genome c = new Genome(0.05);
		b.setGenome("Hermie");
		c.setGenome("Baylon");
		System.out.println();
		System.out.println("	Default Gene:                " +a.toString());
		System.out.println("	Random Gene1:                " +b.toString());
		System.out.println("	Random Gene2: 	             " +c.toString());
		b.mutate();
		System.out.println("	Mutate Random Gene1: 	     " +b.toString());
		b.crossover(c);
		System.out.println("	Random Gene1 x Random Gene2: " +b.toString());
	}

	/**
	 * testPopulation tests all the methods in the Population class
	 */
	private static void testPopulation() {
		System.out.println("-------------------------------------------------");
		System.out.println();
		System.out.println("TESTING POPULATION CLASS METHODS");
		System.out.println();
		System.out.println();
		testPopulationHelper1();
	}
	
	/**
	 * private method helper for testPopulation.
	 */
	private static void testPopulationHelper1() {
		System.out.println("TESTING - day()");
		System.out.println("First, we create a population of 100 Genomes with 0/05");
		System.out.println("mutation rate. Then we apply several days (generations) to test");
		System.out.println("the day() method. Here We are also testing the toString");
		System.out.println("method.");
		System.out.println();
		
		Population p = new Population(100, 0.05);
		int gen = 1;
		System.out.println("Generation 1: ");
		System.out.println(p);
		System.out.println();
		for (int i = 0; i < 4; i++) {
			p.day();
			gen++;
		}
		System.out.println("Generation " +gen+ ": ");
		System.out.println(p);
		System.out.println();
		for (int i = 0; i < 45; i++) {
			p.day();
			gen++;
		}
		System.out.println("Generation " +gen+ ": ");
		System.out.println(p);
		System.out.println();
	} 
}

