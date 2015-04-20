import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.opencsv.CSVWriter;


public class ReadInput {
	public double readInput() throws IOException{
		Scanner sc = new Scanner(System.in);
		CSVWriter writer = new CSVWriter(new FileWriter("E:/B552 Knowledge Based AI/Project/predictions.csv"));
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
		
		
		double eucdist = Math.sqrt(gd*gd + home_injured*home_injured + away_injured*away_injured + home_last5_wins*home_last5_wins
							+ home_last5_draws*home_last5_draws + home_last5_loss*home_last5_loss + away_last5_wins*away_last5_wins
							+ away_last5_draws*away_last5_draws + away_last5_loss*away_last5_loss + winsSameOpponent*winsSameOpponent
							+ drawsSameOpponent*drawsSameOpponent + lossSameOpponent*lossSameOpponent);
		return eucdist;
	}
	
}
