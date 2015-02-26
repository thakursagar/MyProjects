import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import opennlp.tools.cmdline.postag.POSModelLoader;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

class Rating{
	double service;
	double food;
	double ambience;
	
	
	public Rating(double service, double food, double ambience) {
		super();
		this.service = service;
		this.food = food;
		this.ambience = ambience;
	}
	public double getService() {
		return service;
	}
	public void setService(double service) {
		this.service = service;
	}
	public double getFood() {
		return food;
	}
	public void setFood(double food) {
		this.food = food;
	}
	public double getAmbience() {
		return ambience;
	}
	public void setAmbience(double ambience) {
		this.ambience = ambience;
	}
	
	
}

public class DisplayRatingTask2 {
	
	static MongoClient mongoClient;
	static DB db;
	static DBCollection ratings_coll;
	static HashMap<String, Object> manualRatings  = new HashMap<>();
	static{
		try{
			mongoClient = new MongoClient( "localhost" , 27017 );
			db = mongoClient.getDB( "test" );
			ratings_coll = db.getCollection("yelp_rank_restaurants");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	//function that reads the collection yelp_rank_restaurants and displays the results.
	public static void displayRating() {
		System.out.println("\t\tService\tFood\tAmbience");
		DBCursor cursor = ratings_coll.find();
		while(cursor.hasNext()){
			int userScore = 0, nlpScore = 0;
			BasicDBObject doc = (BasicDBObject) cursor.next();
			for(Map.Entry<String, Object> attribute: doc.entrySet()){
				//System.out.println(attribute.getKey());
				//System.out.println(attribute.getValue());
				if(attribute.getKey().contains("business_id")){
					String businessId = (String) attribute.getValue();
					System.out.println("Business Id: "+businessId);
					Rating rating = (Rating) manualRatings.get(businessId);
					System.out.print("ManualRating\t"+rating.getService()+"\t"+rating.getFood()+"\t"+rating.getAmbience());
				}
				if((attribute.getKey().contains("ourRating"))||(attribute.getKey().contains("userRating"))||(attribute.getKey().contains("reviewRating"))){
					
					BasicDBObject ourRatings = (BasicDBObject) attribute.getValue();
					System.out.print(attribute.getKey());
					if(attribute.getKey().contains("nlpRank")){
						System.out.print("\t");
					}
					for(Map.Entry<String, Object> rating:ourRatings.entrySet()){
						if((rating.getKey().contains("Service")) || (rating.getKey().contains("Food")) || (rating.getKey().contains("Ambience"))){
							System.out.print("\t"+(String.format("%.2f", rating.getValue())));
							
						}
					}	
				}
				
				System.out.println();
			}
		}
	}
	
	//storing the manual results
	public static void storeManualResults() {
		manualRatings.put("JwUE5GmEO-sH1FuwJgKBlQ",new Rating(3.7,3.3,4.15));
		manualRatings.put("uGykseHzyS5xAMWoN6YUqA", new Rating(2.5,3,1.5));
		manualRatings.put("rdAdANPNOcvUtoFgcaY9KA", new Rating(3.64,3.83,3.78));
		manualRatings.put("MKsb2VpLB-0UBODcInDsSw", new Rating(0.5,2.92,0.0));
		manualRatings.put("77ESrCo7hQ96VpCWWdvoxg", new Rating(4.05,3.19,3.5));
		manualRatings.put("jqo3Ljexof9sA8PhSTwEjA", new Rating(2.5,3.35,3.42));
		manualRatings.put("KPoTixdjoJxSqRSEApSAGg", new Rating(3.85,3.55,4));
		manualRatings.put("_RBUU1y4yJrK0SPAd8z0-w", new Rating(2.86, 3.32, 2.5 ));//
		manualRatings.put("LYyGQgL60VKdV-p_9OxmWQ", new Rating(2.86, 2.95, 2.4));
		manualRatings.put("UxMnY3Dxafucv5cXHyfBSA", new Rating(2, 3.29, 2));
		manualRatings.put("3DMvGD8ZmlMQmhwV66hdSA", new Rating(4, 3.5, 2.67));
		
		
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		storeManualResults();
		displayRating();
	}

}
