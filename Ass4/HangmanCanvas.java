/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
import acm.util.ErrorException;

public class HangmanCanvas extends GCanvas {

/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		drawScaffold();
		hiddenWord = new GLabel("");
		wrongGuesses = "";
		wrongGuessesLabel = new GLabel("");
	}
	
	//draws the scaffold
	private void drawScaffold() {
		GLine rope = new GLine(getWidth() / 2, (getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS,
				getWidth() / 2, (getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS - ROPE_LENGTH);
		GLine beam = new GLine(getWidth() / 2, (getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS - ROPE_LENGTH,
				getWidth() / 2 - BEAM_LENGTH, (getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS - ROPE_LENGTH);
		GLine scaffold = new GLine(getWidth() / 2 - BEAM_LENGTH, (getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS - ROPE_LENGTH,
				getWidth() / 2 - BEAM_LENGTH, (getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS - ROPE_LENGTH + SCAFFOLD_HEIGHT);
		add(rope);
		add(beam);
		add(scaffold);
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		hiddenWord.setLabel(word);
		hiddenWord.setFont("Helvetica-PLAIN-30");
		hiddenWord.setLocation((getWidth() - hiddenWord.getWidth()) / 2,
				(getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS - ROPE_LENGTH - WORD_OFFSET_FROM_BEAM);
		add(hiddenWord);	
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		turnsLeft--;
		drawMan();
		wrongGuesses += letter;
		wrongGuessesLabel.setLabel(wrongGuesses);
		wrongGuessesLabel.setFont("Helvetica-PLAIN-15");
		wrongGuessesLabel.setLocation((getWidth() - hiddenWord.getWidth()) / 2,
				(getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS - ROPE_LENGTH + SCAFFOLD_HEIGHT + GUESSES_OFFSET_FROM_SCAFFOLD);
		add(wrongGuessesLabel);
	}
	
	//draws the parts of the man
	private void drawMan() {
		switch (turnsLeft) {
		case 0: drawRightFoot();
				break;
		case 1: drawLeftFoot();
				break;
		case 2: drawRightLeg();
				break;
		case 3: drawLeftLeg();
				break;
		case 4: drawRightHand();
				break;
		case 5: drawLeftHand();
				break;
		case 6: drawBody();
				break;
		case 7: drawHead();
				break;
	}
	
	}

	// draw the left hand
	private void drawLeftHand() {
		GLine upperArm = new GLine((getWidth() / 2), (getHeight() - BODY_LENGTH) / 2 + ARM_OFFSET_FROM_HEAD, 
				(getWidth() / 2) - UPPER_ARM_LENGTH, (getHeight() - BODY_LENGTH) / 2 + ARM_OFFSET_FROM_HEAD);
		GLine lowerArm = new GLine((getWidth() / 2) - UPPER_ARM_LENGTH, (getHeight() - BODY_LENGTH) / 2 + ARM_OFFSET_FROM_HEAD,
				(getWidth() / 2) -  UPPER_ARM_LENGTH, (getHeight() - BODY_LENGTH) / 2 + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add(upperArm);
		add(lowerArm);
	}

	//draw the right hand
	private void drawRightHand() {
		GLine upperArm = new GLine((getWidth() / 2), (getHeight() - BODY_LENGTH) / 2 + ARM_OFFSET_FROM_HEAD, 
				(getWidth() / 2) + UPPER_ARM_LENGTH, (getHeight() - BODY_LENGTH) / 2 + ARM_OFFSET_FROM_HEAD);
		GLine lowerArm = new GLine((getWidth() / 2) + UPPER_ARM_LENGTH, (getHeight() - BODY_LENGTH) / 2 + ARM_OFFSET_FROM_HEAD,
				(getWidth() / 2) +  UPPER_ARM_LENGTH, (getHeight() - BODY_LENGTH) / 2 + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add(upperArm);
		add(lowerArm);
	}

	//draw the left leg
	private void drawLeftLeg() {
		GLine hip = new GLine((getWidth() / 2), ((getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH), 
				(getWidth() / 2) - HIP_WIDTH, ((getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH));
		GLine leg = new GLine((getWidth() / 2) - HIP_WIDTH, ((getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH),
				(getWidth() / 2) - HIP_WIDTH, ((getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH) + LEG_LENGTH);
		add(hip);
		add(leg);
	}

	//draw the right leg
	private void drawRightLeg() {
		GLine hip = new GLine((getWidth() / 2), ((getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH), 
				(getWidth() / 2) + HIP_WIDTH, ((getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH));
		GLine leg = new GLine((getWidth() / 2) + HIP_WIDTH, ((getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH),
				(getWidth() / 2) + HIP_WIDTH, ((getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH) + LEG_LENGTH);
		add(hip);
		add(leg);
	}

	//draw the left foot
	private void drawLeftFoot() {
		GLine foot = new GLine((getWidth() / 2) - HIP_WIDTH, ((getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH) + LEG_LENGTH,
				(getWidth() / 2) - HIP_WIDTH - FOOT_LENGTH, ((getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH) + LEG_LENGTH);
		add(foot);
	}

	//draw the right foot
	private void drawRightFoot() {
		GLine foot = new GLine((getWidth() / 2) + HIP_WIDTH, ((getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH) + LEG_LENGTH,
				(getWidth() / 2) + HIP_WIDTH + FOOT_LENGTH, ((getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH) + LEG_LENGTH);
		add(foot);
	}

	//draw the head
	private void drawHead() {
		GOval head = new GOval(getWidth() / 2 - HEAD_RADIUS, (getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS, 
				2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		add(head);
	}
	
	//draw the body
	private void drawBody() {
		GLine body = new GLine((getWidth() / 2), (getHeight() - BODY_LENGTH) / 2, 
				(getWidth() / 2), ((getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH));
		add(body);
	}
	
	

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int WORD_OFFSET_FROM_BEAM = 18;
	private static final int GUESSES_OFFSET_FROM_SCAFFOLD = 18;
	
	private GLabel hiddenWord;
	private GLabel wrongGuessesLabel;
	private String wrongGuesses;

	//counts how many turns are left
	private int turnsLeft = 8;
}
