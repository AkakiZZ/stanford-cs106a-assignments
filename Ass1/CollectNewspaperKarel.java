/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {

	public void run() {
		moveToBeeper();
		getBeeper();
		TakeBeeperToDestination();
	}	
	
	/*
	 * Pre-condition: Facing East at the starting square
	 * Post-condition: Facing East at the square where beeper is placed
	 */
	private void moveToBeeper() {
		doubleMove();
		turnRight();
		move();
		turnLeft();
		move();
	}
	
	// Picks the Beeper
	private void getBeeper() {
		pickBeeper();
	}

	/*
	 * Pre-condition: Facing East at the square where beeper is placed
	 * Post-condition: Facing west at the starting square
	 */
	private void TakeBeeperToDestination() {
		turnLeft();
		turnLeft();
		move();
		turnRight();
		move();
		turnLeft();
		doubleMove();
	}

	//Moves karel 2 times
	private void doubleMove() {
		move();
		move();
	}

}
