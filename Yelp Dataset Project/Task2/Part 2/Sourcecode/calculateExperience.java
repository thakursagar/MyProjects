import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONObject;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;


public class calculateExperience {
	static StanfordCoreNLP pipeline;

	public static void init() {
		pipeline = new StanfordCoreNLP("MyPropFile.properties");
	}

	//This function finds the sentiment for a given sentence and returns the type of sentiment.
	public static String findSentiment(String tweet) {
		String sentiments = "";
		int mainSentiment = 0;
		if (tweet != null && tweet.length() > 0) {
			int longest = 0;
			Annotation annotation = pipeline.process(tweet);
			for (CoreMap sentence : annotation
					.get(CoreAnnotations.SentencesAnnotation.class)) {
				sentiments = sentence
						.get(SentimentCoreAnnotations.ClassName.class);
				//System.out.println(sentiments);
				Tree tree = sentence
						.get(SentimentCoreAnnotations.AnnotatedTree.class);
				int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
				String partText = sentence.toString();
				if (partText.length() > longest) {
					mainSentiment = sentiment;
					longest = partText.length();
				}

			}
		}
		return sentiments;
	}

	//This function is used to break the review into sentences and it stores it in an arraylist
	public static ArrayList<String> breakSentence(String review) {
		BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
		// String source = "My friend, Mr. Jones, has a new dog.";
		iterator.setText(review);
		ArrayList<String> arrList = new ArrayList<String>();
		int start = iterator.first();
		for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator
				.next()) {
			// System.out.println(review.substring(start,end));
			arrList.add(review.substring(start, end));
		}
		//System.out.println(arrList);
		return arrList;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, HashMap<String, Integer>> experienceMap = new HashMap<String, HashMap<String, Integer>>(); // map which stores business_id as the key and its value is a map which stores the type of sentiment as the key and the percentage as its value
		HashMap<String, HashMap<String, Double>> experienceMapPercent = new HashMap<String, HashMap<String, Double>>();
		
		String line = "";
		init();
		//read the input file of reviews
		BufferedReader br = new BufferedReader(
				new FileReader(
						"E:/Z534 Info Retrieval/yelp_dataset_challenge_academic_dataset/yelp_dataset_challenge_academic_dataset~/test_reviews.json"));
		while ((line = br.readLine()) != null) {
			JSONObject obj = new JSONObject(line);
			String business_id = (String) obj.get("business_id");
			String text = (String) obj.get("text");
			ArrayList<String> sentences = breakSentence(text);
			//System.out.println(sentences);
			for (String k : sentences) {

				String sentiment = findSentiment(k);
				if (experienceMap.containsKey(business_id)) {
					HashMap<String, Integer> tempMap = experienceMap
							.get(business_id);
					if (tempMap.containsKey(sentiment)) {
						Integer count = tempMap.get(sentiment);
						count++;
						tempMap.put(sentiment, count);
					} else {
						tempMap.put(sentiment, 1);

					}
				} else {
					HashMap<String, Integer> tempMap = new HashMap<String,Integer>();
					tempMap.put(sentiment, 1);
					experienceMap.put(business_id, tempMap);
				}
			}
		}
		for (String key : experienceMap.keySet()) {
			//System.out.println(key + " : " + experienceMap.get(key));
		}
		for (String key : experienceMap.keySet()) {
			HashMap<String,Integer> temp=experienceMap.get(key);
			int count=0;
			for(String e:temp.keySet()){
				count+=temp.get(e);
			}
			HashMap<String, Double> percent=new HashMap<String,Double>();
			for(String e:temp.keySet()){
				double per=((double)temp.get(e)/count)*100;
				percent.put(e, per);
			}
			experienceMapPercent.put(key,percent);
		}
		for (String key : experienceMapPercent.keySet()) {
			System.out.println(key + " : " + experienceMapPercent.get(key));
		}
		
	}
}