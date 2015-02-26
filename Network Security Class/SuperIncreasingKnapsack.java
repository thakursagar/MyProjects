import java.util.ArrayList;
import java.util.Scanner;
 
public class SuperIncreasingKnapsack {

    public static void main(String arg[])
    {
        Scanner kbd= new Scanner(System.in);
        int arr[]= new int[6];
        int no;
        System.out.println("Enter six numbers:");
        for(int i=0;i<6;i++)
        {
            arr[i]=kbd.nextInt(); // take inputs
        }
        System.out.println("Enter the number to compare the sum with: ");
        no=kbd.nextInt();
        int temp=no;
        ArrayList<Integer> list= new ArrayList<>(); // create arraylist
        for(int i=5;i>=0;i--)
        {
        	if(no>=arr[i])
        	{
        		list.add(arr[i]); // if the sum is greater than the element then add
        		no-=arr[i]; // subtract the sum with the element
        		if(no==0)
        		{
        			System.out.println("The numbers whose sum is equal to " + temp +" are: " + list ); // print the arraylist
        			break;
        		}
        	}
        }
        if(no!=0)
        	System.out.println("Set of numbers not found whose sum is equal to "+temp); 
    }
    
}
