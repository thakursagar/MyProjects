 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InvalidFormatException;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

//Stores the score and count for one category and one business 
class Score{
	double score;
	int count;
	public Score(double score, int count) {
		super();
		this.score = score;
		this.count = count;
	}
	public Score() {
		super();
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}

public class GenerateRating {
	static String token_text = "";
	static StringBuffer noun_text = new StringBuffer();
	static StandardAnalyzer analyzer = new StandardAnalyzer();
	static POSModel model;
	static double count = 1;
	static MongoClient mongoClient;
	static DB db;
	static DBCollection reviews_coll;
	static String[] differentCategories = {"Ambience","Food","Service"};
	static String[] ratingMethods = {"UserScore", "NlpScore"};
/*	static String service = new String("service, staff, friendly, wait staff, time, quick, slow");
	static String ambience = new String("place, area, atmoshpere, family, night, experience, table");
	static String food = new String("dish, fried chicken eggs, snacks, course, cream, desserts, size, pancakes, dinner, "
			+ "breakfast, food, bakery, hash-browns, coffee, bakes, muffin, cream-puffs, eggs"
			+ "");
			
	
*/	
	static HashMap<String, HashMap<String, Score>> reviewScores = new HashMap<>();
	static HashMap<String, HashMap<String, Score>> userScores = new HashMap<>();
	static HashMap<String, HashMap<String, Score>> nlpScores = new HashMap<>();
	
	static{
		try{
			model = new POSModelLoader()
			.load(new File(
					"C:/Users/Admin/Documents/info retrieval/en-pos-maxent.bin"));
	
			mongoClient = new MongoClient( "localhost" , 27017 );
			db = mongoClient.getDB( "test" );
			reviews_coll = db.getCollection("business_reviews_data");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
		

	public static void getTokenizeString(String text) {
		try {
			TokenStream stream = analyzer.tokenStream("feild",
					new StringReader(text));

			stream.reset();
			while (stream.incrementToken()) {
				String x = stream.getAttribute(CharTermAttribute.class)
						.toString();
				// System.out.println(x);
				token_text += " " + x;
			}
			token_text = token_text.trim();
			stream.close();
		} catch (IOException e) {
			// not thrown b/c we're using a string reader...
		}
	}

	public static void getNounPhrases(Parse p) {
		if (p.getType().charAt(0) == 'N') {
			noun_text.append(p.getCoveredText() + " ");
		}

		for (Parse child : p.getChildren()) {
			getNounPhrases(child);
		}
	}

	//Extract the nouns from the given string, returns an arrayList
	public static ArrayList<String> POSTaggerNoun(String str) {
		//ArrayList<String> arrList = new ArrayList<String>();
		ArrayList<String> arrList2 = new ArrayList<String>();
		//String nounStr = "";
		try {
			POSTaggerME tagger = new POSTaggerME(model);
			String[] sent = WhitespaceTokenizer.INSTANCE.tokenize(str);
			String[] tags = tagger.tag(sent);
			
			/*int i = 0;
			System.out.println("tags len: "+tags.length);
			for (String tag : tags) {
				System.out.println(tag);
				if (tag.charAt(0) == 'N') {
					nounStr += sent[i] + " ";
					arrList.add(sent[i]);
				}
				i++;
			}*/
			StringBuffer nounStr = new StringBuffer("");
			boolean prevNoun = false;
			for(int inc = 0; inc<tags.length; inc++){
				if(tags[inc].charAt(0) == 'N'){
					nounStr.append(" "+sent[inc]);
					prevNoun = true;
				}
				else{
					if(prevNoun == true){
						arrList2.add(nounStr.toString().trim());
					}
					nounStr = new StringBuffer("");
					prevNoun = false;
				}
			}
			if(prevNoun == true){
				arrList2.add(nounStr.toString().trim());
			}
			//prints arrayList of noun in the senetence
			/*for(int itr =0;itr<arrList2.size(); itr++){
				System.out.println(arrList2.toArray()[itr]);
			}*/
			return arrList2;
		} catch (Exception expObj) {
			System.out.println(expObj.getMessage());
		}
		return null;

	}
	
		//breaks the review into sentences
		public static ArrayList<String> sentenceSplitter(String sentences){
		ArrayList<String> sentList = new ArrayList<String>();
		BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
		//String source = "Food is good, i liked it. Service is bad. Must Try butter chicken.";
		iterator.setText(sentences);
		int start = iterator.first();
		for (int end = iterator.next();
		    end != BreakIterator.DONE;
		    start = end, end = iterator.next()) {
		  //System.out.println(sentences.substring(start,end));
		  sentList.add(sentences.substring(start, end-1));
		}		
		return sentList;
		/*
		 * for(int itr =0;itr<sentList.size(); itr++){
			String sentence = (sentList.toArray()[itr]).toString();
			System.out.println(sentence);
			System.out.println(NLP.findSentiment(sentence));
		}
		 * */
	}

	//calls the file NLP.java and returns the sentiment score.
	public static int sentenceScoreCalculate(String sentence){
		NLP.init();
		int score = (NLP.findSentiment(sentence));
		return score;
	}
	
	//writes the reviews with into the collection 
	public static void writeReviewWithScores(String businessId, HashMap<String, Integer> sentencesScore, int userStars, int reviewSentimentScore, String categories, ArrayList<String> nounsInText){
		BasicDBObject doc = new BasicDBObject();
		doc.put("businessId", businessId);
		doc.put("stars", userStars);
		doc.put("nlpScore", reviewSentimentScore);
		doc.put("categories", categories);
		doc.put("Nouns-recognized", nounsInText);
		BasicDBObject innDoc = new BasicDBObject();
		for(String k:sentencesScore.keySet()){
			int score = sentencesScore.get(k);
			k=k.replace(".", "");
			k=k.replace("\"", "");
			System.out.println("****sentence: "+k+" score: "+score);
			innDoc.put(k, score);
		}
		doc.put("Reviews", innDoc);
		reviews_coll.insert(doc);
		

	}
	
	
	//function performs reading the reviews and processing it
	public static void ReadReviews(){
		try{
			BufferedReader br = new BufferedReader(
					new FileReader(
							"C:\\Users\\Admin\\Documents\\info retrieval\\test_reviews.json"));
			String line = "";
			int i=0;
			while ((line = br.readLine()) != null) {
				System.out.println(i++);
				JSONObject obj = new JSONObject(line);
				String business_id = (String) obj.get("business_id");
				int userStars = (int)obj.get("stars");
				String text = (String) obj.get("text");
				ArrayList<String> nounsInText = POSTaggerNoun(ReplaceDotsInSentence(text));
				String categories = ReviewClass.findAllCategories(text);
				int reviewSentimentScore = sentenceScoreCalculate(ReplaceDotsInSentence(text))+1;
				ArrayList<String> sentList = sentenceSplitter(text);
				HashMap<String, Integer> sentencesScore= new HashMap<String, Integer>();
				System.out.println("In Read reviews function");
				System.out.println("Business Id"+business_id);
				System.out.println("Stars:"+userStars);
				for(String str:sentList){
					int score = sentenceScoreCalculate(str)+1;
					sentencesScore.put(str, score);
					System.out.println("---------In Main, sentence: "+str+" score: "+score+"--------------");
				}
//				for(String k:sentencesScore.keySet()){
//					System.out.println(k+" : "+sentencesScore.get(k));
//				}
				writeReviewWithScores(business_id, sentencesScore, userStars, reviewSentimentScore, categories, nounsInText);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String ReplaceDotsInSentence(String text) {
		return text.replace(".", "");
	}
	
	//calling the method to call restaurants and perform analysis of the reviews.
	public static void getBusinessIds(){
		generateRatingsForOneBusiness("JwUE5GmEO-sH1FuwJgKBlQ");
		generateRatingsForOneBusiness("uGykseHzyS5xAMWoN6YUqA");
		generateRatingsForOneBusiness("rdAdANPNOcvUtoFgcaY9KA");
		generateRatingsForOneBusiness("MKsb2VpLB-0UBODcInDsSw");
		generateRatingsForOneBusiness("77ESrCo7hQ96VpCWWdvoxg");
		generateRatingsForOneBusiness("jqo3Ljexof9sA8PhSTwEjA");
		generateRatingsForOneBusiness("KPoTixdjoJxSqRSEApSAGg");
		generateRatingsForOneBusiness("_RBUU1y4yJrK0SPAd8z0-w");
		generateRatingsForOneBusiness("LYyGQgL60VKdV-p_9OxmWQ");
		generateRatingsForOneBusiness("UxMnY3Dxafucv5cXHyfBSA");
		generateRatingsForOneBusiness("3DMvGD8ZmlMQmhwV66hdSA");
	}
	
	public static void generateRatingsForOneBusiness(String businessId){
		BasicDBObject bd = new BasicDBObject();
		String business_id=businessId;
		//business_id="JwUE5GmEO-sH1FuwJgKBlQ"
		bd.put("businessId", business_id);
		DBCursor cursor = reviews_coll.find(bd);
		
		while(cursor.hasNext()){
			int userScore = 0, nlpScore = 0;
			BasicDBObject doc = (BasicDBObject) cursor.next();
			for(Map.Entry<String, Object> attribute: doc.entrySet()){
				//System.out.println(attribute.getKey());
				//System.out.println(attribute.getValue());
				if(attribute.getKey().contains("stars")){
					userScore = (int) attribute.getValue();
				}
				if(attribute.getKey().contains("nlpScore")){
					nlpScore = (int) attribute.getValue();
				}
				if(attribute.getKey().contains("categories")){
					String categories = (String) attribute.getValue();
					System.out.println(categories);
					for(String category: differentCategories){
						if(categories.contains(category)){
							System.out.println("In categories"+category);
							System.out.println("userScore: "+userScore);
							System.out.println("nlpScore: "+nlpScore);
							if(userScores.get(business_id)==null){
								HashMap<String,Score> userMap=new HashMap<String,Score>();
								HashMap<String,Score> nlpMap=new HashMap<String,Score>();
								Score usrScr=new Score(userScore,1);
								Score nlpScr=new Score(nlpScore, 1);
								
								userMap.put(category, usrScr);
								userScores.put(business_id, userMap);
								
								nlpMap.put(category, nlpScr);
								nlpScores.put(business_id, nlpMap);
								
							}
							else{
								HashMap<String,Score> usrMap=userScores.get(business_id);
								HashMap<String,Score> nlpMap=nlpScores.get(business_id);
								if(usrMap.get(category)==null){
									usrMap.put(category, new Score(userScore,1));
									nlpMap.put(category, new Score(nlpScore,1));
								}
								else{
									Score usrScr=usrMap.get(category);
									Score nlpScr=nlpMap.get(category);
									
									usrScr.setScore(usrScr.getScore()+userScore);
									usrScr.setCount(usrScr.getCount()+1);
									nlpScr.setScore(nlpScr.getScore()+userScore);
									nlpScr.setCount(nlpScr.getCount()+1);
									
								}
							}
				
						}
					}
				}
				if(attribute.getKey().contains("Reviews")){
					BasicDBObject reviewsDoc = (BasicDBObject) attribute.getValue();
					for(Map.Entry<String, Object> reviews:reviewsDoc.entrySet()){
						ArrayList<String> sentenceNoun = POSTaggerNoun(reviews.getKey());
						int sentenceScore = (int) reviews.getValue();
						for(String collection:differentCategories){
							for(String word:sentenceNoun){
								boolean flag=ReviewClass.reviewforClass(word,collection);
								if(flag){
									if(reviewScores.get(business_id)==null){
										HashMap<String,Score> s=new HashMap<String,Score>();
										Score scr=new Score(sentenceScore,1);
										s.put(collection, scr);
										reviewScores.put(business_id, s);
									}
									else{
										HashMap<String,Score> s=reviewScores.get(business_id);
										if(s.get(collection)==null){
											s.put(collection, new Score(sentenceScore,1) );
										}
										else{
											Score scr=s.get(collection);
											scr.setScore(scr.getScore()+sentenceScore);
											scr.setCount(scr.getCount()+1);
										}
									}
									break;
								}
								
							}
						}
						System.out.println("Sentence given: ");
						System.out.println(POSTaggerNoun("sentence Score: "+reviews.getKey())+":"+reviews.getValue());
						HashMap<String,Score> tt=reviewScores.get(business_id);
						if(tt!=null){
							
							for(String k:tt.keySet()){
								System.out.println(k+" : "+tt.get(k).getCount()+" : "+tt.get(k).getScore());
							}
						}
						
					}
				}
			}
		}
		System.out.println("Total number of documents: "+cursor.count());
	}
	

	/*
	 * reviewRank - ourRating
	 * userRank - userRating
	 * nlpRank - review*/
	public static void insertRatingsIntoMongoDB(){
		DBCollection rankBusiness=db.getCollection("yelp_rank_restaurants");
		for(String business_id:reviewScores.keySet()){
			BasicDBObject bd=new BasicDBObject();
			bd.put("business_id", business_id);
			
			HashMap<String,Object> reviewRank=new HashMap<String,Object>();
			HashMap<String,Object> userRank=new HashMap<String,Object>();
			HashMap<String,Object> nlpRank=new HashMap<String,Object>();
			
			for(String key:reviewScores.get(business_id).keySet()){
				double reviewScore = ((reviewScores.get(business_id).get(key).getScore())/(reviewScores.get(business_id).get(key).getCount()));
				reviewRank.put(key, reviewScore);
				
				double userScore = ((userScores.get(business_id).get(key).getScore())/(userScores.get(business_id).get(key).getCount()));
				userRank.put(key, userScore);
				
				double nlpScore = ((nlpScores.get(business_id).get(key).getScore())/(nlpScores.get(business_id).get(key).getCount()));
				nlpRank.put(key, nlpScore);
				
			}
			bd.put("ourRating", reviewRank);
			bd.put("userRating", userRank);
			bd.put("reviewRating", nlpRank);
			rankBusiness.insert(bd);
		}
	}
	
	public static void main(String[] args) {
		try{
			POSTaggerNoun("Food is good, i liked it. Service is bad. Must Try butter chicken");
			//System.out.println("nouns: ");
			//System.out.println(noun_text);
			
			//sentenceSplitter("Food is good, i liked it. Service is bad. Must Try butter chicken.");
			//sentenceSplitter("Pretty good dinner with a nice selection of food. Open 24 hours and provide nice service. I usually go here after a night of partying. My favorite dish is the Fried Chicken Eggs Benedict.");
			//System.out.println(sentenceScoreCalculate("That place now sits empty"));
			
			
			//operation performs the reading of reviews and storing the reviews with corresponding scores
			
			
			ReadReviews();
			
			getBusinessIds();
			insertRatingsIntoMongoDB();
		
/*			NLP.init();
			System.out.println(NLP.findSentiment("coconut soup, vegetable korma, malai kofta, baingan bhartha, chicken tikka masala, mango ice cream  The staff is wonderful and always keeps plates of naan a plenty :)  Maharaja is by far one of Madison's best restaurants"));
*/
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
		
		/*
		*/
		
/*		BufferedReader br = null;

		try {
			String jsonData = "";
			File file[] = new File[15];
			FileWriter fw[] = new FileWriter[15];
			BufferedWriter bw[] = new BufferedWriter[15];
			for (int i = 0; i < 15; i++) {
//				file[i] = new File(
//						"C:/Users/Ramakant Khandel/Desktop/Course/Information Retrieval/Data/"
//								+ (i + 1) + ".txt");
				String fileName="C:/Users/Ramakant Khandel/Desktop/Course/Information Retrieval/Data/"
						+ (i + 1) + ".txt";
				fw[i] = new FileWriter(fileName, true);
				bw[i] = new BufferedWriter(fw[i]);

			}

			String line;
			br = new BufferedReader(
					new FileReader(
							"C:\\Users\\Ramakant Khandel\\Desktop\\Course\\Information Retrieval\\Project\\yelp_dataset\\yelp_academic_dataset_tip.json"));
			while ((line = br.readLine()) != null) {
				JSONObject obj = new JSONObject(line);
				String business_id = (String) obj.get("business_id");
				String text = (String) obj.get("text");
				if (business_id.length() > 0 && text.length() > 0) {
					if (text.length() > 2) {
//						getTokenizeString(text);
//						if (token_text.length() > 0) {
							POSTaggerNoun(text);
							int a = business_id
									.charAt(business_id.length() - 1);
							int b = business_id
									.charAt(business_id.length() - 2);
							int no = (a + b) % 15;
							bw[no].write(business_id + "|" + noun_text + "\n");
						//}

					}
					System.out.println(count++);
					token_text = "";
					noun_text = new StringBuffer();
					// jsonData="";

				}
			}
			for (int i = 0; i < 15; i++) {
				bw[i].close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();1
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		// System.out.println("File Content: \n" + jsonData);
*/
	}

}
