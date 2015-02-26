package com.example.restaurants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
/*
 * this file is used to read the data from the text file stored in the data folder.
 * This data is stored in hashmap with categories as key and value as hashmap with 
 * word as key and count as value(A hashmap within a hashmap-- HashMap<String,HashMap<String,Integer>)
 * This hashmap is traversed wherein a collection is created based on category and 
 * hashmap of value is stored for that category.  
 *  
 */
public class ReadParsedFileOnceAgain {
	// HashSet<String> categories = new HashSet<String>();
	static HashMap<String, HashSet<String>> business_category = new HashMap<String, HashSet<String>>();
	/*
	 * This method retrieves all the categories for a business based on the business id 
	 * using collection yelp_academic_dataset_business. 	
	 */
	public static void getCategories(String business_id) {
		try {
			// boolean flag=false;
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB("test11");
			DBCollection businessColl = db
					.getCollection("yelp_academic_dataset_business");
			HashSet<String> businessCategories = new HashSet<String>();
			BasicDBObject bd = new BasicDBObject();
			bd.append("business_id", business_id);
			DBCursor cursor = businessColl.find(bd);
			// int i = 1;
			while (cursor.hasNext()) {
				BasicDBObject doc = (BasicDBObject) cursor.next();
				for (Map.Entry<String, Object> attribute : doc.entrySet()) {
					if (attribute.getKey().equalsIgnoreCase("categories")) {
						BasicDBList bl = (BasicDBList) attribute.getValue();
						for (String category : bl.keySet()) {
							businessCategories.add((String) bl.get(category));

						}
						business_category.put(business_id, businessCategories);
						businessCategories = new HashSet<String>();

					}
				}
				// System.out.println(i++);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/*
	 * This file is used to read the data from the text file stored in the data folder.
	 * This data is stored in hashmap with categories as key and value as hashmap with 
	 * word as key and count as value(A hashmap within a hashmap-- HashMap<String,HashMap<String,Integer>)
	 * This hashmap is traversed wherein a collection is created based on category and 
	 * hashmap of value is stored for that category.	 
	 */
	public static void main(String args[]) {

		try {

			HashMap<String, HashMap<String, Integer>> data = new HashMap<String, HashMap<String, Integer>>();
			String line = "";
			File file[] = new File[15];
			FileReader fr[] = new FileReader[15];
			BufferedReader br[] = new BufferedReader[15];
			// for (int i = 0; i < 15; i++) {
			int y = 1;
			file[6] = new File(
					"C:/Users/Ramakant Khandel/Desktop/Course/Information Retrieval/Data2/"
							+ (15) + ".txt");
			fr[6] = new FileReader(file[6]);
			br[6] = new BufferedReader(fr[6]);
			while ((line = br[6].readLine()) != null) {
				if (line.length() > 0) {
					System.out.println(y++);
					if (y == 440) {
						System.out.println();
					}
					HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
					HashSet<String> categry = new HashSet<String>();
					System.out.println(line);
					String arr[] = line.split("\\|");
					if (arr.length == 2) {
						String words[] = arr[1].split("\\s+");
						for (String word : words) {
							if (word.length() > 0) {
								
								if (wordCount.get(word) == null) {
									wordCount.put(word, 1);
								} else {
									int count = wordCount.get(word);
									wordCount.put(word, count + 1);
								}
							}
						}
						if (business_category.get(arr[0]) == null) {
							getCategories(arr[0]);
						}
						categry = business_category.get(arr[0]);
						if (categry != null) {
							for (String c : categry) {
								if (data.get(c) == null) {
									data.put(c, wordCount);
								} else {
									HashMap<String, Integer> wordDataCount = data
											.get(c);
									for (String w : wordCount.keySet()) {
										if (wordDataCount.get(w) == null) {
											wordDataCount.put(w,
													wordCount.get(w));
										} else {
											int l = wordDataCount.get(w);
											wordDataCount.put(w,
													l + wordCount.get(w));
										}
									}

								}
							}
						}
					}
				}
			}
			int size=0;
			for (String key : data.keySet()) {
				size+=data.get(key).size();
				
			}
			System.out.println(size);
			System.out.println(data.size());
			try {
				// boolean flag=false;
				MongoClient mongoClient = new MongoClient("localhost", 27017);
				DB db = mongoClient.getDB("test11");
				int yp=1;
				for (String key : data.keySet()) {
					DBCollection categoryColl = db.getCollection(key);
					HashMap<String, Integer> wordCount = data.get(key);
					Map<String, Object> documentMap = new HashMap<String, Object>();
					Map<String, Object> documentMapDetail = new HashMap<String, Object>();
					for(String k:wordCount.keySet()){
						k=k.replace(".", "");
						documentMapDetail.put(k,wordCount.get(k));
					}
					documentMap.put("file15",documentMapDetail);
					
					categoryColl.insert(new BasicDBObject(documentMap));
					System.out.println(yp++);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println();
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
