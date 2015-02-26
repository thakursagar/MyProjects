

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class AmbienceCollection {

	public static void main(String[] args) throws Exception {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		//db - database name
		DB db = mongoClient.getDB("test");
		DBCollection businessColl = db
				.getCollection("Ambience");
		HashSet<String> ambience=new HashSet<String>();
		/*
		 * words related to ambience
		 */
		ambience.add("romantic");
		ambience.add("intimate");
		ambience.add("touristy");
		ambience.add("hipster");
		ambience.add("divey");
		ambience.add("classy");
		ambience.add("trendy");
		ambience.add("upscale");
		ambience.add("asual");
		ambience.add("TV");
		ambience.add("outdoor-seating");
		ambience.add("dj");
		ambience.add("live");
		ambience.add("jukebox");
		//some more elements
		//"place, area, atmoshpere, family, night, experience, table")
		ambience.add("place");
		ambience.add("area");
		ambience.add("atmosphere");
		ambience.add("family");
		ambience.add("experience");
		ambience.add("table");
		/*
		 * indianfood
		 */
		
//		ambience.add("paneer tikka masala");
//		ambience.add("panner burji");
//		ambience.add("pav bhaji");
//		ambience.add("roti");
//		ambience.add("dal");
//		ambience.add("channa masala");
//		ambience.add("mutter paneer");
//		ambience.add("chicken tikka masala");
//		ambience.add("chicken tandoori");
//		
		
		Map<String, Object> documentMap = new HashMap<String, Object>();
		documentMap.put("wordsList", ambience);
		
		businessColl.insert(new BasicDBObject(documentMap));
		
	}

}
