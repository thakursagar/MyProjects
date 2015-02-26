package com.example.restaurants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InvalidFormatException;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.json.JSONObject;
/*
 * It identifies the reviews of a business whose one of the categories 
 * is Restaurants by parsing the 'yelp_academic_dataset_review' and 'yelp_academic_dataset_tip' file.
 * It then stores the business id  and reviews for the business in the text file 
 * based on the hash value.
 */
public class ParseJasonOnceAgain {
	static StringBuffer token_text = new StringBuffer();
	static StringBuffer noun_text = new StringBuffer();
	static HashSet<String> businessList = new HashSet<String>();
	static POSModel model;
	static double count = 1;
	static EnglishAnalyzer analyzer = new EnglishAnalyzer();

	static {
		model = new POSModelLoader()
				.load(new File(
						"C:/Users/Ramakant Khandel/Desktop/Course/Information Retrieval/en-pos-maxent.bin"));
	}
	/*
	 * This method performs StandardTokenizer, StandardFilter, LowercaseFilter, and StopFilter 
	 * task using Standard Analyzer.
	 * 
	 */
	
	public static String removeStopWord(String text) {
		try {
			StandardAnalyzer analy = new StandardAnalyzer();
			TokenStream stream = analy.tokenStream("feild", new StringReader(
					text));
			StringBuffer a = new StringBuffer();
			stream.reset();
			while (stream.incrementToken()) {
				String x = stream.getAttribute(CharTermAttribute.class)
						.toString();
				// System.out.println(x);
				a.append(" " + x);
			}

			stream.close();
			analy.close();
			return a.toString().trim();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
	/*
	 * This method performs EnglishPossesive filter, KeywordMarker filter and PorterStemFilter
	 * using English Analyzer.
	 *  
	 */
	public static void getTokenizeString(String text) {
		try {
			String t = removeStopWord(text);
			if (t != null) {
				TokenStream stream = analyzer.tokenStream("feild",
						new StringReader(t));

				stream.reset();
				while (stream.incrementToken()) {
					String x = stream.getAttribute(CharTermAttribute.class)
							.toString();
					// System.out.println(x);
					token_text.append(" " + x);
				}
				token_text.toString().trim();
				stream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * This method performs extraction of nouns from String using NLP.
	 *  
	 */
	public static String POSTaggerNoun(String str) {
		String nounStr = "";
		try {
			POSTaggerME tagger = new POSTaggerME(model);
			String[] sent = WhitespaceTokenizer.INSTANCE.tokenize(str);
			String[] tags = tagger.tag(sent);
			int i = 0;
			for (String tag : tags) {
				if (tag.charAt(0) == 'N') {
					nounStr += sent[i] + " ";
				}
				i++;
			}
			noun_text.append(nounStr);
		} catch (Exception expObj) {
			System.out.println(expObj.getMessage());
		}
		return null;

	}
	/*
	 * It returns the list of the business id whose one of the categories is restaurants. 
	 */
	public static void getBusinessList() {
		try {
			String line;
			BufferedReader br = new BufferedReader(
					new FileReader(
							"C:\\Users\\Ramakant Khandel\\Desktop\\Course\\Information Retrieval\\Project\\yelp_dataset\\yelp_academic_dataset_business.json"));
			while ((line = br.readLine()) != null) {
				JSONObject obj = new JSONObject(line);
				if (obj.get("categories").toString().contains("Restaurants")) {
					String business_id = (String) obj.get("business_id");
					businessList.add(business_id);
				}

			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * It identifies the reviews of a business whose one of the 
	 * categories is Restaurants by parsing the 'yelp_academic_dataset_review' and 
	 * 'yelp_academic_dataset_tip' file.
	 * It then stores the business id  and reviews for the business in the text file 
	 * based on the hash value.  
	 */
	public static void main(String[] args) {

		getBusinessList();
		try {
			// String jsonData = "";
			// File file[] = new File[14];
			FileWriter fw[] = new FileWriter[15];
			BufferedWriter bw[] = new BufferedWriter[15];
			for (int i = 0; i < 15; i++) {
				// file[i] = new File(
				// "C:/Users/Ramakant Khandel/Desktop/Course/Information Retrieval/Data/"
				// + (i + 1) + ".txt");
				String fileName = "C:/Users/Ramakant Khandel/Desktop/Course/Information Retrieval/Data2/"
						+ (i + 1) + ".txt";
				fw[i] = new FileWriter(fileName, true);
				bw[i] = new BufferedWriter(fw[i]);

			}

			String line;
			BufferedReader br = new BufferedReader(
					new FileReader(
							"C:\\Users\\Ramakant Khandel\\Desktop\\Course\\Information Retrieval\\Project\\yelp_dataset\\yelp_academic_dataset_tip.json"));
			while ((line = br.readLine()) != null) {
				JSONObject obj = new JSONObject(line);
				String business_id = (String) obj.get("business_id");
				String text = (String) obj.get("text");
				System.out.println(count++);

				if (business_id.length() > 0 && text.length() > 0
						&& businessList.contains(business_id)) {

					if (text.length() > 2) {
						POSTaggerNoun(text);

						if (noun_text.length() > 0) {
							getTokenizeString(noun_text.toString());

							int a = business_id
									.charAt(business_id.length() - 1);
							int b = business_id
									.charAt(business_id.length() - 2);
							int no = (a + b) % 15;
							bw[no].write(business_id + "|" + token_text + "\n");
						}

					}
					token_text = new StringBuffer();
					noun_text = new StringBuffer();
					// jsonData="";

				}
			}

			for (int i = 0; i < 15; i++) {
				bw[i].close();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
