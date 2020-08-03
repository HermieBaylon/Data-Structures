/*
 * TCSS 342 – Spring 2020
 * Assignment 1 – Baron Burger  
 */

import java.io.File;
import java.util.Scanner;

/**
 * This program runs the Burger and MyStack classes by taking in
 * a file input
 * 
 * @author Hermie Baylon
 * @version 12 April 2020
 */
public class Main {

	/**
	 * main runs the Burger and MyStack classes by taking in a 
	 * file input.
	 * 
	 * @param args an argument to run main method
	 * @throws Exception is thrown if no file is found
	 */
	public static void main(String[] args) throws Exception {
		File file = new File("customer.txt");
		Scanner s = new Scanner(file);
		int orderNumber = 1;

		while (s.hasNextLine()) {
			String line = s.nextLine();
			System.out.println("Processing Order " +orderNumber+ ": " +line);
			parseLine(line);
			System.out.println();
			orderNumber++;
		}
		
		System.out.println();
		System.out.println();
		
		testMyStack();
		
		System.out.println();
		System.out.println();
		
		testBurger();
	}
	
	/**
	 * parseLine gets a line from the file input and processes it 
	 * to create a Burger
	 * 
	 * @param line is line that is being processed.
	 */
	public static void parseLine(String line) {
		String words[] = line.split(" ");
		Burger burger;

		if (contains(words, "Baron")) {
			burger = new Burger(true);
			if (contains(words, "no") || contains(words, "but") || contains(words, "with")) {
				if (doOmit(words, burger)) {
					// then omit
					if (hasExceptions(words)) {
						// loop until 'but' then omit

						int thePlace = find(words, "no") + 1;
						String toOmit = words[thePlace];

						while (!toOmit.equalsIgnoreCase("but")) {
							if (toOmit.equalsIgnoreCase("Cheese")) {
								burger.removeCategory("Cheese");
							}
							else if (toOmit.equalsIgnoreCase("Veggies")) {
								burger.removeCategory("Veggies");
							}
							else if (toOmit.equalsIgnoreCase("Sauces") || toOmit.equalsIgnoreCase("Sauce")) {
								burger.removeCategory("Sauces");
							} else {
								burger.removeIngredient(toOmit);
							}
							thePlace++;
							toOmit = words[thePlace];
						}
						// apply exceptions
						int butLocation = find(words, "but");
						for (int i = butLocation + 1; i < words.length; i++) {
							burger.addIngredient(words[i]);
						}

					} else {
						// just omit
						int thePlace = find(words, "no") + 1;
						String toOmit = words[thePlace];

						for (int i = 0; i < words.length - thePlace; i++) {
							if (toOmit.equalsIgnoreCase("Cheese")) {
								burger.removeCategory("Cheese");
							}
							else if (toOmit.equalsIgnoreCase("Veggies")) {
								burger.removeCategory("Veggies");
							}
							else if (toOmit.equalsIgnoreCase("Sauces") || toOmit.equalsIgnoreCase("Sauce")) {
								burger.removeCategory("Sauces");
							} else {
								burger.removeIngredient(toOmit);
							}
							thePlace++;
							toOmit = words[thePlace];
						}
						if (toOmit.equalsIgnoreCase("Cheese")) {
							burger.removeCategory("Cheese");
						}
						else if (toOmit.equalsIgnoreCase("Veggies")) {
							burger.removeCategory("Veggies");
						}
						else if (toOmit.equalsIgnoreCase("Sauces") || toOmit.equalsIgnoreCase("Sauce")) {
							burger.removeCategory("Sauces");
						} else {
							burger.removeIngredient(toOmit);
						}
					}
				} else if (doAdd(words, burger)) {
					// add part
					if (hasExceptions(words)) {
						int thePlace = find(words, "with") + 1;
						String toAdd = words[thePlace];

						while (!toAdd.equalsIgnoreCase("but")) {
							if (toAdd.equalsIgnoreCase("Cheese")) {
								burger.addCategory("Cheese");
							}
							else if (toAdd.equalsIgnoreCase("Veggies")) {
								burger.addCategory("Veggies");
							}
							else if (toAdd.equalsIgnoreCase("Sauces") || toAdd.equalsIgnoreCase("Sauce")) {
								burger.addCategory("Sauces");
							} else {
								burger.addIngredient(toAdd);
							}
							thePlace++;
							toAdd = words[thePlace];
						}
						// apply exceptions
						int butLocation = find(words, "no");
						for (int i = butLocation + 1; i < words.length; i++) {
							burger.removeIngredient(words[i]);
						}
					} else {
						// just add
						int thePlace = find(words, "with") + 1;
						String toAdd = words[thePlace];

						for (int i = 0; i < words.length - thePlace; i++) {
							if (toAdd.equalsIgnoreCase("Cheese")) {
								burger.addCategory("Cheese");
							}
							else if (toAdd.equalsIgnoreCase("Veggies")) {
								burger.addCategory("Veggies");
							}
							else if (toAdd.equalsIgnoreCase("Sauces") || toAdd.equalsIgnoreCase("Sauce")) {
								burger.addCategory("Sauces");
							} else {
								burger.addIngredient(toAdd);
							}
							thePlace++;
							toAdd = words[thePlace];
						}
						if (toAdd.equalsIgnoreCase("Cheese")) {
							burger.addCategory("Cheese");
						}
						else if (toAdd.equalsIgnoreCase("Veggies")) {
							burger.addCategory("Veggies");
						}
						else if (toAdd.equalsIgnoreCase("Sauces") || toAdd.equalsIgnoreCase("Sauce")) {
							burger.addCategory("Sauces");
						} else {
							burger.addIngredient(toAdd);
						}
					}


				}
			}
			setPatties(words, burger);
		} else {
			burger = new Burger(false);
			if (contains(words, "no") || contains(words, "with") || contains(words, "but")) {
				if (doOmit(words, burger)) {
					// then omit
					if (hasExceptions(words)) {
						// loop until 'but' then omit

						int thePlace = find(words, "no") + 1;
						String toOmit = words[thePlace];

						while (!toOmit.equalsIgnoreCase("but")) {
							if (toOmit.equalsIgnoreCase("Cheese")) {
								burger.removeCategory("Cheese");
							}
							else if (toOmit.equalsIgnoreCase("Veggies")) {
								burger.removeCategory("Veggies");
							}
							else if (toOmit.equalsIgnoreCase("Sauces") || toOmit.equalsIgnoreCase("Sauce")) {
								burger.removeCategory("Sauces");
							} else {
								burger.removeIngredient(toOmit);
							}
							thePlace++;
							toOmit = words[thePlace];
						}
						// apply exceptions
						int butLocation = find(words, "but");
						for (int i = butLocation + 1; i < words.length; i++) {
							burger.addIngredient(words[i]);
						}

					} else {
						// just omit
						int thePlace = find(words, "no") + 1;
						String toOmit = words[thePlace];

						for (int i = 0; i < words.length - thePlace; i++) {
							if (toOmit.equalsIgnoreCase("Cheese")) {
								burger.removeCategory("Cheese");
							}
							else if (toOmit.equalsIgnoreCase("Veggies")) {
								burger.removeCategory("Veggies");
							}
							else if (toOmit.equalsIgnoreCase("Sauces") || toOmit.equalsIgnoreCase("Sauce")) {
								burger.removeCategory("Sauces");
							} else {
								burger.removeIngredient(toOmit);
							}
							thePlace++;
							toOmit = words[thePlace];
						}
						if (toOmit.equalsIgnoreCase("Cheese")) {
							burger.removeCategory("Cheese");
						}
						else if (toOmit.equalsIgnoreCase("Veggies")) {
							burger.removeCategory("Veggies");
						}
						else if (toOmit.equalsIgnoreCase("Sauces") || toOmit.equalsIgnoreCase("Sauce")) {
							burger.removeCategory("Sauces");
						} else {
							burger.removeIngredient(toOmit);
						}

					}
				} else {
					if (hasExceptions(words)) {
						int thePlace = find(words, "with") + 1;
						String toAdd = words[thePlace];

						while (!toAdd.equalsIgnoreCase("but")) {
							if (toAdd.equalsIgnoreCase("Cheese")) {
								burger.addCategory("Cheese");
							}
							else if (toAdd.equalsIgnoreCase("Veggies")) {
								burger.addCategory("Veggies");
							}
							else if (toAdd.equalsIgnoreCase("Sauces") || toAdd.equalsIgnoreCase("Sauce")) {
								burger.addCategory("Sauces");
							} else {
								burger.addIngredient(toAdd);
							}
							thePlace++;
							toAdd = words[thePlace];
						}
						// apply exceptions
						int butLocation = find(words, "no");
						for (int i = butLocation + 1; i < words.length; i++) {
							burger.removeIngredient(words[i]);
						}
					} else {
						// just add
						int thePlace = find(words, "with") + 1;
						String toAdd = words[thePlace];

						for (int i = 0; i < words.length - thePlace; i++) {
							if (toAdd.equalsIgnoreCase("Cheese")) {
								burger.addCategory("Cheese");
							}
							else if (toAdd.equalsIgnoreCase("Veggies")) {
								burger.addCategory("Veggies");
							}
							else if (toAdd.equalsIgnoreCase("Sauces") || toAdd.equalsIgnoreCase("Sauce")) {
								burger.addCategory("Sauces");
							} else {
								burger.addIngredient(toAdd);
							}
							thePlace++;
							toAdd = words[thePlace];
						}
						if (toAdd.equalsIgnoreCase("Cheese")) {
							burger.addCategory("Cheese");
						}
						else if (toAdd.equalsIgnoreCase("Veggies")) {
							burger.addCategory("Veggies");
						}
						else if (toAdd.equalsIgnoreCase("Sauces") || toAdd.equalsIgnoreCase("Sauce")) {
							burger.addCategory("Sauces");
						} else {
							burger.addIngredient(toAdd);
						}
					}
				}
			}
			setPatties(words, burger);
		}
		System.out.println(burger);
	}

	/**
	 * hasExceptions is a helper method that checks if a String[] has 
	 * any exceptions to its omisions or additions.
	 * 
	 * @param words is the line that is turned to String[]
	 * @return true if there are exceptions. False otherwise.
	 */
	private static boolean hasExceptions(String[] words) {
		return contains(words, "but");
	}

	/**
	 * doOmit is a helper method that checks if a String[] indicates that
	 * there need to be omission.
	 * 
	 * @param words is the line that is turned to String[]
	 * @param burger is the Burger instance that the user stores the created Burgers
	 * @return true if there needs to be omission. False otherwise.
	 */
	private static boolean doOmit(String[] words, Burger burger) {
		for (int i = 1; i < words.length; i++) {
			String first = words[i - 1];
			String second = words[i];

			if (first.equalsIgnoreCase("with") && second.equalsIgnoreCase("no")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * doAdd is a helper method that checks if there needs to be additions 
	 * 
	 * @param words is the line that is turned to String[]
	 * @param burger is the Burger instance that the user stores the created Burgers
	 * @return true if there needs to be additions. False otherwise.
	 */
	private static boolean doAdd(String[] words, Burger burger) {
		for (int i = 1; i < words.length; i++) {
			String first = words[i - 0];
			String second = words[i];

			if (first.equalsIgnoreCase("with") && (!second.equalsIgnoreCase("no"))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * setPatties is a helper method that adds the right amount of correct patty type.
	 * 
	 * @param words is the line that is turned to String[]
	 * @param burger is the Burger instance that the user stores the created Burgers
	 */
	private static void setPatties(String[] words, Burger burger) {
		int pNum = 0;
		String pType = "Beef";
		if (contains(words, "Double") || contains(words, "double")
				|| contains(words, "Two") || contains(words, "two")
				|| contains(words, "2")) {
			pNum = 1;
		}
		if (contains(words, "Triple") || contains(words, "triple")
				|| contains(words, "Three") || contains(words, "three")
				|| contains(words, "3")) {
			pNum = 2;
		}
		if (contains(words, "Chicken")) {
			pType = "Chicken";
		}
		if (contains(words, "Veggie")) {
			pType = "Veggie";
		}

		for (int i = 0; i < pNum; i++) {
			burger.addPatty();
		}
		burger.changePatties(pType);

	}

	/**
	 * find is a private helper method that find the index of an element
	 * from a given String[]
	 * 
	 * @param a is the String[]
	 * @param s is the String being tracked to find the index to.
	 * @return the index
	 */
	private static int find(String[] a, String s) {
		int result = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i].equalsIgnoreCase(s)) {
				result = i;
			}
		}
		return result;
	}

	/**
	 * contains is a helper method that checks if a String is in a certain
	 * String[]
	 * 
	 * @param a is the String[]
	 * @param s is the String that the user is tracking
	 * @return true if s is in a
	 */
	private static boolean contains(String[] a, String s) {
		for (int i = 0; i < a.length; i++) {
			if (a[i].equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * testMyStack is a method that tests all the methods 
	 * of MyStack class.
	 */
	public static void testMyStack() {
		MyStack s = new MyStack();
		s.push("Bottom Bun");
		s.push("Ketchup");
		s.push("Beef");
		s.push("Mozzarella ");
		s.push("Onions");
		s.push("Lettuce");
		s.push("Mayonnaise");
		s.push("Top Bun");
		
		System.out.println("Let's see Orinal Stack using toString method");
		System.out.println(s.toString());
		System.out.println();
		System.out.println("Let's add a pickle on top using the push method");
		s.push("Pickle");
		System.out.println(s.toString());
		System.out.println();
		System.out.println("Let's look at what's on top using peek method");
		System.out.println(s.peek());
		System.out.println();
		System.out.println("Let's remove the pickle using the pop method");
		System.out.println("Removed Ingredient: "+s.pop());
		System.out.println(s.toString());
		System.out.println();
		System.out.println("Let's see if our stack is empty using the isEmpty method");
		System.out.println(s.isEmpty());
		System.out.println();
		System.out.println("Lets see the size of our stack using the size method");
		System.out.println(s.size());
		
	}

	/**
	 * testBurger is a method that tests all the methods 
	 * of the Burger class
	 */
	public static void testBurger() {
		Burger b = new Burger(true);
		
		System.out.println("Create a Baron Burger using constructor. Print using toString");
		System.out.println(b.toString());
		System.out.println();
		System.out.println("Add a patty (Beef as default) using addPatty");
		b.addPatty();
		System.out.println(b);
		System.out.println();
		System.out.println("Change the Beef patties to Chicken using changePatties");
		b.changePatties("Chicken");
		System.out.println(b);
		System.out.println();
		System.out.println("Remove Cheese using removeCategory");
		b.removeCategory("Cheese");
		System.out.println(b);
		System.out.println();
		System.out.println("Remove Pickle using removeIngredient");
		b.removeIngredient("Pickle");
		System.out.println(b);
		System.out.println();
		System.out.println("Add the Cheese Category using addCategory");
		b.addCategory("Cheese");
		System.out.println(b);
		System.out.println();
		System.out.println("Add Pickle using addIngredient");
		b.addIngredient("Pickle");
		System.out.println(b);
		System.out.println("---------------------------------------------------------------");
		
		Burger b2 = new Burger(false);
		
		System.out.println();
		System.out.println("Create a regular Burger using constructor. Print using toString");
		System.out.println(b2.toString());
		System.out.println();
		System.out.println("Add a patty (Beef as default) using addPatty");
		b2.addPatty();
		System.out.println(b2);
		System.out.println();
		System.out.println("Change the Beef patties to Chicken using changePatties");
		b2.changePatties("Chicken");
		System.out.println(b2);
		System.out.println();
		System.out.println("Remove Cheese using removeCategory");
		b2.removeCategory("Cheese");
		System.out.println(b2);
		System.out.println();
		System.out.println("Remove Pickle using removeIngredient");
		b2.removeIngredient("Pickle");
		System.out.println(b2);
		System.out.println();
		System.out.println("Add the Cheese Category using addCategory");
		b2.addCategory("Cheese");
		System.out.println(b2);
		System.out.println();
		System.out.println("Add Pickle using addIngredient");
		b2.addIngredient("Pickle");
		System.out.println(b2);
	}
}