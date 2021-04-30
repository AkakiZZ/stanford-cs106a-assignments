/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;

import java.awt.Button;
import java.awt.event.*;
import javax.swing.*;


public class NameSurfer extends Program implements NameSurferConstants {

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
		database = new NameSurferDataBase(NAMES_DATA_FILE);
		graph = new NameSurferGraph();
		add(graph);
		nameField = new JTextField(10);
		graphButton = new Button("Graph");
		clearButton = new Button("Clear");
		add(new JLabel("Name:"), SOUTH);
		add(nameField, SOUTH);
		add(graphButton, SOUTH);
		add(clearButton, SOUTH);
		addActionListeners(this);
		nameField.addActionListener(this);
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		NameSurferEntry entry = database.findEntry(nameField.getText());
		if ((e.getSource() == graphButton || e.getSource() == nameField)
				&& entry != null) {
			System.out.println("Graph: " + entry);
			graph.addEntry(entry);
			graph.update();
		} else if (e.getSource() == clearButton) {
			System.out.println("Clear");
			graph.clear();
			graph.update();
		} else if (entry == null) {
			System.out.println("No data found");
			graph.update();
		} 
	}
	
	private JTextField nameField;
	private Button graphButton;
	private Button clearButton;
	private NameSurferDataBase database;
	private NameSurferGraph graph; 
}
