/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import acm.graphics.*;
import acm.util.ErrorException;

public class HangmanCanvas extends GCanvas implements MouseListener{

/** Resets the display so that only the scaffold appears */
	public void reset() {
		addMouseListener(this);
		removeAll();
		Color c = new Color(255, 227, 237);
		setBackground(c);
		drawScaffold();
		hiddenWord = new GLabel("");
		wrongGuesses = "";
		wrongGuessesLabel = new GLabel("");
		turnsLeftLabel = new GLabel("");
		winOrLose = new GLabel("");
		textColor = new Color(255, 253, 249);
		drawHintButton();
		hintButton.addMouseListener(this);
	}
	
	//changes color of a button when hovered
	@Override
	public void mouseEntered(MouseEvent e) {
		if(turnsLeft > 0) {
			Color c1 = new Color(155, 227, 222);
			hintButton.setColor(c1);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (turnsLeft > 0) {
			Color c2 = new Color(190, 235, 233);	
			hintButton.setColor(c2);
		}
	}
	
	//if button is clicked prints a hint
	public void mouseClicked(MouseEvent e) {
		if((getElementAt(e.getX(), e.getY()) == hintButton || getElementAt(e.getX(), e.getY()) == hintLabel) && turnsLeft > 0) {
			remove(hintButton);
			hintLabel.setLabel(hint);;
			hintLabel.setFont("Helvetica-PLAIN-16");
			hintLabel.setLocation((getWidth() / 2) + UPPER_ARM_LENGTH  + BUTTON_OFFSET,
					(getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS + hintLabel.getAscent());
			add(hintLabel);
		} 
	}

/** gets the hint word */
	public void getHint(String word) {
		hint = word;
	} 
	
	//draws hint button
	private void drawHintButton() {
		hintButton = new GRect((getWidth() / 2) + UPPER_ARM_LENGTH + BUTTON_OFFSET, (getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS,
				BUTTON_WIDTH, BUTTON_HEIGHT);
		Color c = new Color(190, 235, 233);
		hintButton.setFilled(true);
		hintButton.setColor(c);
		hintLabel = new GLabel("Hint");
		hintLabel.setFont("Helvetica-PLAIN-18");
		hintLabel.setColor(textColor);
		hintLabel.setLocation((getWidth() / 2) + UPPER_ARM_LENGTH  + BUTTON_OFFSET + (BUTTON_WIDTH - hintLabel.getWidth()) / 2,
				(getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS + hintLabel.getAscent());
		add(hintButton);
		add(hintLabel);
	}
	
	//draws the scaffold
	private void drawScaffold() {
		GImage scaffold = new GImage("scaffold.png");
		scaffold.setSize(WOOD_WIDTH, SCAFFOLD_HEIGHT);
		GImage rope = new GImage("rope.png");
		rope.setSize(ROPE_WIDTH, ROPE_LENGTH);
		GImage beam = new GImage("beam.png");
		beam.setSize(BEAM_LENGTH, WOOD_WIDTH);
		rope.setLocation(getWidth() / 2 - ROPE_WIDTH, (getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS - ROPE_LENGTH);
		beam.setLocation(getWidth() / 2 - BEAM_LENGTH, (getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS - ROPE_LENGTH - WOOD_WIDTH);
		scaffold.setLocation(getWidth() / 2 - BEAM_LENGTH - WOOD_WIDTH, (getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS - ROPE_LENGTH - WOOD_WIDTH);
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
		Color c = new Color(255, 253, 249);
		hiddenWord.setLabel(word);
		hiddenWord.setFont("Helvetica-BOLD-30");
		hiddenWord.setColor(c);
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
		printTurnsLeft();
		wrongGuesses += letter;
		wrongGuessesLabel.setLabel(wrongGuesses);
		wrongGuessesLabel.setFont("Helvetica-PLAIN-15");
		wrongGuessesLabel.setLocation((getWidth() - hiddenWord.getWidth()) / 2,
				(getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH + LEG_LENGTH + FOOT_HEIGHT + GUESSES_OFFSET_FROM_FEET);
		add(wrongGuessesLabel);
	}
	
	//prints you won or you lose
	public void playerLost(boolean b) {
		if (b) {
			winOrLose.setLabel(YOU_LOSE);
		} else {
			winOrLose.setLabel(YOU_WIN);
		}
		winOrLose.setFont("Helvetica-BOLD-30");
		winOrLose.setColor(textColor);
		winOrLose.setLocation((getWidth() - winOrLose.getWidth()) / 2,
				(getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH + LEG_LENGTH + FOOT_HEIGHT + GUESSES_OFFSET_FROM_FEET);
		add(winOrLose);
		remove(wrongGuessesLabel);
		remove(hintButton);
		remove(hintLabel);
	}
	
	//prints how many turns are left
	private void printTurnsLeft() {
		turnsLeftLabel.setLabel("" + turnsLeft);
		turnsLeftLabel.setLocation((getWidth() / 2) + UPPER_ARM_LENGTH  + BUTTON_OFFSET, 
				((getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH) + LEG_LENGTH);
		turnsLeftLabel.setFont("Helvetica-BOLD-40");
		turnsLeftLabel.setColor(textColor);
		add(turnsLeftLabel);
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
		GImage leftHand = new GImage("leftHand.png");
		leftHand.setSize(ARM_WIDTH, ARM_LENGTH);
		leftHand.setLocation((getWidth() - BODY_WIDTH) / 2 - ARM_WIDTH, (getHeight() - BODY_LENGTH) / 2);
		add(leftHand);
	}

	//draw the right hand
	private void drawRightHand() {
		GImage rightHand = new GImage("rightHand.png");
		rightHand.setSize(ARM_WIDTH, ARM_LENGTH);
		rightHand.setLocation((getWidth() - BODY_WIDTH) / 2 + BODY_WIDTH, (getHeight() - BODY_LENGTH) / 2);
		add(rightHand);
	}

	//draw the left leg
	private void drawLeftLeg() {
		GImage leftLeg = new GImage("leftLeg.png");
		leftLeg.setSize(LEG_WIDTH, LEG_LENGTH);
		leftLeg.setLocation(getWidth() / 2 - LEG_WIDTH, (getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH);
		add(leftLeg);
	}

	//draw the right leg
	private void drawRightLeg() {
		GImage rightLeg = new GImage("rightLeg.png");
		rightLeg.setSize(LEG_WIDTH, LEG_LENGTH);
		rightLeg.setLocation(getWidth() / 2, (getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH);
		add(rightLeg);
	}

	//draw the left foot
	private void drawLeftFoot() {
		GImage leftFoot = new GImage("leftFoot.png");
		leftFoot.setSize(FOOT_LENGTH, FOOT_HEIGHT);
		leftFoot.setLocation(getWidth() / 2 - LEG_WIDTH - FOOT_OFFSET, (getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH + LEG_LENGTH);
		add(leftFoot);
	}

	//draw the right foot
	private void drawRightFoot() {
		GImage rightFoot = new GImage("rightFoot.png");
		rightFoot.setSize(FOOT_LENGTH, FOOT_HEIGHT);
		rightFoot.setLocation(getWidth() / 2 + FOOT_OFFSET / 2, (getHeight() - BODY_LENGTH) / 2 + BODY_LENGTH + LEG_LENGTH);
		add(rightFoot);
	}

	//draw the head
	private void drawHead() {
		GImage head = new GImage("head.png");
		head.setLocation(getWidth() / 2 - HEAD_RADIUS, (getHeight() - BODY_LENGTH) / 2 - 2 * HEAD_RADIUS);
		head.setSize(2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		add(head);
	}
	
	//draw the body
	private void drawBody() {
		GImage body = new GImage("body.png");
		body.setSize(BODY_WIDTH, BODY_LENGTH);
		body.setLocation((getWidth() - BODY_WIDTH) / 2, (getHeight() - BODY_LENGTH) / 2);
		add(body);
	}
	
	

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 420;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int BODY_WIDTH = 72;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LEG_WIDTH = 28;
	private static final int ARM_LENGTH = 110;
	private static final int ARM_WIDTH = 32;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 36;
	private static final int FOOT_HEIGHT = 16;
	private static final int WORD_OFFSET_FROM_BEAM = 40;
	private static final int GUESSES_OFFSET_FROM_FEET = 25;
	private static final int BUTTON_OFFSET = 20;
	private static final int BUTTON_WIDTH = 60;
	private static final int BUTTON_HEIGHT = 26;
	private static final int FOOT_OFFSET = 16;
	private static final int ROPE_WIDTH = 6;
	private static final int WOOD_WIDTH = 25;
	private static final String YOU_LOSE = "YOU LOSE.";
	private static final String YOU_WIN = "YOU WIN!";
	
	
	private GLabel hiddenWord;
	private GLabel wrongGuessesLabel;
	private GRect hintButton;
	private GLabel hintLabel;
	private GLabel turnsLeftLabel;
	private String wrongGuesses;
	private String hint;
	private GLabel winOrLose;
	private Color textColor;
	

	//counts how many turns are left
	private int turnsLeft = 8;

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
}
