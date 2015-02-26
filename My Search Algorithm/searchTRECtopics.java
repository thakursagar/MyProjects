import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;


public class searchTRECtopics {

	/**
	 * @param out1 
	 * @param out2 
	 * @param args
	 * @return 
	 * @throws Exception 
	 */
	public static void readFiles(PrintStream out1, PrintStream out2) throws Exception {

		File file = new File("E:/Z534 Info Retrieval/Assignment2/topics.51-100");
		parseFile(file, out1, out2);
	
	}
	public static  void parseFile(File file, PrintStream out1, PrintStream out2) throws Exception
	{
		
		String fileToString = FileUtils.readFileToString(file);
		String divideByTop[] = fileToString.split("<top>");
		for(int i=0;i<divideByTop.length;i++)
		{
			String num1 = StringUtils.substringBetween(divideByTop[i], "<num> Number:", "<dom>");
			int num =0;
			if(num1!=null){
				//System.out.println(num1);
				num = Integer.parseInt(num1.trim());
			}
			String title = StringUtils.substringBetween(divideByTop[i], "<title> Topic:", "<desc>");
			String desc = StringUtils.substringBetween(divideByTop[i], "<desc> Description:", "<smry>");
			if(title == null && desc == null){
				continue;
			}
			
			easySearch.calculateScore(num,title, "title", out1);
			easySearch.calculateScore(num,desc, "desc", out2);
			//System.out.println(top);
			//System.out.println(desc);
		}
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//easySearch es = new easySearch();
		PrintStream out1 = null;
		out1 = new PrintStream(new FileOutputStream("E:\\Z534 Info Retrieval\\Assignment2\\MyAlgoShortQuery.txt"));
		PrintStream out2 = null;
		out2 = new PrintStream(new FileOutputStream("E:\\Z534 Info Retrieval\\Assignment2\\MyAlgoLongQuery.txt"));
		readFiles(out1,out2);
	}

}
