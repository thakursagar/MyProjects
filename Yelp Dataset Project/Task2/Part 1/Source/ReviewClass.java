import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;


public class ReviewClass {
	static MongoClient mongoClient;
	static DB db;
	static String[] differentCategories = {"Ambience","Food","Service"};
	static{
		try{
			
			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDB("test");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//takes as input noun text and category
	public static boolean reviewforClass(String nounsentence, String CollectionName){
		DBCollection collList = db.getCollection(CollectionName);
		DBCursor cursor = collList.find();
		while (cursor.hasNext()){
			BasicDBObject doc = (BasicDBObject) cursor.next();
			//System.out.println(doc.size());
			ArrayList<Object> arrListObj=(ArrayList<Object>) doc.get("wordsList");
			if(arrListObj!=null && arrListObj.size()>0){
				//System.out.println((arrListObj).contains(nounsentence));
				return (arrListObj.contains(nounsentence.toLowerCase()));
			}
		}
		return false;
	}

	//returns a string of the categories are spoken about the review Text
	public static String findAllCategories(String reviewText){
		ArrayList<String> nounsText = GenerateRating.POSTaggerNoun(GenerateRating.ReplaceDotsInSentence(reviewText));
		System.out.println(nounsText);
		StringBuffer categories = new StringBuffer("");
		for(int categoriesIndex = 0; categoriesIndex < differentCategories.length; categoriesIndex++){
			for(String word:nounsText){
				if(reviewforClass(word, differentCategories[categoriesIndex])){
					categories.append(differentCategories[categoriesIndex]+" ");
					break;
				}
					
			}
		}
		return(categories.toString().trim());
	}
	
	public static void main(String[] args) {
		/*System.out.println("place: "+reviewForAmbience("place"));
		System.out.println("food: "+reviewForFood("food"));
		System.out.println("service: "+reviewForService("service"));
		*/
		System.out.println(reviewforClass("place", "Ambience"));
		System.out.println(reviewforClass("food", "Food"));
		System.out.println(reviewforClass("wait staff", "Service"));
		
		System.out.println(findAllCategories("Pretty good dinner with a nice selection of food. Open 24 hours and provide nice service. I usually go here after a night of partying. My favorite dish is the Fried Chicken Eggs Benedict."));
		//Enjoyable experience for the whole family. The wait staff was courteous and friendly; the food was reasonably priced and a good value. A word of advice-LEAVE ROOM FOR DESSERT.the deserters are great but huge. Plan to bring some home
		System.out.println(findAllCategories("Enjoyable experience for the whole family. The wait staff was courteous and friendly; the food was reasonably priced and a good value. A word of advice-LEAVE ROOM FOR DESSERT.the deserters are great but huge. Plan to bring some home"));
	}
}
