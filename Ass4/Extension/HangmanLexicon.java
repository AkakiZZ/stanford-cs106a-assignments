/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import acm.util.*;
import acmx.export.java.io.FileReader;

public class HangmanLexicon {
	
	private ArrayList<String> words;
	private ArrayList<String> hints;
	
	public HangmanLexicon() { 
			words = new ArrayList<String>();
			hints = new ArrayList<String>();
			BufferedReader rd;
			String line = "";
			String word = "";
			String hint = "";
			try {
				rd = new BufferedReader(new FileReader("HintedLexicon.txt"));
				while(true) {
					line = rd.readLine();
					for (int i = 0; i < line.length(); i++) {
						if (line.charAt(i) == ',') {
							word = line.substring(0, i);
							hint = line.substring(i + 1);
							break;
						}
					}
					words.add(word);
					hints.add(hint);
					if(line == null) {
						rd.close();
						break;
					}
				}
			} catch (Exception e) {		
			}	
	} 

/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return words.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return words.get(index);
	}
	
	public String getHint(int index) {
		return hints.get(index);
	}
}
