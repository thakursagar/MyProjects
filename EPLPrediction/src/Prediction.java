import java.util.ArrayList;
import java.util.TreeMap;


public class Prediction {

	public int getPrediction(
			TreeMap<Double, ArrayList<Integer>> distanceSubtract, int[][] data) {
		// TODO Auto-generated method stub
		int count=0;
		int winVote = 0;
		int drawVote = 0;
		int lossVote = 0;
		for(double key : distanceSubtract.keySet()){
			ArrayList<Integer> getIndices = distanceSubtract.get(key);
			if(count > 10)
				break;
			else{
				for(int index : getIndices){
					count++;
					if(data[index][0]==0)
						winVote++;
					else if(data[index][0]==1)
						drawVote++;
					else if(data[index][0]==2)
						lossVote++;
				}
			}
		}
		if(winVote >= drawVote && winVote >= lossVote)
			return 0;
		else if(drawVote > winVote && drawVote >= lossVote)
			return 1;
		else if(lossVote > winVote && lossVote > drawVote)
			return 2;
		return -1;
	}
	
}
