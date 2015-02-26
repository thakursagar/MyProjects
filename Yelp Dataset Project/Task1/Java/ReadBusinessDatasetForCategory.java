package com.example.restaurants;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

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
 * It identifies all the categories for a business by parsing the yelp_academic_dataset_business file.
 * It then stores the business id  along with the attributes for the business in the yelp_academic_dataset_business_attributes collection.
 * 
 */
		
public class ReadBusinessDatasetForCategory {
	static String business_id="";
	static int count=0;
	
	public static void main(String args[]) throws IOException{
//		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
//        DB db = mongoClient.getDB( "test" );
//		DBCollection coll =db.getCollection("yelp_academic_dataset_business_attributes");
		HashMap<String,Object> business_attributes=new HashMap<String,Object>();
		JsonFactory jfactory = new JsonFactory();
		boolean flag=false;
		JsonParser jParser = jfactory.createJsonParser(new File("C:\\Users\\Ramakant Khandel\\Desktop\\Course\\Information Retrieval\\Project\\yelp_dataset\\yelp_academic_dataset_business.json"));
		LinkedHashMap<String, Object> attributes =new LinkedHashMap<String,Object>();
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
        	else if(jParser.getText()!=null && jParser.getText().equalsIgnoreCase("hours")){
				jParser.nextToken();
				while ((jParser.nextToken() != JsonToken.END_OBJECT )){
					//System.out.println(jParser.getText());
					jParser.nextToken();
					while ((jParser.nextToken() != JsonToken.END_OBJECT )){
					}
				}
			}
        	else if(jParser.getText()!=null && jParser.getText().equalsIgnoreCase("categories")){
				jParser.nextToken();
				while ((jParser.nextToken() != JsonToken.END_ARRAY )){
					//System.out.println(jParser.getText());
					if(jParser.getText().equalsIgnoreCase("restaurants")){
						flag=true;
					}
				}
			}
//        	else if(jParser.getText()!=null && jParser.getText().equalsIgnoreCase("categories")){
//				jParser.nextToken();
//				//HashSet<String> categories=new HashSet<String> ();
//				while ((jParser.nextToken() != JsonToken.END_ARRAY)){
//					//System.out.println(jParser.getText());
//					//categories.add(jParser.getText());
//				}
//				//attributes.put("categories",categories);
//			}
        	
        	else if(jParser.getText()!=null && jParser.getText().equalsIgnoreCase("attributes")&& flag){
				jParser.nextToken();
				while ((jParser.nextToken() != JsonToken.END_OBJECT )){
					if(jParser.getText().equalsIgnoreCase("Good for")){
						jParser.nextToken();
						LinkedHashMap<String,String> good_for=new LinkedHashMap<String,String> ();
						
						while ((jParser.nextToken() != JsonToken.END_OBJECT )){
							String key=jParser.getText();
							jParser.nextToken();
							String value= jParser.getText();
							//System.out.println(key +"  "+value);
							if(value.length()>0 && !value.equalsIgnoreCase("{")){
								good_for.put(key, value);
							}
						}
						if(good_for.size()>0){
							attributes.put("good_for", good_for);
						}
					}
					else if(jParser.getText().equalsIgnoreCase("ambience")){
						jParser.nextToken();
						LinkedHashMap<String,String> ambience=new LinkedHashMap<String,String> ();
						while ((jParser.nextToken() != JsonToken.END_OBJECT )){
							String key=jParser.getText();
							jParser.nextToken();
							String value= jParser.getText();
							//System.out.println(key +"  "+value);
							if(value.length()>0&& !value.equalsIgnoreCase("{")){
								ambience.put(key, value);
							}
						}
						if(ambience.size()>0){
							attributes.put("ambience", ambience);
						}
					}
					else if(jParser.getText().equalsIgnoreCase("music")){
						jParser.nextToken();
						LinkedHashMap<String,String> music=new LinkedHashMap<String,String> ();
						
						while ((jParser.nextToken() != JsonToken.END_OBJECT )){
							String key="music "+jParser.getText();
							jParser.nextToken();
							String value= jParser.getText();
							//System.out.println(key +"  "+value);
							if(value.length()>0&& !value.equalsIgnoreCase("{")){
								music.put(key, value);
							}
						}
						if(music.size()>0){
							attributes.put("music", music);
						}
					}
					else if(jParser.getText().equalsIgnoreCase("parking")){
						jParser.nextToken();
						LinkedHashMap<String,String> parking=new LinkedHashMap<String,String> ();
						
						while ((jParser.nextToken() != JsonToken.END_OBJECT )){
							String key=jParser.getText();
							jParser.nextToken();
							String value= jParser.getText();
							//System.out.println(key +"  "+value);
							if(value.length()>0&& !value.equalsIgnoreCase("{")){
								parking.put(key, value);
							}
						}
						if(parking.size()>0){
							attributes.put("parking", parking);
						}
					}
					else{
						String key=jParser.getText();
						jParser.nextToken();
						String value= jParser.getText();
						if(value.length()>0&& !value.equalsIgnoreCase("{")){
							attributes.put(key, value);
						}
					}
				}
			}
        	else if(jParser.getText()!=null && jParser.getText().equalsIgnoreCase("neighborhoods")){
				jParser.nextToken();
				while ((jParser.nextToken() != JsonToken.END_ARRAY )){
					//System.out.println(jParser.getText());
				}
			}
        	
        	if(business_id.length()>0 && attributes.size()>0 && flag){
        		System.out.println(business_id);
        		System.out.println(attributes);
        		business_attributes.put(business_id, attributes);
        		//System.out.println(categories);
        		
        		business_id="";
        		attributes=new LinkedHashMap<String,Object> ();
        		flag=false;
        	}

		}
		jParser.close();
		System.out.println(count);
		try{
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	         // Now connect to your databases
	        DB db = mongoClient.getDB( "test11" );
	        DBCollection coll =db.getCollection("yelp_academic_dataset_business_attributes");
			for(String key:business_attributes.keySet()){
				BasicDBObject doc = new BasicDBObject();
				doc.append("business_id",key);
				doc.append("attributes",business_attributes.get(key));
				coll.insert(doc);
			}
			
		}
		catch(Exception e){
			
		}
   }
	
	
	
}
