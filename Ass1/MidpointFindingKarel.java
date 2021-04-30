/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	public void run() {
		goToMiddlePointOfTopRaw();
		moveDown();
		putBeeperOnMiddlePoint();
	}
	
	/*
	 * Pre-condition: Facing east at the 1x1 square
	 * post-condition: Facing east at the middle point of the top row
	 */
	private void goToMiddlePointOfTopRaw() {
		while(leftIsClear()) {
			moveToNorthSquare();
			moveToEastSqure();
		}
	}


	/*
	 * Pre-condition: Facing east
	 * post-condition: Facing east after double or single move to north/upper square
	 */
	private void moveToNorthSquare() {
		turnLeft();
		move();
		if(frontIsClear()) {
			move();
		}
		turnRight();
	}

	
	/*
	 * Pre-condition: Facing east
	 * post-condition: Facing east at the east/next square
	 */
	private void moveToEastSqure() {
		move();
	}
	
	
	/*
	 * Pre-condition: Facing east in the middle point of the top row
	 * post-condition: Facing east in the middle point of the bottom row
	 */
	private void moveDown() {
		turnRight();
		while(frontIsClear()) {
			move();
		}
		turnLeft();
	}
	
	
	//puts beeper on the destination square
	private void putBeeperOnMiddlePoint() {
		putBeeper();
	}
	
	
}
