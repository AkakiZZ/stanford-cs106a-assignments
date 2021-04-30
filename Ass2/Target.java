/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {	
	
	//radius of big circle
	private static final int R1 = 72;
			
	//radius of medium circle
	private static final int R2 = 48;
			
	//radius of small circle
	private static final int R3 = 22;
	
	public void run() {
		double x = getWidth() / 2;
		double y = getHeight() / 2;
		
		drawCircles(x, y, R1, R2, R3);
		
	}

	/*
	 * draws all circles
	 */
	private void drawCircles(double x, double y, int r1, int r2, int r3) {
		GOval circle1 = createRedCircle(x, y, r1);
		GOval circle2 = createWhiteCircle(x, y, r2);
		GOval circle3 = createRedCircle(x, y, r3);
		add(circle1);
		add(circle2);
		add(circle3);
	}

	/*
	 * creates a red circle
	 */
	private GOval createRedCircle(double x, double y, int radius) {
		GOval circle = createCircle(x, y, radius);
		circle.setColor(Color.RED);
		return circle;
	}
	
	/*
	 * creates a white circle
	 */
	private GOval createWhiteCircle(double x, double y, int radius) {
		GOval circle = createCircle(x, y, radius);
		circle.setColor(Color.WHITE);
		return circle;
	}
	
	/*
	 * creates a circle with radius and x and y coordinates
	 */
	private GOval createCircle(double x, double y, int radius) {
		GOval circle = new GOval(x - radius, y - radius, 2 * radius, 2 * radius);
		circle.setFilled(true);
		return circle;
	}
}
