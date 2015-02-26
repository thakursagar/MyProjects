package com.example.restaurants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.json.JSONObject;
/*
 * It identifies all the reviews for a particular businessId and stores it in a text file after 
 * extracting nouns and performing tokenization and stemming. 
 * The data processing step (noun extraction and tokenization,stemming) is same as the one did 
 * in the process while traversing the review and tip file and storing the data in the text file.
 */
public class CreateTestData {
	static StringBuffer token_text = new StringBuffer();
	static StringBuffer noun_text = new StringBuffer();
	static POSModel model;
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
	 * It identifies all the reviews for a business and stores them in the text file.
	 * This is then used as the test data for task one. 
	 */
	public static void main(String args[]) throws Exception {
//		ArrayList<String> id = new ArrayList<String>();
//		id.add("cpGv6ES2hzPCtCUQgipwXw"); // 32
//		id.add("wPidHd5FxspPRgVqdEQ-Xw"); // 33
//		id.add("yYIEKwgQUHJpySOcVdCAkg"); // 34
//		id.add("QF14BFUVHRODxGNVflgm3A"); // 35
//		id.add("q83uB6EGF6y2MuR-7VZPRg"); // 36
//		id.add("oudTF4EN_C8thENGiTQf-w"); // 37
//		id.add("ZjwZIQM4TfFO7T7ck7FtYQ"); // 38
//		id.add("MVFeOZVt641WBvdsRmnhPg"); // 39
//		id.add("9vgu6o1CwP5NakVkb29z0g"); // 40
//		id.add("lN-5-YTsaJr_IByyA476iw"); // 21
//		id.add("KFJ1jBfFkRfyn3AoAUl3YQ"); // 22
//		id.add("G1qUGBNYNS220jKlPAlFsA"); // 23
//		id.add("kiAsweI4sOjDzGyz3Y_HsQ"); // 24
//		id.add("q8fD82us6uuGufvI44NoAg"); // 25
//		id.add("B0Vuwn6Hugc-0U5n31YBfg"); // 26
//		id.add("3zD6rxB92Mr0jb-LoYFFWw"); // 27
//		id.add("OC8AUJshLVimn_-P_INvJw"); // 28
//		id.add("eptljN50_4BpIwc2Op_A_A"); // 29
//		id.add("5uxhIGZlJm9g_zfG_83z5Q"); // 30
//		id.add("RIsEPpt2jM7c_Ko_CyLwMw"); // 31
		// id.add("");

		//for (int i = 32; i < 41; i++) {
		//	System.out.println(i);
			BufferedReader br = new BufferedReader(
					new FileReader(
							"C:\\Users\\Ramakant Khandel\\Desktop\\Course\\Information Retrieval\\Project\\yelp_dataset\\yelp_academic_dataset_review.json"));
			String fileName = "C:/Users/Ramakant Khandel/Desktop/Course/Information Retrieval/Data2/TestData"
					+ 41 + ".txt";
			FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bw = new BufferedWriter(fw);
//			String idNo = id.get(i - 32);
//			bw.write(idNo + "\n");

			String line = "";
			while ((line = br.readLine()) != null) {
				JSONObject obj = new JSONObject(line);
				String business_id = (String) obj.get("business_id");
				if (business_id.equalsIgnoreCase("gUt-pPUpOVVhaCFC8-E4yQ")) {
					String text = obj.getString("text");
					POSTaggerNoun(text);
					getTokenizeString(noun_text.toString());
					bw.write(token_text.toString() + "\n");
					noun_text = new StringBuffer();
					token_text = new StringBuffer();
				}
			}
			br.close();
			bw.close();
		}
	//}
}
