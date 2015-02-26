import java.io.*;
import java.util.*;


public class Frequency {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Sagar.Sagar-PC\\workspace\\NetSec\\src\\sagar.txt"));
        String data;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>(); //create a HashMap 
        
        try {
			while(( data=br.readLine())!=null){ // traverse the text file till the end
				char arr[]=data.toCharArray(); // parse the string to a char array
				for(char c:arr){
					if(map.get(c)==null){  // first occurence of the character is encountered
						map.put(c, 1);     // put in the HashMap and set the value to 1
					}
					else{
						int counter=map.get(c);  // get the value of that particular character and save it in a counter variable
						map.put(c, ++counter);   // increment the value of the counter
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for(Map.Entry aa:map.entrySet()){ // printing the HashMap
        	System.out.print("key :"+aa.getKey());
        	System.out.println("\t Value :"+aa.getValue());
        }
        


	}

}
