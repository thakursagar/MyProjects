package com.example.restaurants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class RestaurantsCategories {
/*
 * It is used to identify different categories in which restaurants is one of the category.
 * There are in all 241 different categories
 * The collections in which this is stored is yelp_restaurants_categories
 * 
 */
		
	public static void main(String[] args) {
		BufferedReader br = null;
		HashSet<Object >s=new HashSet<Object>();
		try {
			String line;
			HashSet<String> category=new HashSet<String>();
			br = new BufferedReader(
					new FileReader(
							"C:\\Users\\Ramakant Khandel\\Desktop\\Course\\Information Retrieval\\Project\\yelp_dataset\\yelp_academic_dataset_business.json"));
			while ((line = br.readLine()) != null) {
				JSONObject obj = new JSONObject(line);
				System.out.println(line);
				boolean flag=false;
				HashSet<String> cgry=new HashSet<String>();				
				System.out.println( obj.getJSONArray("categories"));
				JSONArray a=obj.getJSONArray("categories");
				
				for(int i=0;i<a.length();i++){
					System.out.println(a.get(i));
					cgry.add( a.getString(i));
					if(a.getString(i).equalsIgnoreCase("restaurants")){
						flag=true;
					}
				}
				if(flag){
					Iterator itr=cgry.iterator();
					while(itr.hasNext()){
						category.add((String)itr.next());
					}
				}
				
			}
			System.out.println(category.size());
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB("test11");
			DBCollection categoryColl = db
					.getCollection("yelp_restaurants_categories");
			Map<String, Object> documentMap = new HashMap<String, Object>();
			documentMap.put("category", category);
			
			categoryColl.insert(new BasicDBObject(documentMap));
			
			
		}
		catch(Exception e){
			
		}
	}

}
