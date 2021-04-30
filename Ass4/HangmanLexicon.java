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

import acm.util.*;
import acmx.export.java.io.FileReader;

public class HangmanLexicon {
	
	private ArrayList<String> words;
	
	public HangmanLexicon() { 
			words = new ArrayList<String>();
			BufferedReader rd;
			String word;
			try {
				rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
				while(true) {
					word = rd.readLine();
					if(word == null) {
						rd.close();
						break;
					}
					words.add(word);
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
}
