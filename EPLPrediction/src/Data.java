import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


public class Data {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		Data dat = new Data();
		int data[][] = dat.read();
		Heuristics heu = new Heuristics();
		TreeMap<Double, ArrayList<Integer>> heuristicsMap = heu.calculateEuclideanDistance(data);
		for(double key : heuristicsMap.keySet()){
			System.out.println(key + "-" + heuristicsMap.get(key));
		}
		//ReadInput ri = new ReadInput();
		//double inputEucDistance = ri.readInput();
		System.out.println("Enter the details for which prediction is to be done.");
		System.out.println("Home team name: ");
		String home = sc.nextLine();
		System.out.println("Away team name: ");
		String away = sc.nextLine();
		System.out.println("Enter Goal Difference w.r.t home team: ");
		int gd = sc.nextInt();
		System.out.println("Enter number of players(from starting xi) injured/banned for this match for the home team: ");
		int home_injured = sc.nextInt();
		System.out.println("Enter number of players(from starting xi) injured/banned for this match for the away team: ");
		int away_injured = sc.nextInt();
		System.out.println("Number of wins for the home team in the last 5 matches: ");
		int home_last5_wins = sc.nextInt();
		System.out.println("Number of draws for the home team in the last 5 matches: ");
		int home_last5_draws = sc.nextInt();
		System.out.println("Number of losses for the home team in the last 5 matches: ");
		int home_last5_loss = sc.nextInt();
		System.out.println("Number of wins for the away team in the last 5 matches: ");
		int away_last5_wins = sc.nextInt();
		System.out.println("Number of draws for the away team in the last 5 matches: ");
		int away_last5_draws = sc.nextInt();
		System.out.println("Number of losses for the away team in the last 5 matches: ");
		int away_last5_loss = sc.nextInt();
		System.out.println("Number of wins for the home team against the same opponent from their last 5 meetings: ");
		int winsSameOpponent = sc.nextInt();
		System.out.println("Number of draws for the home team against the same opponent from their last 5 meetings: ");
		int drawsSameOpponent = sc.nextInt();
		System.out.println("Number of losses for the home team against the same opponent from their last 5 meetings: ");
		int lossSameOpponent = sc.nextInt();	



		double inputEucDistance = Math.sqrt(gd*gd + home_injured*home_injured + away_injured*away_injured + home_last5_wins*home_last5_wins
				+ home_last5_draws*home_last5_draws + home_last5_loss*home_last5_loss + away_last5_wins*away_last5_wins
				+ away_last5_draws*away_last5_draws + away_last5_loss*away_last5_loss + winsSameOpponent*winsSameOpponent
				+ drawsSameOpponent*drawsSameOpponent + lossSameOpponent*lossSameOpponent);
		TreeMap<Double, ArrayList<Integer>> distanceSubtract = dat.subtractDistances(heuristicsMap, inputEucDistance);
		Prediction prediction = new Prediction();
		int result = prediction.getPrediction(distanceSubtract, data);
		if(result == -1)
			System.out.println("Error!");
		else{
			System.out.println(result);
		}
		CSVWriter writer = new CSVWriter(new FileWriter("E:/B552 Knowledge Based AI/Project/predictions.csv",true),',',CSVWriter.NO_QUOTE_CHARACTER);
		String [] write = new String[15];
		write[0] = home;
		write[1] = away;
		write[2] = result+"";
		write[3] = gd+"";
		write[4] = home_injured+"";
		write[5] = away_injured+"";
		write[6] = home_last5_wins+"";
		write[7] = home_last5_draws+"";
		write[8] = home_last5_loss+"";
		write[9] = away_last5_wins+"";
		write[10] = away_last5_draws+"";
		write[11] = away_last5_loss+"";
		write[12] = winsSameOpponent + "";
		write[13] = drawsSameOpponent+"";
		write[14] = lossSameOpponent+"";
		writer.writeNext(write);
		writer.close();
	}
	public TreeMap<Double, ArrayList<Integer>> subtractDistances(
			TreeMap<Double, ArrayList<Integer>> heuristicsMap,
			double inputEucDistance) {
		// TODO Auto-generated method stub
		TreeMap<Double, ArrayList<Integer>> eucDistSubtract = new TreeMap<Double, ArrayList<Integer>>();
		for(double key : heuristicsMap.keySet()){
			double newKey = Math.abs(inputEucDistance - key);
			eucDistSubtract.put(newKey, heuristicsMap.get(key));			
		}
		return eucDistSubtract;
	}
	public int[][] read() throws IOException{
		CSVReader reader = new CSVReader(new FileReader("E:/B552 Knowledge Based AI/Project/premierleague.csv"));
		List<String[]> myEntries = reader.readAll();
		int i=0, k=0;
		int [][]data = new int[100][13];
		for(String[] s : myEntries){
			if(k!=0){
				for(int j=0; j<s.length; j++){
					if(j==0 || j==1){
						System.out.println(s[j]);
					}

					else{
						System.out.println(s[j]);
						data[i-1][j-2] = Integer.parseInt(s[j].trim());

					}

				}

			}
			k++;
			i++;
		}
		CSVReader readerPredictions = new CSVReader(new FileReader("E:/B552 Knowledge Based AI/Project/predictions.csv"));
		List<String[]> predictionEntries = readerPredictions.readAll();
		for(String[] str : predictionEntries){

			for(int j=0; j<str.length; j++){
				if(j==0 || j==1){
					System.out.println(str[j]);
				}

				else{
					System.out.println(str[j]);
					data[i-1][j-2] = Integer.parseInt(str[j].trim());

				}

			}


		}
		System.out.println(Arrays.deepToString(data));

		return data;

		//		for(int j=0; i<data.length; i++){
		//			for(int m=0; m<data[0].length; m++){
		//				System.out.println(Arrays.deepToString(data));
		//			}
		//		}
	}


}

