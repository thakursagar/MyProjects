import java.util.ArrayList;
import java.util.TreeMap;


public class Heuristics {
	public TreeMap<Double, ArrayList<Integer>> calculateEuclideanDistance(int data[][]){
		TreeMap<Double, ArrayList<Integer>> eucDist = new TreeMap<Double, ArrayList<Integer>>();
		for(int i=0; i<data.length; i++){
			double value = 0;
			for(int j=0; j<data[0].length; j++){
				if(data[i][0]==0 && data[i][1]==0 && data[i][2]==0 && data[i][3]==0 && data[i][4]==0 && data[i][5]==0 && data[i][6]==0)
					return eucDist;
				value += data[i][j] * data[i][j];
			}
			double key = Math.sqrt(value);
			if(eucDist.containsKey(key)){
				ArrayList<Integer> getIndices = eucDist.get(key);
				getIndices.add(i);
				eucDist.put(key, getIndices);
			}
			else{
				ArrayList<Integer> putIndex = new ArrayList<Integer>();
				putIndex.add(i);
				eucDist.put(key, putIndex);
			}
			
		}
		return eucDist;
	}
}
