Baristamatic Assignment

Name - Sagar Thakur 
Date - 03/09/2015

How to run: 

- Use any IDE such as Eclipse and make sure to keep both the java files in one directory.
- Run the BaristamaticApp.java to run the program
- You can also compile using the command prompt by using the following commands:
		>javac BaristamaticApp.java
		>java BaristamaticApp
- The output is attached in the file "BaristamaticApp - output.txt"

Approach:

- My approach to this assignment is very simple and straight-forward.
- I have created two classes Inventory and Baristamatic.
- The Inventory class has the data structures which holds the information.
  about the inventory of the drinks, ingredients and the number of units
  which are available.
- I have used LinkedHashMap which preserves the ordering by the keys as they are put in the map.
- There are functions to generate the inventory, print the inventory, check if 
  the stock is available or not, display out of stock drinks and reduce the inventory  when the drinks are dispensed.
- The BaristamaticApp class has the main method which calls the methods from the inventory class. 
- It also has an input method which checks if the input is a valid input or not, otherwise it displays a message as required.
- If a valid input is encountered, it performs the necessary steps which are to dispense the drink if all the ingredients are in stock, print out of stock message if any one of the ingredients for that drink are out of stock, refill the inventory if the user requests to do that.