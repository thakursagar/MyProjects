import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.LMDirichletSimilarity;
import org.apache.lucene.search.similarities.LMJelinekMercerSimilarity;
import org.apache.lucene.search.similarities.Similarity;

public class compareAlgorithms {

	/**
	 * @param out1 
	 * @param out2 
	 * @param similarity 
	 * @param string 
	 * @param args
	 */
	public static  void parseFile(File file, PrintStream out2, PrintStream out1, Similarity similarity, String string) throws Exception
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
			else continue;
			//System.out.println(num);
			//out1.println(num + "\t");
			String title = StringUtils.substringBetween(divideByTop[i], "<title> Topic:", "<desc>");
			//System.out.println(title);
			String desc = StringUtils.substringBetween(divideByTop[i], "<desc> Description:", "<smry>");
			//System.out.println(desc);
			if(title == null && desc == null){
				continue;
			}
			calcScore(num,title, "title", out2, similarity,string);
			calcScore(num,desc, "desc", out1, similarity,string);
			//System.out.println(top);
			//System.out.println(desc);
		}
	}
	public static void calcScore(int num,String string, String type, PrintStream out2, Similarity similarity, String string2) throws IOException, ParseException{
		
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File("E:/Z534 Info Retrieval/Assignment2/index/default"))); 
		IndexSearcher searcher = new IndexSearcher(reader);
		searcher.setSimilarity(similarity); //You need to explicitly specify the ranking algorithm using the respective Similarity class
		Analyzer analyzer = new StandardAnalyzer(); 
		QueryParser parser = new QueryParser("TEXT", analyzer);
		Query query = parser.parse(string.replaceAll("/", "//"));
		TopScoreDocCollector collector = TopScoreDocCollector.create(1000, true ); 
		searcher.search(query, collector);
		ScoreDoc[] docs = collector.topDocs().scoreDocs; 
		int counterTitle = 1;
		int counterDesc = 1;
		for (int i = 0; i < docs.length; i++) {
		Document doc = searcher.doc(docs[i].doc);
		
		if(type.equals("title")){
			
			out2.println(num+" " + "0 " +doc.get("DOCNO")+" " + counterTitle +" "+docs[i].score+" " + string2+"_short");
			//out1.println("0\t");
			//out1.println(doc.get("DOCNO")+"\t"+docs[i].score+"\t");
			//System.out.println(num +"\t0\t" + doc.get("DOCNO")+"\t"+docs[i].score);
			counterTitle++;
		}
		else {
			out2.println(num+"\t" + "1\t" + doc.get("DOCNO")+"\t"+ counterDesc + "\t" + docs[i].score+"\t" + string2+"_long");
		
			
			//out2.println("1\t");
			//out2.println(doc.get("DOCNO")+"\t"+docs[i].score);
			//System.out.println(num + "\t1\t" + doc.get("DOCNO")+"\t"+docs[i].score);
			counterDesc++;
		}
	}
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File file = new File("E:/Z534 Info Retrieval/Assignment2/topics.51-100");
		
		PrintStream out2=null;
		PrintStream out1=null;
		PrintStream out3=null;
		PrintStream out4=null;
		PrintStream out5=null;
		PrintStream out6=null;
		PrintStream out7=null;
		PrintStream out8=null;
		

		Similarity BM25 = new BM25Similarity();
		Similarity VSM = new DefaultSimilarity();
		Similarity LMDS = new LMDirichletSimilarity();
		float lambda = (float) 0.7;
		Similarity LMJMS = new LMJelinekMercerSimilarity(lambda);
		

		out2 = new PrintStream(new FileOutputStream("E:\\Z534 Info Retrieval\\Assignment2\\BM25shortQuery.txt"));
		out1 = new PrintStream(new FileOutputStream("E:\\Z534 Info Retrieval\\Assignment2\\BM25longQuery.txt"));
		out3 = new PrintStream(new FileOutputStream("E:\\Z534 Info Retrieval\\Assignment2\\VSMlongQuery.txt"));
		out4 = new PrintStream(new FileOutputStream("E:\\Z534 Info Retrieval\\Assignment2\\VSMshortQuery.txt"));
		out5 = new PrintStream(new FileOutputStream("E:\\Z534 Info Retrieval\\Assignment2\\LMDSlongQuery.txt"));
		out6 = new PrintStream(new FileOutputStream("E:\\Z534 Info Retrieval\\Assignment2\\LMDSshortQuery.txt"));
		out7 = new PrintStream(new FileOutputStream("E:\\Z534 Info Retrieval\\Assignment2\\LMJMSlongQuery.txt"));
		out8 = new PrintStream(new FileOutputStream("E:\\Z534 Info Retrieval\\Assignment2\\LMJMSshortQuery.txt"));
		parseFile(file, out2, out1, BM25, "BM25");
		parseFile(file, out4, out3, VSM, "VSM");
		parseFile(file, out6, out5, LMDS, "LMDS");
		parseFile(file, out8, out7, LMJMS, "LMJMS");
		
		
		
	}
		

	}


