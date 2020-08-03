/*
 * TCSS 342 – Spring 2020
 * Assignment 1 – Baron Burger  
 */

/**
 * Burger is a class that represents a burger with many different
 * ingredients
 * 
 * @author Hermie Baylon
 * @version 12 April 2020
 */
public class Burger {
	
	/** Represents the burger */
	private MyStack myBurger;
	
	/** Represents how many patties at maximum can be in a burger */
	public static final int MAX_PATTY = 3;
	
	/** Represents how many patties there are currently */
	private int pattyCount;


	/**
	 * Burger is the contructor that initializes a Baron burger or a 
	 * regular burger.
	 * 
	 * @param theWorks is the boolean data that determines if a Burger 
	 * will be a Baron burger or regular. Initialize pattyCount to 1.
	 */
	public Burger(boolean theWorks) {
		MyStack burger = new MyStack();
		if (!theWorks) {
			burger.push("Bottom Bun");
			burger.push("Beef");
			burger.push("Top Bun");
		} else{
			createBurgerBarron(burger);
		}
		myBurger = burger;
		pattyCount = 1;
	}

	/**
	 * changePatties changes all the patties in the burger to a desire
	 * patty type.
	 * 
	 * @param pattyType is the desired patty type
	 */
	public void changePatties(String pattyType) {
		MyStack temp = new MyStack();

		while (myBurger.size() != 0) {
			String top = (String) myBurger.peek();
			if (top.equals("Beef") || top.equals("Chicken") || top.equals("Veggie")) {
				myBurger.pop();
				temp.push(pattyType);
			} else {
				String ingredient = (String) myBurger.pop();
				temp.push(ingredient);
			}
		}
		while (temp.size() != 0) {
			String ingredient = (String) temp.pop();
			myBurger.push(ingredient);
		}
	}

	/**
	 * addPatty adds a beef patty to the burger.
	 */
	public void addPatty() {
		MyStack newBurger = new MyStack();
		if (pattyCount < MAX_PATTY) {
			while (myBurger.size() != 0) {
				String top = (String) myBurger.peek();
				if (pattyCount > 0) {
					if (top.equals("Cheddar") || top.equals("Mozzarella") || top.equals("Pepperjack")	
							|| top.equals("Beef") || top.equals("Chicken") || top.equals("Veggie")) {	
						newBurger.push("Beef");											
						String ingredient = (String) myBurger.pop();					
						newBurger.push(ingredient);										
						break;														 	
					} 																	
					String ingredient = (String) myBurger.pop();						
					newBurger.push(ingredient);											
				} else if (pattyCount == 0) {
					if (top.equals("Mushrooms") || top.equals("Mustard") || top.equals("Ketchup")
							|| top.equals("Bottom Bun")) {
						newBurger.push("Beef");
						String ingredient = (String) myBurger.pop();
						newBurger.push(ingredient);
						break;
					} 
					String ingredient = (String) myBurger.pop();
					newBurger.push(ingredient);
				}
			}
			while (myBurger.size() != 0) {
				String ingredient = (String) myBurger.pop();
				newBurger.push(ingredient);
			}
			while (newBurger.size() != 0) {
				String ingredient = (String) newBurger.pop();
				myBurger.push(ingredient);
			}
			pattyCount++;
		} else {
			System.out.println("cant add anymore patties");
		}
	}

	/**
	 * addCategory adds an entire category of ingredients to the burger
	 * 
	 * @param type is the desired category type
	 */
	public void addCategory(String type) {
		if (type.equals("Cheese")) {
			addIngredient("Pepperjack");
			addIngredient("Mozzarella");
			addIngredient("Cheddar");
		}
		if (type.equals("Sauces")) {
			addIngredient("Mayonnaise");
			addIngredient("Baron Sauce");
			addIngredient("Mustard");  
			addIngredient("Ketchup");
		}
		if (type.equals("Veggies")) {
			addIngredient("Pickle");
			addIngredient("Lettuce");
			addIngredient("Tomato");
			addIngredient("Onions");  
			addIngredient("Mushrooms");
		}
	}

	/**
	 * removeCategory removes an entire category from the burger
	 * 
	 * @param type is the desired category type
	 */
	public void removeCategory(String type) {
		if (type.equals("Sauces")) {
			this.removeIngredient("Ketchup");
			this.removeIngredient("Mustard");
			this.removeIngredient("Mayonnaise");
			this.removeIngredient("Baron-Sauce");
		} else if (type.equals("Cheese")) {
			this.removeIngredient("Cheddar");
			this.removeIngredient("Mozzarella");
			this.removeIngredient("Pepperjack");
		} else if (type.equals("Veggies")) {
			this.removeIngredient("Lettuce");
			this.removeIngredient("Tomato");
			this.removeIngredient("Onions");
			this.removeIngredient("Pickle");
			this.removeIngredient("Mushrooms");
		}
	}

	/**
	 * addIngredient adds an ingredient to the burger
	 * 
	 * @param type is the type of ingredient the user want to add
	 */
	public void addIngredient(String type) {
		Burger fullBurger1 = new Burger(true);
		Burger fullBurger2 = new Burger(true);

		fullBurger1.removeIngredient("Beef");
		fullBurger2.removeIngredient("Beef");

		int howManyBeef = this.howMany("Beef");
		int howManyChicken = this.howMany("Chicken");
		int howManyVeggie = this.howMany("Veggie");

		if (this.has("Beef")) {
			for (int i = 0; i < howManyBeef; i++) {
				fullBurger1.addPatty();
				fullBurger2.addPatty();
			}
		}
		if (this.has("Chicken")) {
			for (int i = 0; i < howManyChicken; i++) {
				fullBurger1.addGeneralPatty("Chicken");
				fullBurger2.addGeneralPatty("Chicken");
			}
		}
		if (this.has("Veggie")) {
			for (int i = 0; i < howManyVeggie; i++) {
				fullBurger1.addGeneralPatty("Veggie");
				fullBurger2.addGeneralPatty("Veggie");
			}
		}
		myBurger.push(type);	
		while (myBurger.size() != 0) {
			String top = (String) myBurger.peek();
			fullBurger1.removeIngredient(top);
			this.removeIngredient(top);
		}

		while (fullBurger1.myBurger.size() != 0) {
			String top = (String) fullBurger1.myBurger.peek();
			fullBurger2.removeIngredient(top);
			fullBurger1.removeIngredient(top);
		}
		int totalPatties = howManyBeef + howManyChicken + howManyVeggie;
		myBurger = fullBurger2.myBurger;
		pattyCount = pattyCount + totalPatties;
		if (type.equals("Beef") || type.equals("Chicken") || type.equals("Veggie")) {
			pattyCount++;
		}
	}

	/**
	 * removeIngredient removes an ingredient from the burger
	 * 
	 * @param type is the type of ingredient the user wants to remove
	 */
	public void removeIngredient(String type) {
		MyStack newBurger = new MyStack();
		while (myBurger.size() != 0) {
			if (myBurger.peek().equals(type)) {
				myBurger.pop();
				break;
			} else {
				String ingredient = (String) myBurger.pop();
				newBurger.push(ingredient);
			}
		}
		while (newBurger.size() != 0) {
			String ingredient = (String) newBurger.pop();
			myBurger.push(ingredient);
		}
		if (type.equals("Beef") || type.equals("Chicken") || type.equals("Veggie")) {
			pattyCount--;
		}
	}

	/**
	 * String returns a string representation of the Burger
	 */
	public String toString() {
		String result = myBurger.toString();
		return result;
	}

	/**
	 * createBurgerBarron is a helper method that creates a Burger
	 *  with all the ingredients from a Baron Burger
	 * 
	 * @param s is the MyStack instance that adds the ingredients
	 */
	private void createBurgerBarron(MyStack s) {
		s.push("Bottom Bun");
		s.push("Ketchup");
		s.push("Mustard");
		s.push("Mushrooms");
		s.push("Beef");
		s.push("Cheddar");
		s.push("Mozzarella");
		s.push("Pepperjack");
		s.push("Onions");
		s.push("Tomato");
		s.push("Lettuce");
		s.push("Baron-Sauce");
		s.push("Mayonnaise");
		s.push("Top Bun");
		s.push("Pickle");
	}

	/**
	 * has is a helper method that tells user if the Burger has a certain
	 * ingredient
	 * 
	 * @param s is the ingredient the user is checking
	 * @return true if a burger has s
	 */
	private boolean has(String s) {
		boolean result = false;
		MyStack newBurger = new MyStack();
		while (myBurger.size() != 0) {
			if (myBurger.peek().equals(s)) {
				result = true;
				break;
			} else {
				newBurger.push(myBurger.pop());
			}
		}
		while (newBurger.size() != 0) {
			myBurger.push(newBurger.pop());
		}
		return result;
	}

	/**
	 * howMany is a private method that counts how many of the same 
	 * certain ingredient are there in the burger
	 *  
	 * @param s is the ingredient
	 * @return the count for s
	 */
	private int howMany(String s) {
		int count = 0;
		MyStack newBurger = new MyStack();
		while (myBurger.size() != 0) {
			if (myBurger.peek().equals(s)) {
				count++;
				newBurger.push(myBurger.pop());
			} else {
				newBurger.push(myBurger.pop());
			}
		}
		while (newBurger.size() != 0) {
			myBurger.push(newBurger.pop());
		}
		return count;
	}

	/**
	 * addGeneralPatty is a helper method that adds any desired
	 * patty
	 * 
	 * @param s is the desired patty type
	 */
	private void addGeneralPatty(String s) {
		MyStack newBurger = new MyStack();
		if (pattyCount < MAX_PATTY) {
			while (myBurger.size() != 0) {
				String top = (String) myBurger.peek();
				if (pattyCount > 0) {
					if (top.equals("Cheddar") || top.equals("Mozzarella") || top.equals("Pepperjack")	
							|| top.equals("Beef") || top.equals("Chicken") || top.equals("Veggie")) {	
						if (s.equals("Beef")) {
							newBurger.push("Beef");											
							String ingredient = (String) myBurger.pop();					
							newBurger.push(ingredient);										
							break;
						}
						if (s.equals("Chicken")) {
							newBurger.push("Chicken");											
							String ingredient = (String) myBurger.pop();					
							newBurger.push(ingredient);										
							break;
						}
						if (s.equals("Veggie")) {
							newBurger.push("Veggie");											
							String ingredient = (String) myBurger.pop();					
							newBurger.push(ingredient);										
							break;
						}
					} 																	
					String ingredient = (String) myBurger.pop();						
					newBurger.push(ingredient);											
				} else if (pattyCount == 0) {
					if (top.equals("Mushrooms") || top.equals("Mustard") || top.equals("Ketchup")
							|| top.equals("Bottom Bun")) {
						if (s.equals("Beef")) {
							newBurger.push("Beef");
							String ingredient = (String) myBurger.pop();
							newBurger.push(ingredient);
							break;
						} if (s.equals("Chicken")) {
							newBurger.push("Chicken");
							String ingredient = (String) myBurger.pop();
							newBurger.push(ingredient);
							break;
						} if (s.equals("Veggie")) {
							newBurger.push("Veggie");
							String ingredient = (String) myBurger.pop();
							newBurger.push(ingredient);
							break;
						}
					} 
					String ingredient = (String) myBurger.pop();
					newBurger.push(ingredient);
				}
			}
			while (myBurger.size() != 0) {
				String ingredient = (String) myBurger.pop();
				newBurger.push(ingredient);
			}
			while (newBurger.size() != 0) {
				String ingredient = (String) newBurger.pop();
				myBurger.push(ingredient);
			}
			pattyCount++;
		} else {
			System.out.println("cant add anymore patties");
		}
	}
}
