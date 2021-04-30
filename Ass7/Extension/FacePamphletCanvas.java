/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		message = new GLabel(EMPTY_STRING);
		name = new GLabel(EMPTY_STRING);
		status = new GLabel(EMPTY_STRING);
		friends = new GLabel("Friends");
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		message.setLabel(msg);;
		message.setLocation((getWidth() - message.getWidth()) / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
		message.setFont(MESSAGE_FONT);
		add(message);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		displayName(profile.getName());
		if (profile.getImage() != null) {
			displayImage(profile.getImage());
		} else {
			displayDefault();
		}
		displayStatus(profile.getStatus(), profile.getName());
		displayFriends(profile.getFriends());
	}
	/**
	 * Removes everything from canvas
	 */
	public void clearCanvas() {
		removeAll();
	}
	
	/*
	 * Shows the name of the current profile
	 */
	private void displayName(String name) {
		this.name.setLabel(name);
		this.name.setFont(PROFILE_NAME_FONT);
		this.name.setColor(Color.blue);
		this.name.setLocation(LEFT_MARGIN, this.name.getAscent() + TOP_MARGIN);
		add(this.name);
	}
	
	/*
	 * Displays the image
	 */
	private void displayImage(GImage image) {
		image.scale(IMAGE_WIDTH / image.getWidth(), IMAGE_HEIGHT / image.getHeight());
		image.setLocation(LEFT_MARGIN, name.getY() +  IMAGE_MARGIN);
		add(image);
	}
	
	/*
	 * Displays the "No Image"
	 */
	private void displayDefault() {
		GRect rect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
		rect.setLocation(LEFT_MARGIN, name.getY() +  IMAGE_MARGIN);
		GLabel noImage = new GLabel("No Image");
		noImage.setFont(PROFILE_IMAGE_FONT);
		noImage.setLocation(rect.getX() + (IMAGE_WIDTH - noImage.getWidth()) / 2, rect.getY() + (IMAGE_HEIGHT - noImage.getAscent()) / 2);
		add(rect);
		add(noImage);
	}
	
	/*
	 * Displays the status
	 */
	private void displayStatus(String status, String name) {
		remove(this.status);
		if (!status.equals(EMPTY_STRING)) {
			this.status.setLabel(name + " is " + status);
		} else {
			this.status.setLabel("No Current Status");
		}
		this.status.setFont(PROFILE_STATUS_FONT);
		this.status.setLocation(LEFT_MARGIN, this.name.getY() +  IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + this.status.getAscent());
		add(this.status);
	}
	
	/*
	 * Displays the friends
	 */
	private void displayFriends(Iterator<String> friends) {
		this.friends.setLocation(getWidth() / 2, name.getY() +  IMAGE_MARGIN);
		this.friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(this.friends);
		int counter = 1;
		while (friends.hasNext()) {
			GLabel friendLabel = new GLabel(friends.next());
			friendLabel.setFont(PROFILE_FRIEND_FONT);
			friendLabel.setLocation(getWidth() / 2 , name.getY() +  IMAGE_MARGIN + friendLabel.getAscent() * counter++);
			add(friendLabel);
		}
	}

	private GLabel message;
	private GLabel name;
	private GLabel status;
	private GLabel friends;
}
