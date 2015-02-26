import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
//Name - Sagar Thakur
//University - Indiana University Bloomington
//Email - srthakur@indiana.edu / thakursagar@gmail.com
//Phone - +1 219-448-0231 
// Node class which stores destination and pathCost/time
class Node{
	String destination;
	int pathCost;
}


public class FlightCalculations {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	
	static HashSet<String> printSolution = new HashSet<String>();
	static ArrayList<ArrayList<Node>> finalList = new ArrayList<ArrayList<Node>>();
	
	//Checks if the time stored in the hashmap is less than the time which we are evaluating right now and returns the minimum node name.
	static String returnMinimum(ArrayList<String> list, HashMap<String,Node> map){
		if(list.size()==0){
			return null;
		}
		String tempString = list.get(0);
		Node tempNode = map.get(tempString);
		int minCost = tempNode.pathCost;
		int currentCost;
		int pos=0, posDelete=0;
		for(String s:list){
			Node getCost = map.get(s);
			currentCost = getCost.pathCost;
			if(currentCost < minCost){
				minCost = currentCost;
				tempString = s;
				posDelete=pos;
			}
			pos++;
		}
		
			list.remove(posDelete);
		return tempString;
		
	}
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
	
		
		HashMap<String, ArrayList<Node>> graph = new HashMap<String, ArrayList<Node>>();
		//Path of the Input file flights.txt
		Scanner sc = new Scanner(new File("E:\\Orbitz\\orbitzworldwidecodingtestwattachments\\flights.txt"));
		String source, dest, time;
		// Below statements prepare the Hashmap of String and Arraylist of Node from the input file.
		while(sc.hasNextLine()){
			String line = sc.nextLine(); 
			String linearr[] = line.split(",");
			int timeDuration = Integer.parseInt(linearr[2]);
			Node node = new Node();
			node.destination = linearr[1];
			node.pathCost = timeDuration;
			
			if(graph.containsKey(linearr[0]))
			{
				ArrayList<Node> value = graph.get(linearr[0]);
				value.add(node);
			}
			else{
				ArrayList<Node> list = new ArrayList<Node>();
				list.add(node);
				graph.put(linearr[0], list);
				
			}
		}
		//Path of tasks file
		sc = new Scanner(new File("E:\\Orbitz\\orbitzworldwidecodingtestwattachments\\tasks.txt"));
		String line1 = sc.nextLine();
		String line1arr[] = line1.split(",");
		source = line1arr[0];
		dest = line1arr[1];
		String line2 = sc.nextLine();
		String line2arr[] = line2.split(",");
		String sourceTask2 = line2arr[0];
		int depthTask2 = Integer.parseInt(line2arr[1]);
		depthTask2--;
		String sourceTask3=sc.nextLine();
		HashSet<String> visited = new HashSet<String>();
		HashMap<String,Node> nodeData = new HashMap<String, Node>();
		ArrayList<String> keepSorted = new ArrayList<String>();
		int minCost=1000;
		Node temp = new Node();
		temp.destination = null;
		temp.pathCost = 0;
		nodeData.put(source, temp);
		String tempDestination = source;
		String actualDestination = dest;
		
		//Calculates Shortest Path from Node A to Node B using Dijkstra's Shortest Path Algorithm Strategy
		while(!tempDestination.equals(actualDestination) && tempDestination!=null){
			if(visited.contains(tempDestination)){
				tempDestination = returnMinimum(keepSorted, nodeData);
				continue;
			}	
			ArrayList<Node> getSourceValue = graph.get(tempDestination);
			for(Node n : getSourceValue){
				if(nodeData.containsKey(n.destination))
				{
					int pc=0;
					Node valueEdge = nodeData.get(n.destination);
					if(nodeData.containsKey(tempDestination)){
						Node getParentCost = nodeData.get(tempDestination); 
						pc = n.pathCost + getParentCost.pathCost; 
					}
					if(pc!=0 && valueEdge.pathCost > pc)
							valueEdge.pathCost = pc;
					
						
				}
				else{
					Node tempNode = new Node();
					tempNode.destination = tempDestination;
					tempNode.pathCost = n.pathCost;
					if(nodeData.containsKey(tempDestination)){
						Node getParentCost = nodeData.get(tempDestination); 
						tempNode.pathCost += getParentCost.pathCost; 
					}
						
					nodeData.put(n.destination, tempNode);
					keepSorted.add(n.destination);
					
					
				}
			
			
		}
		visited.add(tempDestination);
		tempDestination = returnMinimum(keepSorted, nodeData);

		}
		
		Node print = nodeData.get(actualDestination);
		ArrayList<String> printList = new ArrayList<String>();
		printList.add(print.destination);
		
		while(print.destination!=null){
			print = nodeData.get(print.destination);
			printList.add(print.destination);
		}
		Collections.reverse(printList);
		printList.remove(0);
		printList.add(dest);
		//Path of the solution file where results are to be written
		PrintStream out = new PrintStream(new FileOutputStream("E:\\Orbitz\\orbitzworldwidecodingtestwattachments\\solution.txt"));
		printToSolution(printList, out);
		//System.out.println("The Shortest path from " + source + " to " + dest + " is: " + printList);
		sc.close();
		//Function call for for task2
		listAllAirports(sourceTask2, depthTask2,  graph);
		//To sort the HashSet, I converted it to an ArrayList first. Then I sorted the ArrayList to print in alphabetical order.
		//The reason for using a HashSet was to avoid duplicates.
		ArrayList<String> sortList = new ArrayList<String>(printSolution);
		Collections.sort(sortList); //To sort the list
		//Function call to print the answer in solution.txt file
		printToSolution(sortList, out);
		//Function call for task3
		longestRoundTrip(sourceTask3, graph);
		//Used for debugging. Printing all paths for round trip flight.
		/*for(ArrayList<Node> n : finalList){
			for(Node p : n){
				System.out.println("All paths from source to source are: " + p.destination);
			}
		}
		*/
		//Checking the longest round trip route
		int max=0,newmax=0,index=0,cnt=0;
		for(ArrayList<Node> n : finalList){
			newmax=0;
			for(Node p : n){
				newmax += p.pathCost;
			}
			if(newmax > max ){
				max = newmax;
				index = cnt;
			}
			cnt++;
		}
		ArrayList<Node> toPrint = finalList.get(index);
		ArrayList<String> giveSolution = new ArrayList<String>();
		giveSolution.add(sourceTask3);
		for(Node n : toPrint){
			giveSolution.add(n.destination);
			//System.out.print(n.destination + " ");
		}
		printToSolution(giveSolution, out);
	}
	//A recursive function which calculates how many airports can be reached by using exactly n flights from a source
	 static void listAllAirports(String sourceTask2, int depthTask2, HashMap<String, ArrayList<Node>> map){
		ArrayList<Node> print = map.get(sourceTask2);
		if(depthTask2 ==0){
			if(print!=null){
				for(Node n : print){
				printSolution.add(n.destination);
				//System.out.print(n.destination + " ");
			}
			}
			return;
		}
		for(Node n : print){
			listAllAirports(n.destination, depthTask2-1, map);
		}
		}
	 //This function calls a recursive function which calculates all possible round trip paths from a source
	 static void longestRoundTrip(String sourceTask3, HashMap<String, ArrayList<Node>> map){
		 ArrayList<Node> getEdge = map.get(sourceTask3);
		 for(Node n : getEdge){
			 ArrayList<Node> tempList = new ArrayList<Node>();
			 tempList.add(n);
			 getEdges(n.destination, n.pathCost, sourceTask3, map, tempList);
		 }
	 }
	 //A recursive function which will calculate all possible round trip paths from a source excluding the duplicate airports.
	 static void getEdges(String getChildren, int cost, String sourceTask3, HashMap<String,ArrayList<Node>> map, ArrayList<Node> tempList ){
		 if(getChildren.equals(sourceTask3))
		 {
			 finalList.add((ArrayList<Node>) tempList.clone());
			 tempList.remove(tempList.size()-1);
			 return;
		 }
		 ArrayList<Node> keepOnFinding = map.get(getChildren);
		 for(Node n : keepOnFinding){
			 boolean flag = false;
			 for(Node x : tempList){
				 if(x.destination.equals(n.destination)){
					 flag = true;
					 break;
				 }
			 }
			 if(flag == false){
				 tempList.add(n);
			 	 getEdges(n.destination, n.pathCost, sourceTask3, map, tempList);
			 }
		 }
		 tempList.remove(tempList.size()-1);
	 }
	 //This function prints the result which is an arraylist to the solution.txt file
	 static void printToSolution(ArrayList<String> list, PrintStream out){
		 for(int i =0; i<list.size()-1;i++)
			{
				out.print(list.get(i)+ ",");
			}
			out.print(list.get(list.size()-1)+ " ");
			out.println();
	 }
	}
	
	


