/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {

	public void run() {
		int a = readInt("a: ");
		int b = readInt("b: ");
		double c = calculateHypotenuse(a , b);
		printResult(c);
	}

	/*
	 * gets a and b sides as arguments
	 * returns calculated hypotenuse c
	 */
	private double calculateHypotenuse(int a , int b) {
		return Math.sqrt(a * a + b * b);
	}
	
	/* 
	 * print calculated length of hypotenyse
	 */
	private void printResult(double c) {
		println("c = " + c);
	}
	
}
