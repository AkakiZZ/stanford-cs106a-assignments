/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.util.ArrayList;

public class Hangman extends ConsoleProgram {
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private HangmanLexicon lexicon = new HangmanLexicon();
	private HangmanCanvas canvas;
	//counts how many turns are left
	private int turnsLeft = 8;
	
	//initialize
	public void init() { 
		canvas = new HangmanCanvas(); 
		add(canvas); 
	} 

    public void run() {
		printWelcomeMessage();
		playGame();
	}
    
    
	private void playGame() {
		canvas.reset();
		
		//generate radnom word
		String word = lexicon.getWord(rgen.nextInt(lexicon.getWordCount() -  1));	
		
		//generate hidden word
		String hiddenWord = generateDashedText(word.length());
		
		boolean guessIsCorrect = false;
		boolean incorrectInput = false;
		
		canvas.displayWord(hiddenWord);
		println("The word now looks like this: " + hiddenWord);
		
		while (turnsLeft > 0) {
			char guess = getGuess();
			for (int i = 0; i < word.length(); i++) {
				if (guess == 0) {
					incorrectInput = true;
					break;
				} else if (guess == word.charAt(i)) {
					guessIsCorrect = true;
					incorrectInput = false;
					break;
				} else if (guess != word.charAt(i)){
					guessIsCorrect = false;
					incorrectInput = false;
				}
			}
			
			//generate hidden word
			hiddenWord = generateCurrentHiddenWord(hiddenWord, word, guess, guessIsCorrect);
			
			//check for win
			if (playerWon(hiddenWord, word)) {
				println("You Win!");
				println("You guessed the word: " + word);
				canvas.displayWord(word);
				break;
			} else {
				printStates(guessIsCorrect, incorrectInput, guess, hiddenWord);
			}
			canvas.displayWord(hiddenWord);
		}
		
		//print losing message
		if (!playerWon(hiddenWord, word)) {
			println("You Lose.");
			println("The word was: " + word);
			canvas.displayWord(word);
		}
	}

	/*
	 * generates hidden word
	 * hides unguessed letters
	 * shows guessed letters
	 */
	private String generateCurrentHiddenWord(String hiddenWord, String word, char guess, boolean guessIsCorrect) {
		for (int i = 0; i < word.length(); i++) {
			if (guessIsCorrect && guess == word.charAt(i)) {
				hiddenWord = hiddenWord.substring(0, i) + word.charAt(i) + hiddenWord.substring(i + 1);
			}
		}		
		return hiddenWord;
	}

	//prints states in the console
	private void printStates(boolean guessIsCorrect, boolean incorrectInput, char guess, String hiddenWord) {
		if (incorrectInput) {
			println("Incorrect Input.");
		} else if (guessIsCorrect){
			println("That guess is correct.");
		} else {
			println("There are no " + guess + "'s in the word.");
			canvas.noteIncorrectGuess(guess);
			turnsLeft--;
		} 
		println("The word now looks like this: " + hiddenWord);
		println("You have " + turnsLeft + " guesses left.");
	}

	//checks whether player has won
	private boolean playerWon(String hiddenWord, String word) {
		if (hiddenWord.equals(word)) {
			return true;
		}
		return false;
	}

	//generates dashed (hidden) text
	private String generateDashedText(int length) {
		String res = "";
		for (int i = 0; i < length; i++) {
			res += "-";
		}
		return res;
	}

	//lets the user to input a letter
	private char getGuess() {
		String input = readLine("Your Guess Is: ");
		char result = 0;
		if(input.length() == 1 && Character.isLetter(input.charAt(0))) {
			input = input.toUpperCase();
			result = input.charAt(0);
		}
		return result;
	}

	//prints welcome message
	private void printWelcomeMessage() {
    	println("Welcome to Hangman!");
    }

}
