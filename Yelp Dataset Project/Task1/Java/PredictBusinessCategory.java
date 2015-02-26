package com.example.restaurants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
/*
 * It predicts the categories for a business based on the reviews. 
 * The categories for a business is determined based on the tf-idf score across categories.
 * The top tf-idf values is used to determine the categories.
 */
public class PredictBusinessCategory {
	static HashMap<String, Integer> categoryCount = new HashMap<String, Integer>();
	static HashMap<String, HashMap<String, Integer>> wordCount = new HashMap<String, HashMap<String, Integer>>();
	static MongoClient mongoClient;
	static DB db;
	static DBCollection restaurantsCategoryColl;
	static HashMap<String, Double> tfidf = new HashMap<String, Double>();
	static {
		try {
			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDB("test11");
			restaurantsCategoryColl = db
					.getCollection("yelp_restaurants_categories");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * It sorts the hashmap based on the values in it.
	 */
	public static HashMap<String, Double> sortByComparator(
			Map<String, Double> unsortMap) {

		List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(
				unsortMap.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		HashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		for (Iterator<Map.Entry<String, Double>> it = list.iterator(); it
				.hasNext();) {
			Map.Entry<String, Double> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	/*
	 * For a given word it identifies the count across different categories.
	 * It returns a hashmap with key as category and value as count. 
	 */
	public static HashMap<String, Integer> getCategoryCountForWord(String word) {
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		DBCursor restaurantsCategoryCursor = restaurantsCategoryColl.find();
		BasicDBObject doc = (BasicDBObject) restaurantsCategoryCursor.next();
		for (Map.Entry<String, Object> m : doc.entrySet()) {
			if (m.getKey().equalsIgnoreCase("category")) {
				BasicDBList bl = (BasicDBList) m.getValue();
				for (String category : bl.keySet()) {
					DBCollection categoryColl = db.getCollection(bl.get(
							category).toString());
					DBCursor categoryCursor = categoryColl.find();
					while (categoryCursor.hasNext()) {
						BasicDBObject docCategory = (BasicDBObject) categoryCursor
								.next();

						for (Map.Entry<String, Object> wm : docCategory
								.entrySet()) {
							if (wm.getKey().contains("file")) {
								BasicDBObject bd = (BasicDBObject) wm
										.getValue();
								if (bd.get(word) != null) {
									if (hm.get((String) bl.get(category)) == null) {
										hm.put((String) bl.get(category),
												(Integer) bd.get(word));
									} else {
										hm.put((String) bl.get(category),
												hm.get((String) bl
														.get(category))
														+ (Integer) bd
																.get(word));

									}
								}

							}
						}
					}
				}
			}
		}
		return hm;
	}
	/*
	 * It returns the size of each collection where each collection represents a particular category.
	 *  
	 */
	
	public static void getCategorySize() {
		// HashMap<String, Integer> hm = new HashMap<String, Integer>();
		DBCursor restaurantsCategoryCursor = restaurantsCategoryColl.find();
		BasicDBObject doc = (BasicDBObject) restaurantsCategoryCursor.next();
		for (Map.Entry<String, Object> m : doc.entrySet()) {
			if (m.getKey().equalsIgnoreCase("category")) {
				BasicDBList bl = (BasicDBList) m.getValue();
				for (String category : bl.keySet()) {
					DBCollection categoryColl = db.getCollection(bl.get(
							category).toString());
					DBCursor categoryCursor = categoryColl.find();
					while (categoryCursor.hasNext()) {
						BasicDBObject docCategory = (BasicDBObject) categoryCursor
								.next();
						for (Map.Entry<String, Object> wm : docCategory
								.entrySet()) {
							if (wm.getKey().contains("file")) {
								BasicDBObject bd = (BasicDBObject) wm
										.getValue();
								if (categoryCount
										.get((String) bl.get(category)) == null) {
									categoryCount.put(
											(String) bl.get(category),
											(Integer) bd.size());
								} else {
									categoryCount
											.put((String) bl.get(category),
													(Integer) bd.size()
															+ categoryCount
																	.get((String) bl
																			.get(category)));
								}
							}
						}
					}
				}
			}
		}
	}
	/*
	 * It is used to compute the tf-idf score across categories.
	 */
	public static void calculateTfIdf() {
		for (String key : wordCount.keySet()) {
			HashMap<String, Integer> hm = wordCount.get(key);

			for (String k : hm.keySet()) {
				double tf = ((double) hm.get(k) / (double) categoryCount.get(k));
				double idf = Math.log((double) 241 / hm.size());
				if (tfidf.get(k) == null) {
					tfidf.put(k, tf * idf);
				} else {
					tfidf.put(k, tfidf.get(k) + (tf * idf));
				}
			}
		}

	}
	/*
	 * It predicts the categories for a business based on the reviews. 
	 * The categories for a business is determined based on the tf-idf score across categories.
	 * The top tf-idf values is used to determine the categories.
	 * The tf-idf values are then written to the file.
	 */
	public static void main(String args[]) {
		try {
			getCategorySize();
			
			for (int i = 1; i < 10; i++) {
				System.out.println(i);
				BufferedReader br = new BufferedReader(
						new FileReader(
								"C:\\Users\\Ramakant Khandel\\Desktop\\Course\\Information Retrieval\\Data2\\TestData"
										+ i + ".txt"));
				String text = "";

				while ((text = br.readLine()) != null) {

					if (text.length() > 1) {
						String arr[] = text.split("\\s+");
						for (String word : arr) {
							if (wordCount.get(word) == null) {
								HashMap<String, Integer> cCount = getCategoryCountForWord(word);
								wordCount.put(word, cCount);
							}
						}
					}
				}
				calculateTfIdf();
				tfidf = sortByComparator(tfidf);
				String fileName = "C:/Users/Ramakant Khandel/Desktop/Course/Information Retrieval/Data2/OutputData/TestOutput"
						+ i + ".txt";
				FileWriter fw = new FileWriter(fileName, true);
				BufferedWriter bw = new BufferedWriter(fw);

				for (String key : tfidf.keySet()) {
					System.out.println(key + " : " + tfidf.get(key));
					bw.write(key + " : " + tfidf.get(key)+"\n");
				}
				br.close();
				bw.close();
				tfidf = new HashMap<String, Double>();
				wordCount = new HashMap<String, HashMap<String, Integer>>();
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
