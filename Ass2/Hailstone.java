/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	
	public void run() {
		int n = getInputNumber();
		getOne(n);
	}

	/*
	 * gets inputed number
	 */
	private int getInputNumber() {
		return readInt("Enter a number: ");
	}

	/*
	 * does the process until n becomes 1
	 */
	private void getOne(int n) {
		int counter = 0;
		while (n != 1) {
			int result;
			if (nIsEven(n)) {
				result = n / 2;
				println(n + " is even, so I take half: " + result);
				n = result;
			} else {
				result = 3 * n + 1;
				println(n + " is odd, so I make 3n + 1: " + result);
				n = result;
			}	
			counter++;
		}
		printNumberOfSteps(counter);
	}

	/*
	 * Checks whether n is even or not
	 */
	private boolean nIsEven(int n) {
		return n % 2 == 0;
	}
	
	/*
	 * prints how many steps did it take to reach 1
	 */
	private void printNumberOfSteps(int counter) {
		println("The process took " + counter + " to reach 1");
	}
}

