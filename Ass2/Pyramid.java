/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

	/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

	/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;

	public void run() {
		drawPyramid();
	}

	/*
	 * method that draws the pyramid
	 */
	private void drawPyramid() {
		
		for (int i = 0; i < BRICKS_IN_BASE; i++) {
			
			//x coordinate of a brick
			int x = (getWidth() - BRICK_WIDTH * (BRICKS_IN_BASE - i)) / 2;
			
			//y coordinate of a brick
			int y = getHeight() - i * BRICK_HEIGHT;
			
			drawOneLine(i, x, y);
			y -= BRICK_HEIGHT;
		}
		
	}
	
	/*
	 * draws one line of the pyramid
	 */
	private void drawOneLine(int i, int x, int y) {
		for (int j = 0; j < BRICKS_IN_BASE - i; j++) {
			drawBrick(x, y);
			x += BRICK_WIDTH;
		}
	}

	/*
	 * draws bricks from which pyramid is created
	 */
	private void drawBrick(int x , int y) {
		GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
		add(brick);
	}
}

