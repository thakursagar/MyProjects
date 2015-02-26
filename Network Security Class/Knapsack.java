import java.util.*;
public class Knapsack {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner kbd=new Scanner(System.in);
		System.out.println("Enter six numbers:"); // take six numbers and store in the array
		int arr[]=new int[6];
		int sum=0;
		String print="";
		for(int c=0;c<6;c++) // storing in the array
		{
			arr[c]=kbd.nextInt();
		}
		System.out.println("Enter the number to compare the sum with:");
		int no=kbd.nextInt();
		System.out.println("The numbers whose sum is equal to "+ no + " are:");
		int i=0,j=0,k=0,l=0,m=0,n=0;
		for(i=0;i<2;i++)
		{
			
		
			for(j=0;j<2;j++)
			{
				
				
			
				for(k=0;k<2;k++)
				{
					for(l=0;l<2;l++)
					{
						for(m=0;m<2;m++)
						{
							for(n=0;n<2;n++)
							{
						
					
					if(i==1)
						{
							sum+=arr[0];
							print+=arr[0] + " ";
						}
					if(j==1){
						sum+=arr[1];
						print+=arr[1] + " ";
					}
					if(k==1){
						sum+=arr[2];
						print+=arr[2] + " ";
					}
					if(l==1){
						sum+=arr[3];
						print+=arr[3] + " ";
					}
					if(m==1){
						sum+=arr[4];
						print+=arr[4] + " ";
					}
					if(n==1){
						sum+=arr[5];
						print+=arr[5] + " ";
					}
					//System.out.println(i+""+j+""+k+""l+""+m+""n);
					//System.out.println(sum);
					if(sum==no)
						System.out.println(print);
					sum=0;
					print="";
		
	}}}}}}

	}}
