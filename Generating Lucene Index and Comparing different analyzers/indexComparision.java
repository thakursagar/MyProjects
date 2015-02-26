import java.util.Scanner;

import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;


public class indexComparision {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//abc gen = new abc();
		//gen.readFiles();
		System.out.println("Enter 1 for Standard:");
		System.out.println("Enter 2 for Keyword:");
		System.out.println("Enter 3 for Simple:");
		System.out.println("Enter 4 for Stop:");

		// User is asked which analyzer to use. Based on the option selected, corresponding analyzer object is created and the readfile and parsefile functions are called.
		Scanner kbd =new Scanner(System.in);
		int number = kbd.nextInt();
		switch(number){
		case 1:generateIndex genStand = new generateIndex(new StandardAnalyzer());
		genStand.readFiles(); 
			System.out.println("STANDARD ANALYZER:");
			
			genStand.extractData("E:/Z534 Info Retrieval/Assignment1/indexKeyword");
			break;
		case 2:generateIndex genKey = new generateIndex(new KeywordAnalyzer());
		genKey.readFiles(); 
			System.out.println("KEYWORD ANALYZER:");

			genKey.extractData("E:/Z534 Info Retrieval/Assignment1/indexKeyword");
		break;
		case 3:generateIndex genSimple = new generateIndex(new SimpleAnalyzer());
		genSimple.readFiles(); 
			System.out.println("SIMPLE ANALYZER:");

			genSimple.extractData("E:/Z534 Info Retrieval/Assignment1/indexKeyword");
		break;
		case 4:generateIndex genStop = new generateIndex(new StopAnalyzer());
		genStop.readFiles(); 
			System.out.println("STOP ANALYZER:");

			genStop.extractData("E:/Z534 Info Retrieval/Assignment1/indexKeyword");
		break;
		
		}
		
	}

}

