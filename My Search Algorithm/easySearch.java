import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;


public class easySearch {

	/**
	 * @param type 
	 * @param type 
	 * @param out1 
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 */
	
	public static void calculateScore(int num, String string, String type, PrintStream out1) throws IOException, ParseException{
		// TODO Auto-generated method stub
		Analyzer analyzer = new StandardAnalyzer();
		QueryParser parser = new QueryParser("TEXT", analyzer); 
		String queryString = string;
		Query query = parser.parse(queryString.replaceAll("[/?]", " "));
		Set<Term> queryTerms = new LinkedHashSet<Term>(); 
		query.extractTerms(queryTerms);
		ArrayList<String> k = new ArrayList<String>();
		for (Term t : queryTerms) 
		{ 
			//System.out.println(t.text());
			k.add(t.text());
			
		}
		HashMap<String, Double> printMap = new HashMap<String, Double>();
		HashMap<String, Double> insideMap = new HashMap<String, Double>();
		HashMap<String, HashMap<String, Double>> outsideMap = new HashMap<String, HashMap<String, Double>>();
		//HashMap<Integer, HashMap<String, Double>> NTF = new HashMap<Integer, HashMap<String, Double>>();
		String pathToIndex = "E:\\Z534 Info Retrieval\\Assignment2\\index\\default";
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(pathToIndex)));

		//Use DefaultSimilarity.decodeNormValue(…) to decode normalized document length 
		DefaultSimilarity dSimi=new DefaultSimilarity();
		//k(qi)
		//int kOfQi = 0;
		//Get the segments of the index
		List<AtomicReaderContext> leafContexts = reader.getContext().reader().leaves();
		int N=0;
		int index = 0;
		int kOfQi[] = new int[queryTerms.size()];
		for (int i = 0; i < leafContexts.size(); i++) 
		{
			AtomicReaderContext leafContext=leafContexts.get(i); 
			int startDocNo=leafContext.docBase;
			int numberOfDoc=leafContext.reader().maxDoc();
			N += numberOfDoc;
			//float normDocLeng=dSimi.decodeNormValue(leafContext.reader().getNormValues("TEXT").get(docId-startDocNo)); 
			int doc;
			index=0;
			for(Term t: queryTerms)
			{
				//Get the term frequency of "new" within each document containing it for <field>TEXT</field>
				DocsEnum de = MultiFields.getTermDocsEnum(leafContext.reader(),MultiFields.getLiveDocs(leafContext.reader()),"TEXT", new BytesRef(t.text()));
				
				//If the term is present in that doc
				while (de != null && (doc = de.nextDoc()) != DocsEnum.NO_MORE_DOCS) 
				{
					float normDocLeng=dSimi.decodeNormValue(leafContext.reader().getNormValues("TEXT").get(doc)); 
					
					//k.add(t.text());
					String realDocId = leafContext.reader().document(de.docID()).get("DOCNO");
					kOfQi[index]++;
					if(outsideMap.containsKey(realDocId))
					{
						HashMap<String, Double> map = outsideMap.get(realDocId);
						map.put(t.text(), (double) (de.freq()/normDocLeng));
						//outsideMap.put((doc+startDocNo), map);
					}
					else
					{
						HashMap<String, Double> map = new HashMap<String, Double>();
						Double hashValue = (double) (de.freq()/normDocLeng);
						//System.out.println("hashValue: " + hashValue);
						map.put(t.text(), hashValue);
						outsideMap.put(realDocId, map);
					}
					//System.out.println(t.text() + " occurs "+de.freq() + " times in doc(" +(de.docID()+startDocNo)+") for the field TEXT");
					
				}
				
				//System.out.println("k(qi) for term " + t.text() +" :" + kOfQi[index]);
				index++;
			}
			
			//System.out.println("N: " + N);
		}
		/*for (Map.Entry<Integer, HashMap<String, Double>> entry1 : outsideMap.entrySet()) {
		    Integer docId1 = entry1.getKey();
		    System.out.print("For doc: " + docId1);
		    HashMap<String, Double> value1 = entry1.getValue();
		    for (Map.Entry<String, Double> insideEntry : value1.entrySet()){
		    	String key = insideEntry.getKey();
		    	Double Val = insideEntry.getValue();
		    	//System.out.println("Key: " + key + "Val: " + Val);
		    }
		}*/
		
		Double finalValue = 0.0;
		for (Map.Entry<String, HashMap<String, Double>> entry : outsideMap.entrySet()) {
			String docId = entry.getKey();
		  //  System.out.print("For doc: " + docId);
		    
		    HashMap<String, Double> value = entry.getValue();
		    
		    String s = null;
		    for (Map.Entry<String, Double> insideEntry : value.entrySet()){
		    	String key = insideEntry.getKey();
		    	int index2 = getIndex(k, key);
		    	Double Val = insideEntry.getValue();
		    	//System.out.println("Val :" + Val);
		    	Val = Val * (double)Math.log10((1+ (N/kOfQi[index2])));
		    	finalValue += Val;
		    	
		    	
		    	s = key;
		    	//System.out.println("Individual value: " + Val);
		    }
		   // System.out.print(" value: " + finalValue);
		    printMap.put(docId.toString(), finalValue);
		    System.out.println();
		    finalValue=0.0;
		    //System.out.println();
		    
		    
		}
		HashMap<String, Double> sortedMap = sortByComparator(printMap);
		int counterTitle = 1;
		int counterDesc = 1;
		if(sortedMap.size()<1000){
			//System.out.println("Values less than 1000. Top" + sortedMap.size() + "Values are-");
			for (Map.Entry<String, Double> print : sortedMap.entrySet()){
				String doc = print.getKey();
				double value = print.getValue();
				
				if(type.equals("title")){
					//System.out.println(num + "\t0\t" + doc + "\t" + counterTitle + "\t" + value + "\tMyAlgo");
					out1.println(num + "\t0\t" + doc + "\t" + counterTitle +"\t" + value + "\tMyAlgo_short");
					counterTitle++;
				}
				else{
					//System.out.println(num + "\t0\t" + doc + "\t" + counterTitle + "\t" + value + "\tMyAlgo");
					out1.println(num + "\t1\t" + doc + "\t" + counterDesc +"\t" + value + "\tMyAlgo_long");
					counterDesc++;
				}			
				}
		}
		else{
			int cnt = 0;
			//System.out.println("Top 1000 values are:");
			for (Map.Entry<String, Double> print : sortedMap.entrySet()){
				if(cnt>=1000)
					break;
				cnt++;
				String doc = print.getKey();
				double value = print.getValue();
				if(type.equals("title")){
					//System.out.println(num + "\t0\t" + doc + "\t" + counterTitle + "\t" + value + "\tMyAlgo_short");
					out1.println(num + "\t0\t" + doc + "\t" + counterTitle + "\t" + value + "\tMyAlgo_short");
					counterTitle++;
				}
				else{
					//System.out.println(num + "\t0\t" + doc + "\t" + counterTitle + "\t" + value + "\tMyAlgo_long");
					out1.println(num + "\t1\t" + doc + "\t" + counterDesc + "\t" + value + "\tMyAlgo_long");
					counterDesc++;
				}
				
			}
		}
	}
	public static void main(String[] args) throws ParseException, IOException {
		PrintStream out1 = new PrintStream(new FileOutputStream("E:\\Z534 Info Retrieval\\Assignment2\\easySearch.txt"));
		 calculateScore(999,"browse online", "easySearch", out1 );
}
	public static HashMap<String, Double> sortByComparator(Map<String, Double> unsortMap) {
		 
		List<Map.Entry<String, Double>> list = 
			new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());
 
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,
                                           Map.Entry<String, Double> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
 
		
		HashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		for (Iterator<Map.Entry<String, Double>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Double> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	private static int getIndex(ArrayList<String> k, String key) {
		// TODO Auto-generated method stub
		int cnt=0;
		for(String s : k){
			
			if(key.equals(s)){
				//System.out.println("COunt: " + cnt);
				return cnt;
			}
				
			cnt++;
		}
		return 0;
	}
	}
