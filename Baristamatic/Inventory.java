import java.util.ArrayList;
import java.util.LinkedHashMap;

/*
 * This class holds all the data structures required to store the ingredients, costs and drinks.
 * I have used a LinkedHashMap to preserve the ordering(by key) of drinks when entered in the map. 
 * ingredients stores the ingredient name and the units available.
 * cost stores the ingredient name and the unit cost.(This can be used in future if we want to find out the total amount of drinks
 * dispensed or for profit calculations or for report generations)
 * drinks stores the drink number as the key. This drink number is also the index of the drinkMenu ArrayList which
 * maps the index to the drink name. The drinks map stores the cost of drink as the value.
 */
public class Inventory {
	LinkedHashMap<String, Integer> ingredients = new LinkedHashMap<String, Integer>();
	LinkedHashMap<String, Double> costs = new LinkedHashMap<String, Double>();
	LinkedHashMap<Integer, Double> drinks = new LinkedHashMap<Integer, Double>();
	ArrayList<String> drinkMenu = new ArrayList<String>();
	boolean isAvailable = true;
		
	private static Inventory instance = null; 

	private Inventory(){} 
	//Singleton
	public static Inventory getInstance(){ 
	if(instance == null){ 
	instance = new Inventory(); 
	} 
	return instance; 
	} 
	
	/*
	 * This function will generate the inventory and the ingredients with max available units.
	 */
	void generateInventory(){
		ingredients.put("Cocoa", 10);
		ingredients.put("Coffee", 10);
		ingredients.put("Cream", 10);
		ingredients.put("Decaf Coffee", 10);
		ingredients.put("Espresso", 10);
		ingredients.put("Foamed Milk", 10);
		ingredients.put("Steamed Milk", 10);
		ingredients.put("Sugar", 10);
		ingredients.put("Whipped Cream", 10);
		costs.put("Coffee", 0.75);
		costs.put("Decaf Coffee", 0.75);
		costs.put("Sugar", 0.25);
		costs.put("Cream", 0.25);
		costs.put("Steamed Milk", 0.35);
		costs.put("Foamed Milk", 0.35);
		costs.put("Espresso", 1.10);
		costs.put("Cocoa", 0.90);
		costs.put("Whipped Cream", 1.00);	
		drinkMenu.add("Caffe Americano");
		drinkMenu.add("Caffe Latte");
		drinkMenu.add("Caffe Mocha");
		drinkMenu.add("Cappuccino");
		drinkMenu.add("Coffee");
		drinkMenu.add("Decaf Coffee");
		drinks.put(1, 3.30);
		drinks.put(2, 2.55);
		drinks.put(3, 3.35);
		drinks.put(4, 2.90);
		drinks.put(5, 2.75);
		drinks.put(6, 2.75);
	}
	
	/*
	 * Function for printing the inventory and the Menu.
	 */
	public void printInventory(){
		System.out.println("Inventory:");
		for(String ingredient : ingredients.keySet()){
			System.out.println(ingredient + "," + ingredients.get(ingredient));
		}
		System.out.println("Menu:");
		for(Integer drink : drinks.keySet()){
			System.out.println(drink + "," + drinkMenu.get(drink-1) + ", $" + drinks.get(drink) + "," + checkStock(drink));
		}
		
	}
	
	/*
	 * This function takes the drinkNo as the parameter and checks if the ingredients are in stock or not.
	 * Returns a boolean.
	 */
	boolean checkStock(int drinkNo){
		switch(drinkNo){
		case 1:	if(ingredients.get("Espresso") < 3){
					return false;
				}
				break;
		case 2: if(ingredients.get("Espresso") < 2 || ingredients.get("Steamed Milk") < 1){
					return false;
				}
				break;
		case 3: if(ingredients.get("Espresso") < 1 || ingredients.get("Steamed Milk") < 1 || ingredients.get("Cocoa") < 1 ||ingredients.get("Whipped Cream") < 1){
					return false;
				}
				break;
		case 4: if(ingredients.get("Espresso") < 2 || ingredients.get("Steamed Milk") < 1 || ingredients.get("Foamed Milk") < 1 ){
					return false;
				}
				break;
		case 5: if(ingredients.get("Coffee") < 3 || ingredients.get("Sugar") < 1 || ingredients.get("Cream") < 1 ){
					return false;
				}
				break;
		case 6: if(ingredients.get("Decaf Coffee") < 3 || ingredients.get("Sugar") < 1 || ingredients.get("Cream") < 1 ){
					return false;
				}
				break;
		
		}
		return true;
	}
/*
 * This function takes the drinkNo as the parameter and displays the out of stock message for that drink and calls the method
 * which will print the inventory and the menu.
 */
	public void displayOutOfStock(int drinkNo) {
		// TODO Auto-generated method stub
		System.out.println("Out of stock: " + drinkMenu.get(drinkNo-1));
		printInventory();
	}
	/*
	 * This function takes the drinkNo as the parameter and reduces the inventory when that drink is dispensed.
	 */
	public void reduceInventory(int drinkNo){
		switch(drinkNo){
		case 1: {
				reduceIngredients("Espresso", 3);
				printInventory();
				break;
				}
		case 2: {
					reduceIngredients("Espresso", 2);
					reduceIngredients("Steamed Milk", 1);
					printInventory();
					break;
				}
		case 3: {
					reduceIngredients("Espresso", 1);
					reduceIngredients("Cocoa", 1);
					reduceIngredients("Steamed Milk", 1);
					reduceIngredients("Whipped Cream", 1);
					printInventory();
					break;
				}
		case 4: {
					reduceIngredients("Espresso", 2);
					reduceIngredients("Steamed Milk", 1);
					reduceIngredients("Foamed Milk", 1);
					printInventory();
					break;
				}
		case 5: {
					reduceIngredients("Coffee", 3);
					reduceIngredients("Sugar", 1);
					reduceIngredients("Cream", 1);
					printInventory();
					break;
				}
		case 6: {
					reduceIngredients("Decaf Coffee", 3);
					reduceIngredients("Sugar", 1);
					reduceIngredients("Cream", 1);
					printInventory();
					break;
				}
		}
	}
	
	/*
	 * This function takes two parameters which are the ingredient name and the units by which the inventory of that ingredient
	 * is to be reduced.
	 */

	private void reduceIngredients(String ingredient, int units) {
		// TODO Auto-generated method stub
		int value = ingredients.get(ingredient);
		value -= units;
		ingredients.put(ingredient, value);		
	}
	

}

