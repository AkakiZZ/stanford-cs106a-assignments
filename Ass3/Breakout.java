/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;
	
	/** paddle and ball */
	private GRect paddle;
	private GOval ball;
	
	/** velocity */
	private double vx;
	private double vy;
	
	/** random generator */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	/** shows how many bricks are left */
	private int bricksLeft;
	
	/*
	 * Draws bricks, the ball and the paddle
	 */
	public void init() {
		drawBricks();
		drawPaddle();
		drawBall();
		addMouseListeners();
	}
	
	/** Runs the Breakout program. */
	public void run() {
		playGame();
	}
	
	/*
	 * starts the playing process of the game
	 */
	private void playGame() {
		waitForClick();
		moveBall();
	}
	
	/*
	 * controls moving and bouncing of the ball from walls
	 */
	private void moveBall() {
		int turnsLeft = NTURNS;
		bricksLeft = 100;
		vy = 3.0;
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		} 
		
		while (true) {
			ball.move(vx, vy);
			pause(10);
			
			if (ball.getX() + 2 * BALL_RADIUS > WIDTH || ball.getX() < 0) {
				vx = -vx;
			} else if (ball.getY() < 0) {
				vy = -vy;
			} else if (ball.getY() + 2 * BALL_RADIUS > HEIGHT) {
				turnsLeft--;
				pause(1000);
				if (turnsLeft > 0) {
					ball.setLocation(WIDTH / 2 - BALL_RADIUS, HEIGHT / 2 - BALL_RADIUS);
				} else {
					remove(ball);
					printLosingMessage();
					break;
				}
			}
			checkCollidingObject();
			if(bricksLeft == 0) {
				printWinningMessage();
				pause(1000);
				remove(ball);
				break;
			}
		}
	}
	
	/*
	 * prints winning message
	 */
	private void printWinningMessage() {
		printLabel("You've Won!");
	}

	/*
	 * prints loosing message
	 */
	private void printLosingMessage() {
		printLabel("You've Lost");
	}
	
	/*
	 * prints a text in the center of the window
	 */
	private void printLabel(String str) {
		GLabel label = new GLabel(str);
		label.setLocation((WIDTH - label.getWidth()) / 2, (HEIGHT - label.getAscent()) / 2);
		add(label);
	}
	
	/*
	 * checks what is collided by the ball
	 * removes it if it's a brick
	 * bounces the ball off if it's the paddle
	 */
	private void checkCollidingObject() {
		double lowerYCoordinateOfBall = ball.getY() + 2 * BALL_RADIUS;
		GObject collidingObject = getCollidingObject();
		if (collidingObject == paddle && lowerYCoordinateOfBall == HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT) {
			vy = -vy;
		} else if (collidingObject == paddle && lowerYCoordinateOfBall < HEIGHT - PADDLE_Y_OFFSET && lowerYCoordinateOfBall > HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT) {
			ball.setLocation(ball.getX(), paddle.getY() - 2 * BALL_RADIUS);
			vy = -vy;
		} else if (collidingObject != null && collidingObject != paddle && lowerYCoordinateOfBall <= HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT) {
			remove(collidingObject);
			vy = -vy;
			bricksLeft--;
		}
	}

	/*
	 * gets object which is collided by the ball
	 */
	private GObject getCollidingObject() {
		GObject objectAtFirstPoint = getElementAt(ball.getX() , ball.getY());
		GObject objectAtSecondPoint = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		GObject objectAtThirdPoint = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
		GObject objectAtFourthPoint = getElementAt(ball.getX() , ball.getY() + 2 * BALL_RADIUS);
		
		if (objectAtFirstPoint != null) {
			return objectAtFirstPoint;
		} else if (objectAtSecondPoint != null) {
			return objectAtSecondPoint;
		} else if (objectAtThirdPoint != null) {
			return objectAtFourthPoint;
		} else if (objectAtFourthPoint != null) {
			return objectAtFourthPoint;
		} else {
			return null;
		}
	}

	/*
	 * Moves the paddle when mouse moves
	 */
	public void mouseMoved(MouseEvent e) {
		//x coordinate of the cursor
		int x = e.getX();
		if (x + PADDLE_WIDTH / 2 < WIDTH && x - PADDLE_WIDTH / 2 > 0) {
			paddle.setLocation(x - PADDLE_WIDTH / 2, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}

	/*
	 * draws bricks
	 */
	private void drawBricks() {
		//x coordinate of the brick
		double x = (WIDTH - 10 * BRICK_WIDTH - 9 * BRICK_SEP) / 2;
		
		//y coordinate of the brick
		double y = BRICK_Y_OFFSET;
		
		for (int i = 0; i < 10; i++) {
			drawOneLine(x, y, i);
			y += (BRICK_HEIGHT + BRICK_SEP);
			
		}
	}
	
	/*
	 * draws one line of brick set
	 */
	private void drawOneLine(double x, double y, int i) {
		for (int j = 0; j < 10; j++) {
			createBrick(x, y, i);
			x += (BRICK_WIDTH + BRICK_SEP);
		}
	}
	
	/*
	 * creates a brick with x and y coordinates
	 */
	private void createBrick(double x, double y, int i) {
		GRect brick = new GRect(x , y, BRICK_WIDTH, BRICK_HEIGHT);
		brick.setFilled(true);
		drawColoredBrick(brick, i);
	}
	
	/*
	 * adds color to a brick and draws it
	 */
	private void drawColoredBrick(GRect brick, int i) {
		if(i == 0 || i == 1) {
			brick.setColor(Color.RED);
		} else if (i == 2 || i == 3) {
			brick.setColor(Color.ORANGE);
		} else if (i == 4 || i == 5) {
			brick.setColor(Color.YELLOW);
		} else if (i == 6 || i == 7) {
			brick.setColor(Color.GREEN);
		} else {
			brick.setColor(Color.CYAN);
		}
		add(brick);
	}

	/*
	 * draws the paddle
	 */
	private void drawPaddle() {
		// x coordinate of the paddle
		double x = (WIDTH - PADDLE_WIDTH) / 2;
		
		// y coordinate of the paddle
		double y = HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT;

		paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
	}
	
	/*
	 * draws the ball
	 */
	private void drawBall() {
		ball = new GOval(WIDTH / 2 - BALL_RADIUS, HEIGHT / 2 - BALL_RADIUS, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
		ball.setFilled(true);
		ball.setColor(Color.BLACK);
        add(ball);
	}
}
