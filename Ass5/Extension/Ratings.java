import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import acmx.export.java.io.FileReader;

public class Ratings {
	
	//returns formated top results
	public String getRatings() {
		names = new ArrayList<>();
		scores = new ArrayList<>();
		readRatings();
		String result = "";
		for (int i = 0; i < names.size(); i++) {
			result += names.get(i) + " - " + scores.get(i) + " ";
		}
		return result;
	}
	
	/*
	 * adds new ratings from new game into arraylists
	 * adds old ratings from file into arraylist
	 * sorts them
	 * writes new top results in file
	 */
	public void updateRatings(String playerNames[], int playerScores[]) {
		names = new ArrayList<>();
		scores = new ArrayList<>();
		for (int i = 0; i < playerNames.length; i++) {
			names.add(playerNames[i]);
			scores.add(playerScores[i]);
		}
		readRatings();
		ArrayList<Integer> sortedScores = new ArrayList<>();
		ArrayList<String> sortedNames = new ArrayList<>();
		sortList(scores, sortedScores, names, sortedNames);
		writeRatings(sortedScores, sortedNames);
	}
	
	/*
	 * reads existing ratings from file
	 * adds them into arraylists
	 */
	private void readRatings() {
		BufferedReader rd;
		String line = "";
		String name = "";
		int score = 0;
		try {
			rd = new BufferedReader(new FileReader(FILE_NAME));
			while(true) {
				line = rd.readLine();
				if(line == null) {
					rd.close();
					break;
				}
				for (int i = 0; i < line.length(); i++) {
					if (line.charAt(i) == ',') {
						name = line.substring(0, i);
						score = Integer.parseInt(line.substring(i + 1));
						break;
					}
				}
				names.add(name);
				scores.add(score);
			}
		} catch (Exception e) {		
		}	
	}
	
	// writes new top results in file
	private void writeRatings(ArrayList<Integer> sortedScores, ArrayList<String> sortedNames) {
		try {
			FileWriter fw = new FileWriter(FILE_NAME);
			PrintWriter pw = new PrintWriter(fw);
			String line = "";
			
			if (sortedNames.size() < N_RATINGS) {
				for (int i = 0; i < sortedNames.size(); i++) {
					line = sortedNames.get(i) + "," + sortedScores.get(i);
					pw.println(line);
				}
			} else {
				for (int i = 0; i < N_RATINGS; i++) {
					line = sortedNames.get(i) + "," + sortedScores.get(i);
					pw.println(line);
				}
			}
			fw.close();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//sorts data
	private void sortList(ArrayList<Integer> scores, ArrayList<Integer> sortedScores, 
			ArrayList<String> names, ArrayList<String> sortedNames) {
		while (!scores.isEmpty()) {
			int maxNumberIndex = getMaxNumberIndex(scores);
			sortedScores.add(scores.get(maxNumberIndex));
			scores.remove(maxNumberIndex);
			sortedNames.add(names.get(maxNumberIndex));
			names.remove(names.get(maxNumberIndex));
		}
	}
	
	//gets index of maximum number from arraylist
	private int getMaxNumberIndex(ArrayList<Integer> list) {
		int max = list.get(0);
		int maxIndex = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) > max) {
				maxIndex = i;
				max = list.get(i);
			}
		}
		return maxIndex;
	}

	private static final int N_RATINGS = 5;
	private static final String FILE_NAME = "Ratings.txt";
	ArrayList<String> names;
	ArrayList<Integer> scores;
}
