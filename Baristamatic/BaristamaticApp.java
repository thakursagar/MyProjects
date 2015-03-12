import java.util.Scanner;

/*
 * This class is the main class which handles the input using an input() function.
 * It also has the object of the Inventory class which is used to generate and print the inventory.
 */

public class BaristamaticApp {
	static Scanner sc = new Scanner(System.in);
	static Inventory inventory = Inventory.getInstance();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		inventory.generateInventory();
		inventory.printInventory();
		
		input();
		
	}
	/*
	 * This function takes care of all the input data and displays messages if an unacceptable input is encountered.
	 * It calls the dispense method when a drink is to be dispensed. It calls the methods in the inventory class when the 
	 * inventory is to be re-filled.
	 */
	private static void input(){
		String input = sc.next();
		if(input.equalsIgnoreCase("Q"))
			System.exit(1);
		else if(input.equalsIgnoreCase("R")){
			inventory.generateInventory();
			inventory.printInventory();
				input();
			}
		else if(input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5") || input.equals("6")){
			
			dispense(input);
		}
			else{ 
				System.out.println("Invalid selection: " + input);
				inventory.printInventory();
				input();
		}
	}
	
	/*
	 * This function takes input which is the drink number and checks if the ingredients required to make that drink are in stock or not.
	 * If they are in stock then it dispenses the drink and reduces the ingredients from the inventory by the number of units
	 * required to make that drink. On the other hand if the ingredients are not in stock, it displays the appropriate message.
	 */
	private static void dispense(String input){
		if(inventory.checkStock(Integer.parseInt(input))){
			System.out.println("Dispensing: " + inventory.drinkMenu.get(Integer.parseInt(input)-1) );
			inventory.reduceInventory(Integer.parseInt(input));
			input();
		}
		else{
			inventory.displayOutOfStock(Integer.parseInt(input));
			input();
		}
		
	}
	
	
	

}
