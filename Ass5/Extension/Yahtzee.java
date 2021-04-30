/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.applet.AudioClip;
import java.util.ArrayList;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		diceSound = MediaTools.loadAudioClip("DiceSound.au");
		winningSound = MediaTools.loadAudioClip("WinningSound.au");
		ratings = new Ratings();
		IODialog dialog = getDialog();	
		dialog.println(ratings.getRatings());
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		categories = new int[nPlayers][N_CATEGORIES];
		playerScores = new int[nPlayers][N_CATEGORIES];
		for (int i = 0; i < N_ROUNDS; i++) {
			for (int j = 0; j < nPlayers; j++) {
				display.printMessage(playerNames[j] + "'s turn! Click 'Roll Dice' button to roll the dice");
				rollDice(j + 1);
			}
		}
		checkForWin();
	}
		
	//prints winner and his/her score
    private void checkForWin() {
		int WinnerPlayerIndex = getMaxNumberIndex(playerScores);
		ArrayList<Integer> otherWinners = new ArrayList<>();
		getOtherWinners(otherWinners, playerScores);
		if (otherWinners.size() == 1) {
			display.printMessage("Congratulations, " + playerNames[WinnerPlayerIndex] + ", You are the winner with a total of " + playerScores[WinnerPlayerIndex][TOTAL - 1]);
		} else {
			String str = "";
			for (int i = 0; i < otherWinners.size(); i++) {
				if (i == otherWinners.size() - 1) {
					str += playerNames[otherWinners.get(i)] + " ";
				} else {
					str += playerNames[otherWinners.get(i)] + ", ";
				}
			}
			str += "are the winners with with a total of " + playerScores[WinnerPlayerIndex][TOTAL - 1];	
			display.printMessage(str);
		}
		int scores[] = new int[nPlayers];
		for (int i = 0; i < nPlayers; i++) {
			scores[i] = playerScores[i][TOTAL - 1];
		}
		winningSound.play();
		ratings.updateRatings(playerNames, scores);
	}
    
    //gets other winners if exists 
    private void getOtherWinners(ArrayList<Integer> otherWinners, int[][] arr) {
    	int max = arr[getMaxNumberIndex(arr)][TOTAL - 1];
		for (int i = 0; i < nPlayers; i++) {
			if (arr[i][TOTAL - 1] == max) {
				otherWinners.add(i);
			}
		}	
	}

	//returns index of the maximum number
	private int getMaxNumberIndex(int[][] arr) {
		int max = arr[0][TOTAL - 1];
		int maxIndex = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i][TOTAL - 1] > max) {
				max = arr[i][TOTAL - 1];
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	//controls the rolling of the dice
	private void rollDice(int playerIndex) {
		
    	display.waitForPlayerToClickRoll(playerIndex);
    	int[] rolls = new int[N_DICE];
		for(int i = 0; i < rolls.length; i++) {
			rolls[i] = rgen.nextInt(FIRST_DIE, 2);
		}
		display.displayDice(rolls);
		diceSound.play();
		for(int i = 0; i < N_TRIES; i++) {
			display.printMessage("Select the dice you wish to re-roll and click 'Roll-Again'");
			display.waitForPlayerToSelectDice();
			for(int j = 0; j < rolls.length; j++) {
				if(display.isDieSelected(j)) {
					rolls[j] = rgen.nextInt(FIRST_DIE, 2);
				}
			}
			display.displayDice(rolls);
			diceSound.play();
		}	
		updateScores(rolls, playerIndex);
	}

	/*
	 * updates each array of scores
	 * updates scores on the display
	 */
    private void updateScores(int[] rolls, int playerIndex) {
    	display.printMessage("Select a category for this roll");
    	int category = display.waitForPlayerToSelectCategory();
    	boolean isValid = checkCategory(rolls, category);
    	boolean isYahtzee = checkCategory(rolls, YAHTZEE);
    	while (categories[playerIndex - 1][category - 1] != 0) {
        	display.printMessage("This Category has already been chosen. Select an another category");
    		category = display.waitForPlayerToSelectCategory();
    		isValid = checkCategory(rolls, category);
    	}
    	int score = calculateScore(rolls, isValid, category);
        if(isYahtzee && playerScores[playerIndex - 1][YAHTZEE - 1] != 0) {
    		playerScores[playerIndex - 1][YAHTZEE - 1] += YAHTZEE_BONUS;
    		display.updateScorecard(YAHTZEE, playerIndex, playerScores[playerIndex - 1][YAHTZEE - 1]);
    		display.printMessage("Yahtzee again! +100 scores as a bonus");
    		pause(PAUSE);
    	}
        playerScores[playerIndex - 1][category - 1] += score;
        display.updateScorecard(category, playerIndex, score);
    	updateTotalScores(playerIndex, score);
    	categories[playerIndex - 1][category - 1] = 1;
	}

    /*
	 * updates each array of total scores
	 * updates total scores on the display
	 */
	private void updateTotalScores(int playerIndex, int score) {
		int upperScore = 0;
		int lowerScore = 0;
		int bonus = 0;
		
		for (int i = THREE_OF_A_KIND; i <= CHANCE; i++) {
            lowerScore += playerScores[playerIndex - 1][i - 1];
        }
		playerScores[playerIndex - 1][LOWER_SCORE - 1] = lowerScore;
        display.updateScorecard(LOWER_SCORE, playerIndex, lowerScore);
		
		for (int i = ONES; i <= SIXES; i++) {
            upperScore += playerScores[playerIndex - 1][i - 1];
        }
		playerScores[playerIndex - 1][UPPER_SCORE - 1] = upperScore;
        display.updateScorecard(UPPER_SCORE, playerIndex, upperScore);
        
        if (upperScore >= 63) {
        	playerScores[playerIndex - 1][UPPER_BONUS - 1] = BONUS;
        	bonus = BONUS;
        }
        display.updateScorecard(UPPER_BONUS, playerIndex, bonus);
        
        playerScores[playerIndex - 1][TOTAL - 1] = upperScore + lowerScore + bonus;
        display.updateScorecard(TOTAL, playerIndex, upperScore + lowerScore + bonus);

		
	}

	/*
	 * counts the score
	 * receives as parameters:
	 * dice combinations (rolls array)
	 * isValid variable which tells whether chosen category is valid for the dice combinations
	 * index of the category
	 */
	private int calculateScore(int[] rolls, boolean isValid, int category) {
    	int score = 0;
    	if (!isValid)
    		return 0;
    	if (category >= ONES && category <= SIXES) {
    		for (int i = 0; i < N_DICE; i++) {
                if (rolls[i] == category)
                    score += category;
            }
    	} else if (category == THREE_OF_A_KIND || category == FOUR_OF_A_KIND) {
            for (int i = 0; i < N_DICE; i++) {
                score += rolls[i];
            }
        } else if (category == FULL_HOUSE) {
            score = 25;
        } else if (category == SMALL_STRAIGHT) {
            score = 30;
        } else if (category == LARGE_STRAIGHT) {
            score = 40;
        } else if (category == YAHTZEE) {
            score = 50;
        } else if (category == CHANCE) {
            for (int i = 0; i < N_DICE; i++) {
                score += rolls[i];
            }
        }
		return score;
	}
	
	//checks whether chosen category is valid for the dice combinations
	private boolean checkCategory(int[] rolls, int category) {
		if (category >= ONES && category <= SIXES)
			return isfirstSixCategories(rolls, category);
		if (category == YAHTZEE)
			return isYahtzee(rolls, category);
		if (category == THREE_OF_A_KIND)
			return isThreeOfAKind(rolls, category);
		if (category == FOUR_OF_A_KIND)
			return isFourOfAKind(rolls, category);
		if (category == FULL_HOUSE)
			return isFullHouse(rolls, category);
		if (category == LARGE_STRAIGHT)
			return isLargeStraight(rolls, category);
		if (category == SMALL_STRAIGHT)
			return isSmallStraight(rolls, category);
		return true;
	}

	//checks if the combination is small straight
    private boolean isSmallStraight(int[] rolls, int category) {
    	sortArray(rolls);
    	int counter = 1;
    	for (int i = 1; i < rolls.length; i++) {
    		if (rolls[i - 1] + 1 == rolls[i])
    			counter++;
    	}
    	if (counter >= 4) 
    		return true;
    	return false;
	}

    //checks if the combination is large straight
	private boolean isLargeStraight(int[] rolls, int category) {
		sortArray(rolls);
    	int counter = 1;
    	for (int i = 1; i < rolls.length; i++) {
    		if (rolls[i - 1] + 1 == rolls[i])
    			counter++;
    	}
    	if (counter == 5) 
    		return true;
    	return false;
	}
	
	//checks if the combination is full house
	private boolean isFullHouse(int[] rolls, int category) {
		//amounts of each die
		int[] amounts = new int[5];
    	
		fillAmountsArray(rolls, amounts);
    	
    	for (int i = 0; i < amounts.length; i++) {
    		if (amounts[i] == 3)
    			for (int j = 0; j < amounts.length; j++) {
    				if (amounts[j] == 2) {
    					return true;
    				}
    			}		
    	}   	
		return false;
	}

	//checks if the combination is four of a kind
	private boolean isFourOfAKind(int[] rolls, int category) {
		//amounts of each die
		int[] amounts = new int[5];
    	
		fillAmountsArray(rolls, amounts);
    	
    	for (int i = 0; i < amounts.length; i++) {
    		if (amounts[i] >= 4)
    			return true;
    	}   	
		return false;
	}

	//checks if the combination is three of a kind
	private boolean isThreeOfAKind(int[] rolls, int category) {
		//amounts of each die
    	int[] amounts = new int[5];
    	
    	fillAmountsArray(rolls, amounts);
    	
    	for (int i = 0; i < amounts.length; i++) {
    		if (amounts[i] >= 3)
    			return true;
    	}   	
		return false;
	}

	//checks if the combination is yahtzee
	private boolean isYahtzee(int[] rolls, int category) {
		for (int i = 1; i < rolls.length; i++) {
			if (rolls[i] != rolls[i - 1])
				return false;
		}
		return true;
	}

	//checks whether rolls array contains the number or not
	private boolean isfirstSixCategories(int[] rolls, int category) {
		for (int i = 0; i < rolls.length; i++) {
			if (rolls[i] == category) {
				return true;
			}
		}
		return false;
	}
	
	// fills an array with the amounts of each die
	private void fillAmountsArray(int[] rolls, int amounts[]) {
		for (int i = 0; i < rolls.length; i++) {
			int counter = 0;
			for (int j = 0; j < rolls.length; j++) {
				if (rolls[i] == rolls[j])
					counter++;
			}
			amounts[i] = counter;
		}
	}

	// sorts an array
	private void sortArray(int[] arr) {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		
		int index = 0;
		while (list.size() > 0) {
			arr[index++] = getMinNumberOfArray(list);
			list.remove(list.indexOf(getMinNumberOfArray(list)));
		}	
	}
	
	// gets a minimum number of an array
	private int getMinNumberOfArray(ArrayList<Integer> list) {
		int min = list.get(0);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) < min)
				min = list.get(i);
		}
		return min;
	}

	/* Private instance variables */
	private AudioClip diceSound;
	private AudioClip winningSound;
	private Ratings ratings;
	private int[][] playerScores;
	private int[][] categories;
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
}
