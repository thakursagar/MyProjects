Name - Sagar Thakur
Email - srthakur@indiana.edu / thakursagar@gmail.com
Phone Number - +1 219-448-0231
School - Indiana University Bloomington

1. How to compile my program?

To compile the program, you have to open any editor which can compile and run java programs(For eg:- Eclipse) and open the FlightCalculations.java file.
Give the appropriate path for the flights.txt, tasks.txt and solutions.txt file in the java file and run it. 
The solution.txt file will contain the answers to the tasks.

2 - Brief description of my implementation.

Storing the graph:
For storing the graph, I scan the input file flights.txt and store all the information in a HashMap<String, ArrayList<Node>>. The key is the source and the ArrayList of Node will contain the destination as a String and the time or the pathCost.
The reason for choosing HashMap as the datastructure is that HashMap has very good access time of O(1). 
Task 1:
For Task 1, I use the Dijkstra's Algorithm strategy and find the quickest flight path between the source and the destination.
For doing this task, I used a HashSet which will store the visited nodes, a HashMap<String,Node> which will store the information of the previous node's 
path from source in the Node as pathCost and the destination here is the parent of that node. So String is the node and destination is the parent. 
I have also used an ArrayList<String> which holds the unvisited nodes.

Task 2:
For Task 2, I use a recursive function which will find the edges or the airports which can be flown in from the source airports until the depth(second argument) becomes zero.

Task 3:
For this task, I have a function which will find all the edges or the airports which can be reached from the source airports. Now I pass each node to a recursive function which will find its sub nodes and also stores the pathcost while doing this. This is done until we find the source. Also care is taken that if a duplicate node is reached then we stop evaluating that path and go on and evaluate the next node. This way all possible paths from Source to Source are stored in an ArrayList<ArrayList<Node>>. 
Then, each ArrayList<Node> is traversed and the list with maximum pathCost is printed which is the longest roundtrip path.

 