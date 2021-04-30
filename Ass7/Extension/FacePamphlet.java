/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;

import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * Interactors in the application, and taking care of any other 
	 * Initialization that needs to be performed.
	 */
	public void init() {
		nameField = new JTextField(TEXT_FIELD_SIZE);
		statusField = new JTextField(TEXT_FIELD_SIZE);
		pictureNameField = new JTextField(TEXT_FIELD_SIZE);
		addFriendField = new JTextField(TEXT_FIELD_SIZE);
		addPersonButton = new JButton("Add");
		lookUpPersonButton = new JButton("Lookup");
		removePersonButton = new JButton("Delete");
		changeStatusButton = new JButton("Change Status");
		changePicButton = new JButton("Change Picture");
		addFriendButton = new JButton("Add Freind");
		saveButton = new JButton("Save");
		nameLabel = new JLabel("Name:");
		canvas = new FacePamphletCanvas();
		add(nameLabel, NORTH);
		add(nameField, NORTH);
		add(addPersonButton, NORTH);
		add(removePersonButton, NORTH);
		add(lookUpPersonButton, NORTH);
		add(saveButton, NORTH);
		add(statusField, WEST);
		add(changeStatusButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST); 
		add(pictureNameField, WEST);
		add(changePicButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST); 
		add(addFriendField, WEST);
		add(addFriendButton, WEST);
		add(canvas);
		addActionListeners(this);
		statusField.addActionListener(this);
		pictureNameField.addActionListener(this);
		addFriendField.addActionListener(this);
		db = new FacePamphletDatabase();
	}
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == addPersonButton && !nameField.getText().equals(EMPTY_STRING)) {
    		currentProfile = new FacePamphletProfile(nameField.getText());
    		canvas.displayProfile(currentProfile);
    		if (!db.containsProfile(nameField.getText())) {
    			db.addProfile(currentProfile);
    			canvas.showMessage("New profile created");
    		} else {
    			db.addProfile(currentProfile);
    			canvas.showMessage("A profile with the name " + nameField.getText() +  " already exists");
    		}	
    	} else if (e.getSource() == removePersonButton && !nameField.getText().equals(EMPTY_STRING)) {
    		if (!db.containsProfile(nameField.getText())) {
    			canvas.showMessage("A Profile with the name of " + nameField.getText() +  " doesn't exist");
    		} else {
    			db.deleteProfile(nameField.getText());
    			currentProfile = null;
    			canvas.clearCanvas();
    			canvas.showMessage("Profile of " + nameField.getText() +  " deleted");
    		}
    	} else if (e.getSource() == lookUpPersonButton && !nameField.getText().equals(EMPTY_STRING)) {
    		if (db.containsProfile(nameField.getText())) {
    			canvas.showMessage("Displaying: " + nameField.getText());
    			currentProfile = db.getProfile(nameField.getText());
        		canvas.displayProfile(currentProfile);
    		} else {
    			currentProfile = null;
    			canvas.clearCanvas();
    			canvas.showMessage("A Profile with the name of " + nameField.getText() +  " doesn't exist");
    		}
    		
    	} else if ((e.getSource() == changeStatusButton || e.getSource() == statusField) 
    			&& !statusField.getText().equals(EMPTY_STRING)) {
    		if (currentProfile != null) {
    			db.getProfile(currentProfile.getName()).setStatus(statusField.getText());
        		canvas.displayProfile(currentProfile);
    			canvas.showMessage("Status updated to: " + statusField.getText());
    		} else {
    			canvas.showMessage("Please select a profile to change status");
    		}
    	} else if ((e.getSource() == changePicButton || e.getSource() == pictureNameField) 
    			&& !pictureNameField.getText().equals(EMPTY_STRING)) {
    		if (currentProfile != null) {
    			GImage image = null;
    			try { 
    				image = new GImage(pictureNameField.getText()); 
    				currentProfile.setImage(image, pictureNameField.getText());
    				db.getProfile(currentProfile.getName()).setImage(image, pictureNameField.getText());
    				canvas.displayProfile(currentProfile);
    				canvas.showMessage("Picture updated");
    			} catch (ErrorException ex) { 
    				canvas.showMessage("Unable to open image file: " + pictureNameField.getText());
    			}
    		} else {
    			canvas.showMessage("Please select a profile to change picture");
    		}
    		
    	} else if ((e.getSource() == addFriendButton || e.getSource() == addFriendField) 
    			&& !addFriendField.getText().equals(EMPTY_STRING)) {
    		if (currentProfile != null) {
    			if (db.getProfile(addFriendField.getText()) != null) {
    				if (currentProfile.addFriend(addFriendField.getText())) {
        				db.getProfile(addFriendField.getText()).addFriend(currentProfile.getName());
        				canvas.displayProfile(currentProfile);
        				canvas.showMessage(addFriendField.getText() + " Added as a friend");

        			} else {
        				canvas.showMessage(currentProfile.getName() + " Already has " + addFriendField.getText() + " as a friend");
        			}	
    			} else {
    				canvas.showMessage(addFriendField.getText() + " doesn't exist");
    			}
    		} else {
    			canvas.showMessage("Please select a profile to add friend");
    		}
    	} else if (e.getSource() == saveButton) {
    		db.writeData();
    	}
    }
    
    private JTextField nameField;
    private JTextField statusField;
    private JTextField pictureNameField;
    private JTextField addFriendField;
    private JButton addPersonButton;
    private JButton lookUpPersonButton;
    private JButton removePersonButton;
    private JButton changeStatusButton;
    private JButton changePicButton;
    private JButton addFriendButton;
    private JButton saveButton;
    private JLabel nameLabel;
    private FacePamphletDatabase db;
    private FacePamphletProfile currentProfile;
    private FacePamphletCanvas canvas;
}
