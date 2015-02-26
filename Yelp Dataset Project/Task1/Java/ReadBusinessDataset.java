package com.example.restaurants;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.JsonMappingException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
/*
 * It identifies all the categories for a business by parsing the 'yelp_academic_dataset_business' file.
 * It then stores the business id  and the attributes for the business in the 'yelp_academic_dataset_business_attributes' collection.

 */
public class ReadBusinessDataset {
	static String business_id="";
	static String categories="";
	static String name="";
	static int count=0;
	static HashSet<String> category=new HashSet<String> ();
	public static void main(String args[]){
		try {
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	         // Now connect to your databases
	        DB db = mongoClient.getDB( "test11" );
	        DBCollection coll =db.getCollection("yelp_academic_dataset_business");
			JsonFactory jfactory = new JsonFactory();
		 
			JsonParser jParser = jfactory.createJsonParser(new File("C:\\Users\\Ramakant Khandel\\Desktop\\Course\\Information Retrieval\\Project\\yelp_dataset\\yelp_academic_dataset_business.json"));
		 
			while ((jParser.nextToken() != JsonToken.END_ARRAY)) {
				 
            	if(jParser.getText()==null){
					break;
				}
            	//System.out.println(jParser.getText());
            	if(jParser.getText()!=null && jParser.getText().equalsIgnoreCase("business_id")){
					jParser.nextToken();
					//System.out.println(jParser.getText());	
					business_id=jParser.getText();
				}
            	else if(jParser.getText()!=null && jParser.getText().equalsIgnoreCase("name")){
					jParser.nextToken();
					//System.out.println(jParser.getText());	
					name=jParser.getText();
				}
            	else if(jParser.getText()!=null && jParser.getText().equalsIgnoreCase("categories")){
					jParser.nextToken();
					while ((jParser.nextToken() != JsonToken.END_ARRAY)){
						//System.out.println(jParser.getText());
//						if(!db.collectionExists(jParser.getText())){
//							BasicDBObject doc = new BasicDBObject();
//							db.createCollection(jParser.getText(),doc);
//							count++;
//						}
						category.add(jParser.getText());
					}
				}
            	else if(jParser.getText()!=null && jParser.getText().equalsIgnoreCase("neighborhoods")){
					jParser.nextToken();
					while ((jParser.nextToken() != JsonToken.END_ARRAY)){
						//System.out.println(jParser.getText());
					}
				}
            	if(name.length()>0 && business_id.length()>0 && category.size()>0){
            		System.out.println(name);
            		System.out.println(business_id);
            		System.out.println(categories);
            		BasicDBObject doc = new BasicDBObject("business_id", business_id).
            	    append("name", name).
            	    append("categories", category);
            		coll.insert(doc);       
            		name="";
            		business_id="";
            		categories="";
            		category=new HashSet<String> ();
            	}

			}
			jParser.close();
			System.out.println(count);
	   }
	   catch (JsonGenerationException e) {
			  e.printStackTrace();
	   } 
	   catch (JsonMappingException e) {
			  e.printStackTrace();
	   } 
	   catch (IOException e) {
			  e.printStackTrace();
	   }

	}

}
