import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

public class generateIndex {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws Exception 
	 */
	public generateIndex(Analyzer a) throws Exception {
		// TODO Auto-generated constructor stub
		dir = FSDirectory.open(new File("E:/Z534 Info Retrieval/Assignment1/indexKeyword"));
		analyzer = a;
		iwc = new IndexWriterConfig(Version.LUCENE_4_10_0, analyzer);
		iwc.setOpenMode(OpenMode.CREATE);
		writer = new IndexWriter(dir, iwc);
		

		
		
	}
	Directory dir;
	Analyzer analyzer;
	IndexWriterConfig iwc;
	IndexWriter writer;
	
	// This function reads all the file in the corpus
	public  void readFiles() throws Exception
	{
		
		int fname = 890101;
		final File folder = new File("E:/Z534 Info Retrieval/corpus/corpus");
		for (final File fileEntry : folder.listFiles()) {
			File file = new File("E:/Z534 Info Retrieval/corpus/corpus/"+fileEntry.getName()+"");
			parseFile(file);			
	        //System.out.println(fileEntry.getName());
	    
		}
		/*
		while(true)
		{
		try{	
		File file = new File("E:/Z534 Info Retrieval/corpus/corpus/AP"+ fname+".trectext");
		
		fname++;
		parseFile(file);
		}
		catch(Exception e){
			
		}
		}*/
		writer.forceMerge(1);
		writer.commit();
		writer.close();
		
		
	}
	// This function does the parsing using the apache commons library and puts the data in the luceneDoc
	public  void parseFile(File file) throws Exception
	{
		String fileToString = FileUtils.readFileToString(file);
		String divideByDoc[] = fileToString.split("<DOC>");
		/*for(int i=0;i<divideByDoc.length;i++)
		{
			System.out.println(divideByDoc[i]);
		}
		*/
		
		for(int i=0;i<divideByDoc.length;i++)
		{
			Document luceneDoc = new Document();
			
			
			String first = StringUtils.substringBetween(divideByDoc[i], "<DOCNO>", "</DOCNO>");
			StringBuffer docNo = new StringBuffer();
			docNo.append(first);
			docNo.toString();
			luceneDoc.add(new StringField("DOCNO", docNo.toString(),Field.Store.YES));
			
			String second = StringUtils.substringBetween(divideByDoc[i], "<HEAD>", "</HEAD>");
			StringBuffer head = new StringBuffer();
			head.append(second);
			head.toString();
			luceneDoc.add(new TextField("HEAD", head.toString(),Field.Store.YES));
			String third = StringUtils.substringBetween(divideByDoc[i], "<BYLINE>", "</BYLINE>");
			StringBuffer byLine = new StringBuffer();
			byLine.append(third);
			byLine.toString();
			luceneDoc.add(new TextField("BYLINE", byLine.toString(),Field.Store.YES));
			String fourth = StringUtils.substringBetween(divideByDoc[i], "<DATELINE>", "</DATELINE>");
			StringBuffer dateLine = new StringBuffer();
			dateLine.append(fourth);
			dateLine.toString();
			luceneDoc.add(new TextField("DATELINE", dateLine.toString(),Field.Store.YES));
			String fifth = StringUtils.substringBetween(divideByDoc[i], "<TEXT>", "</TEXT>");
			StringBuffer text = new StringBuffer();
			text.append(fifth);
			text.toString();
			luceneDoc.add(new TextField("TEXT", text.toString(),Field.Store.YES));
			writer.addDocument(luceneDoc);
		}
		
		//System.out.println(title);
	}
	//This function displays the statistics
	public static void extractData(String path) throws Exception{
				IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(path)));
				//Print the total number of documents in the corpus
				System.out.println("Total number of documents in the corpus:"+reader.maxDoc());
				//Print the number of documents containing the term "new" in <field>TEXT</field>.
				System.out.println("Number of documents containing the term \"new\" for field \"TEXT\": "+reader.docFreq(new Term("TEXT", "new")));
				//Print the total number of occurrences of the term "new" across all documents for <field>TEXT</field>.
				System.out.println("Number of occurences of \"new\" in the field \"TEXT\": "+reader.totalTermFreq(new Term("TEXT","new")));
				Terms vocabulary = MultiFields.getTerms(reader, "TEXT");
				//Print the size of the vocabulary for <field>content</field>, only available per-segment.
				System.out.println("Size of the vocabulary for this field: "+vocabulary.size());
				//Print the total number of documents that have at least one term for <field>TEXT</field>
				System.out.println("Number of documents that have at least one term for this field: "+vocabulary.getDocCount());
				//Print the total number of tokens for <field>TEXT</field>
				System.out.println("Number of tokens for this field: "+vocabulary.getSumTotalTermFreq());
				//Print the total number of postings for <field>TEXT</field>
				System.out.println("Number of postings for this field: "+vocabulary.getSumDocFreq());
				//Print the vocabulary for <field>TEXT</field>
				/*TermsEnum iterator = vocabulary.iterator(null);
				BytesRef byteRef = null;
				System.out.println("\n*******Vocabulary-Start**********");
				while((byteRef = iterator.next()) != null) {
				String term = byteRef.utf8ToString();
				System.out.print(term+"\t");
				}*/
				System.out.println("\n*******Vocabulary-End**********");
				reader.close();
	}
}
