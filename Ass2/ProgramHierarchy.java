/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;


public class ProgramHierarchy extends GraphicsProgram {	

	//Width of the rectangle
	private static final int RECT_WIDTH = 130; 
	
	//Height of the rectangle
	private static final int RECT_HEIGHT = 35; 
	
	public void run() {
		drawRectangles();
		drawLines();
		addTextsInRects();
	}

	/*
	 * Draws lines
	 */
	private void drawLines() {
		drawCentralLine();
		drawLeftLine();
		drawRightLine();
	}
	
	/*
	 * draws the central line
	 */
	private void drawCentralLine() {
		double x1 = getWidth() / 2;
		double y1 = (getHeight() - RECT_HEIGHT) / 2 ;
		double x2 = getWidth() / 2;
		double y2 = (getHeight() + RECT_HEIGHT) / 2;
		drawLine(x1, y1, x2, y2);
	}

	/*
	 * draws the left line
	 */
	private void drawLeftLine() {
		double x1 = getWidth() / 2;
		double y1 = (getHeight() - RECT_HEIGHT) / 2 ;
		double x2 = (getWidth() - 13 * RECT_WIDTH / 4) / 2 + RECT_WIDTH / 2;
		double y2 = (getHeight() + RECT_HEIGHT) / 2;
		drawLine(x1, y1, x2, y2);
	}
	
	/*
	 * draws the right line
	 */
	private void drawRightLine() {
		double x1 = getWidth() / 2;
		double y1 = (getHeight() - RECT_HEIGHT) / 2 ;
		double x2 = getWidth() / 2 + 5 * RECT_WIDTH / 8 + RECT_WIDTH / 2;
		double y2 = (getHeight() + RECT_HEIGHT) / 2;
		drawLine(x1, y1, x2, y2);
	}

	/*
	 * Draws rectangles
	 */
	private void drawRectangles() {
		drawTopRect();
		drawLowerRects();
	}

	/*
	 * Draws the top rectangle
	 */
	private void drawTopRect() {
		double x = (getWidth() - RECT_WIDTH) / 2;
		double y = (getHeight() - 3 * RECT_HEIGHT) / 2;
		drawRect(x, y, RECT_WIDTH, RECT_HEIGHT);
	}
	
	/*
	 * Draws lower rectangles
	 */
	private void drawLowerRects() {
		drawLeftRect();
		drawCentralRect();
		drawRightRect();
	}

	/*
	 * Draws the lower left rectangle
	 */
	private void drawLeftRect() {
		double x = (getWidth() - 13 * RECT_WIDTH / 4) / 2;
		double y = (getHeight() + RECT_HEIGHT) / 2;
		drawRect(x, y, RECT_WIDTH, RECT_HEIGHT);
	}

	/*
	 * Draws the lower central rectangle
	 */
	private void drawCentralRect() {
		double x = (getWidth() - RECT_WIDTH) / 2;
		double y = (getHeight() + RECT_HEIGHT) / 2;
		drawRect(x, y, RECT_WIDTH, RECT_HEIGHT);
	}

	/*
	 * Draws the lower right rectangle
	 */
	private void drawRightRect() {
		double x = getWidth() / 2 + 5 * RECT_WIDTH / 8;
		double y = (getHeight() + RECT_HEIGHT) / 2;
		drawRect(x, y, RECT_WIDTH, RECT_HEIGHT);
	}

	/*
	 * adds texts in all rectangles
	 */
	private void addTextsInRects() {
		addTextInTopRect("Program");
		addTextInLeftRect("GraphicsProgram");
		addTextInCentralRect("ConsoleProgram");
		addTextInRightRect("DialogProgram");
	}
	
	/*
	 * adds text in the top rectangle
	 */
	private void addTextInTopRect(String str) {
		GLabel label = new GLabel (str);
		double x = (getWidth() - label.getWidth()) /2;
		double y = (getHeight() - 2 * RECT_HEIGHT + label.getAscent())/2;
		label = new GLabel(str , x, y);
		add(label);
	}

	/*
	 * adds text in the lower central rectangle
	 */
	private void addTextInCentralRect(String str) {
		GLabel label = new GLabel (str);
		double x = (getWidth() - label.getWidth()) /2;
		double y = (getHeight() + 2 * RECT_HEIGHT + label.getAscent())/2;
		label = new GLabel(str , x, y);
		add(label);
	}

	/*
	 * adds text in the lower right rectangle
	 */
	private void addTextInRightRect(String str) {
		GLabel label = new GLabel (str);
		double x = (getWidth() + 9 * RECT_WIDTH / 4 - label.getWidth()) /2;
		double y = (getHeight() + 2 * RECT_HEIGHT + label.getAscent())/2;
		label = new GLabel(str , x, y);
		add(label);
	}

	/*
	 * adds text in the lower left rectangle
	 */
	private void addTextInLeftRect(String str) {
		GLabel label = new GLabel (str);
		double x = (getWidth() - 9 * RECT_WIDTH / 4 - label.getWidth()) /2;
		double y = (getHeight() + 2 * RECT_HEIGHT + label.getAscent())/2;
		label = new GLabel(str , x, y);
		add(label);
	}
	
	/*
	 * draws a rectangle
	 * receives x and y coordinates, width and height of rectangle as arguments
	 */
	private void drawRect(double x, double y, int width, int height) {
		GRect rect = new GRect(x, y, width, height);
		add(rect);
	}
	
	/*
	 * draws a line
	 * receives starting and ending coordinates as arguments
	 */
	private void drawLine(double x1, double y1, double x2, double y2) {
		GLine line = new GLine(x1, y1, x2, y2);
		add(line);
		
	}
}

