

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class ServiceCollection {

	public static void main(String[] args) throws Exception {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("test");
		DBCollection businessColl = db
				.getCollection("Service");
		HashSet<String> service=new HashSet<String>();
		/*
		 * service data words
		 */
		service.add("take-out");
		service.add("latenight");
		service.add("lunch");
		service.add("dinner");
		service.add("breakfast");
		service.add("brunch");
		service.add("caters");
		service.add("take-reservations");
		service.add("delivery");
		service.add("parking-lot");
		service.add("valet");
		service.add("parking-street");
		service.add("alcohol");
		service.add("waiter");
		service.add("accept-cc");
		service.add("kids");
		service.add("groups");
		service.add("wifi");
		service.add("wheelchair-accessible");
		service.add("smoking");
		service.add("drive-thru");
		service.add("by-appt-only");
		service.add("coat check");
		//some fields added by me
		service.add("service");
		service.add("quick");
		service.add("slow");
		service.add("wait staff");
		service.add("staff");
		service.add("people");
		service.add("place");
		service.add("area");
		service.add("experience");
		/*
		 * ambience data
		 */
//		service.add("romantic");
//		service.add("intimate");
//		service.add("touristy");
//		service.add("hipster");
//		service.add("divey");
//		service.add("classy");
//		service.add("trendy");
//		service.add("upscale");
//		service.add("asual");
//		service.add("TV");
//		service.add("outdoor-seating");
//		service.add("dj");
//		service.add("live");
//		service.add("jukebox");
		/*
		 * indianfood
		 */
		
//		service.add("paneer tikka masala");
//		service.add("panner burji");
//		service.add("pav bhaji");
//		service.add("roti");
//		service.add("dal");
//		service.add("channa masala");
//		service.add("mutter paneer");
//		service.add("chicken tikka masala");
//		service.add("chicken tandoori");
//		
		
		Map<String, Object> documentMap = new HashMap<String, Object>();
		documentMap.put("wordsList", service);
		
		businessColl.insert(new BasicDBObject(documentMap));
		
	}

}
