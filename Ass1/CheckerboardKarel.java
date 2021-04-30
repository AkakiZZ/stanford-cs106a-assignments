/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {

	public void run() {
		fillFirstColumn();	
		changeColumn();
		while (frontIsClear()) {
			fillEvenNumberedColumn();
			changeColumn();	
			fillOddNumberedColumn();
			changeColumn();
		}
		fillLastColumn();
	}

	
	/*
	 * Pre-condition: Facing east at the beginning of the first column
	 * Post-condition: Facing east at the beginning of the same column
	 * Fills the first column
	 */
	private void fillFirstColumn() {
		fillOddNumberedColumn(); 
	}
	
	/*
	 * Pre-condition: Facing east at the beginning of the last column
	 * Post-condition: Facing east at the beginning of the last column
	 * Fills the last column if necessary
	 */ 
	private void fillLastColumn() {
		if (noBeepersPresent() && frontIsBlocked()) {	//To make sure that the last column is filled
			fillEvenNumberedColumn();
		}
	}
	
	
	/*
	 * Pre-condition: Facing east at the beginning of odd numbered column
	 * Post-condition: Facing east at the beginning of the same column
	 * Fills odd numbered columns
	 */
	private void fillOddNumberedColumn() {
		turnLeft();
		putBeeper();
		while (frontIsClear()) {
			move();
			if (frontIsClear()) {
				move();
				putBeeper();
			}	
			
		}
		getBackToFirstSquareOfColumn();
	}
	

	/*
	 * Pre-condition: Facing east at the beginning of even numbered column
	 * Post-condition: Facing east at the beginning of the same column
	 * Fills even numbered columns
	 */
	private void fillEvenNumberedColumn() {
		turnLeft();
		while (frontIsClear()) {
			move();
			putBeeper();
			if (frontIsClear()) {
				move();
			}	
		}
		getBackToFirstSquareOfColumn();
	}
	
	
	/*
	 * Pre-condition: Facing east at the beginning of column
	 * Post-condition: Facing east at the beginning of the next column
	 * Moves Karel to next column if front is clear
	 */
	private void changeColumn() {
		if (frontIsClear()) {
			move();
		}
	}
	
	
	/*
	 * Pre-condition: Facing east at the end of a column
	 * Post-condition: Facing east at the beginning of the same column
	 * Moves Karel to the first square of a column
	 */
	private void getBackToFirstSquareOfColumn() {
		turnRight();
		turnRight();
		while (frontIsClear()) {
			move();
		}
		turnLeft();
	}
}
