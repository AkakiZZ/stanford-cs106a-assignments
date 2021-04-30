/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {

	private final int SENTINEL = 0;
	
	public void run() {
		//input the first number
		int inputNumber = inputNextNumber();
		if (inputNumber == SENTINEL) {
			println("You haven't entered any number: ");
		} else {
			int maxNumber = inputNumber;
			int minNumber = inputNumber;
			while (true) {
				inputNumber = inputNextNumber();
				if (inputNumber == SENTINEL) {
					break;
				} else {
					maxNumber = Math.max(maxNumber, inputNumber);
					minNumber = Math.min(minNumber, inputNumber);
				}		
			}
			printResult(minNumber, maxNumber);
		}	
	}

	/*
	 *Allows the user to input a number
	 */
	private int inputNextNumber() {
			int inputNumber = readInt("?");
			return inputNumber;
	}
	
	/*
	 * Prints the maximum number and the minimum number
	 */
	private void printResult(int minNumber, int maxNumber) {
		println("smallest: " + minNumber);
		println("largest: " + maxNumber);
	}
}
